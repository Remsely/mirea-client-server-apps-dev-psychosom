"use client";

import {FieldError, FieldValues, SubmitHandler, useForm} from "react-hook-form";
import styles from "./AuthModalForm.module.scss";
import {toast} from "react-hot-toast";
import {useEffect, useState} from "react";
import {signIn, SignInResponse, signOut, useSession} from "next-auth/react";
import {QRCodeSection} from "./QRCodeSection/QRCodeSection";
import {useDataStore} from "@/shared/stores/dataStore";
import {
    Button,
    ContactInput,
    Dialog,
    DialogContent,
    DialogHeader,
    DialogTitle,
    NameInput,
    PasswordInput
} from "@/shared/componetns";
import {redirect} from "next/navigation";

interface AuthModalFormProps {
    isOpen: boolean;
    onClose: () => void;
}

export function AuthModalForm({isOpen, onClose}: AuthModalFormProps) {
    const {
        register,
        formState: {errors},
        clearErrors,
        handleSubmit,
        watch,
        reset,
    } = useForm({mode: "onBlur"});
    const {data: session} = useSession();

    const [qrLink, setQrLink] = useState<string | null>(null);
    const [mode, setMode] = useState<"login" | "register">("login");
    const setData = useDataStore((state) => state.setDataAfterRegistration);
    const { dataAfterRegistration } = useDataStore();

    useEffect(() => {
        if (session?.webSocketToken) {
            signOut({redirect: false});
            const ws = new WebSocket(`${process.env.NEXT_PUBLIC_WS_URL}/ws/auth/confirmation?token=${session.webSocketToken}`);
            ws.onopen = () => console.log("WebSocket подключен");
            ws.onclose = async () => {
                console.log("WebSocket отключён");
                if (dataAfterRegistration) onSubmit(dataAfterRegistration);
            };
            ws.onerror = (error) => console.error("Ошибка WebSocket:", error);
        }
    }, [session]);

    useEffect(() => {
        if (session?.accountConfirmationUrl) {
            setQrLink(session.accountConfirmationUrl);
        }
    }, [session]);

    const onSubmit: SubmitHandler<FieldValues> = async (data) => {
        const formattedContact = data.contact.startsWith("+7") ? "+" + data.contact.replace(/[^0-9]/g, "") : data.contact;
        const credentials = {
            username: formattedContact,
            password: data.password,
            redirect: false,
            callbackUrl: "/",
        };

        if (mode === "register") {
            setData(data);
            await signIn("credentials", {
                ...credentials,
                firstName: data.firstName,
                lastName: data.lastName,
                isRegister: true,
            });
            onSwitchMode();
        } else {
            const result = await signIn("credentials", credentials);
            handleAuthResult(result);
        }
    };

    const handleAuthResult = (result: SignInResponse | undefined) => {
        if (result?.status === 401 && !qrLink) {
            return toast.error("Ошибка авторизации: Неверный логин или пароль");
        }

        if (result?.ok) {
            toast.success("Успешная авторизация!");
            setTimeout(() => (redirect("/")), 700);
        }
    };

    const onSwitchMode = () => {
        setMode(mode === "login" ? "register" : "login");
        reset();
    };

    return (
        <Dialog open={isOpen} onOpenChange={onClose}>
            <DialogContent forceMount>
                <DialogHeader>
                    <>
                        <DialogTitle>{mode === "login" ? "Вход" : "Регистрация"}</DialogTitle>
                        {qrLink ? (
                            <QRCodeSection qrLink={qrLink}/>
                        ) : (
                            <form onSubmit={handleSubmit(onSubmit)} className={styles.form}>
                                <div className={styles.inputs}>
                                    {mode === "register" && (
                                        <>
                                            <NameInput
                                                label={"Имя"}
                                                name={"firstName"}
                                                register={register}
                                                errors={errors as Record<string, FieldError | undefined>}
                                                clearErrors={clearErrors}
                                            />
                                            <NameInput
                                                label={"Фамилия"}
                                                name={"lastName"}
                                                register={register}
                                                errors={errors as Record<string, FieldError | undefined>}
                                                clearErrors={clearErrors}
                                            />
                                        </>
                                    )}
                                    <ContactInput
                                        register={register}
                                        clearErrors={clearErrors}
                                        errors={errors as Record<string, FieldError | undefined>}
                                    />
                                    <PasswordInput
                                        register={register}
                                        errors={errors as Record<string, FieldError | undefined>}
                                        watch={watch}
                                    />
                                    {mode === "register" && (
                                        <PasswordInput
                                            register={register}
                                            errors={errors as Record<string, FieldError | undefined>}
                                            watch={watch}
                                            mode="again"
                                        />
                                    )}
                                </div>
                                <Button type="submit" className={styles.button}>
                                    {mode === "login" ? "Войти" : "Зарегистрироваться"}
                                </Button>
                                {mode === "register" ? (
                                    <p className={styles.paragraph}>
                                        Есть аккаунт?{" "}
                                        <a className={styles.switchMode} onClick={onSwitchMode}>
                                            Вход в аккаунт
                                        </a>
                                    </p>
                                ) : (
                                    <p className={styles.paragraph}>
                                        Нет аккаунта?{" "}
                                        <a className={styles.switchMode} onClick={onSwitchMode}>
                                            Зарегистрироваться
                                        </a>
                                    </p>
                                )}
                            </form>
                        )}
                    </>
                </DialogHeader>
            </DialogContent>
        </Dialog>
    );
}