import {ContactInput, PasswordInput} from "@/shared/componetns/shared/Inputs";
import {FieldError, FieldValues, SubmitHandler, useForm} from "react-hook-form";
import {Button} from "@/shared/componetns/ui";
import styles from "./AuthForm.module.scss";
import {toast} from "react-hot-toast";
import {useEffect, useState} from "react";
import {signIn, SignInResponse, signOut, useSession} from "next-auth/react";
import useDialogStore from "@/shared/stores/dialogStore";
import {QRCodeSection} from "@/shared/componetns/shared/Forms/AuthForm/QRCodeSection/QRCodeSection";

export function AuthForm() {
    const { register, formState: { errors }, clearErrors, handleSubmit, watch, reset } = useForm({ mode: "onBlur" });
    const { setTitle } = useDialogStore();
    const { data: session } = useSession();

    const [qrLink, setQrLink] = useState<string | null>(null);
    const [mode, setMode] = useState<"login" | "register">("login");
    const [dataAfterRegistration, setDataAfterRegistration] = useState<FieldValues | null>(null);

    useEffect(() => setTitle(mode === "login" ? "Вход в аккаунт" : "Регистрация"), [mode, setTitle]);

    useEffect(() => {
        if (session?.user.webSocketToken) {
            signOut({redirect: false})
            const ws = new WebSocket(`ws://${process.env.NEXT_PUBLIC_BACKEND_URL}/ws/auth/confirmation?token=${session.user.webSocketToken}`);
            ws.onopen = () => console.log("WebSocket подключен");
            ws.onclose = async () => {
                console.log("WebSocket отключён");
                if (dataAfterRegistration) onSubmit(dataAfterRegistration);
            }
            ws.onerror = (error) => console.error("Ошибка WebSocket:", error);
        }
    }, [session, dataAfterRegistration]);

    useEffect(() => {
        if (session?.user.tbBotConfirmationUrl) {
            setQrLink(session.user.tbBotConfirmationUrl);
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
            setDataAfterRegistration(data);
            await signIn("credentials", {...credentials, isRegister: true});
            onSwitchMode();
        } else {
            const result = await signIn("credentials", credentials);
            handleAuthResult(result);
        }
    };

    const handleAuthResult = (result: SignInResponse | undefined) => {
        if (result?.status === 401 && !qrLink) return toast.error("Ошибка авторизации: Неверный логин или пароль");

        if (result?.ok) {
            toast.success("Успешная авторизация!");
            setTimeout(() => (window.location.href = "/"), 700);
        }
    };

    const onSwitchMode = () => {
        setMode(mode === 'login' ? 'register' : 'login');
        reset();
    };

    return (
        <>
            <div>
                {qrLink ? (
                    <QRCodeSection qrLink={qrLink}/>
                ) : (
                    <>
                        <form onSubmit={handleSubmit(onSubmit)} className={styles.form}>
                            <div className={styles.inputs}>
                                <ContactInput register={register} clearErrors={clearErrors}
                                              errors={errors as Record<string, FieldError | undefined>}/>
                                <PasswordInput register={register}
                                               errors={errors as Record<string, FieldError | undefined>} watch={watch}/>
                                {mode === "register" && (
                                    <PasswordInput register={register}
                                                   errors={errors as Record<string, FieldError | undefined>}
                                                   watch={watch} mode="again"/>
                                )}
                            </div>
                            <Button type="submit" className={styles.button}>{
                                mode === "login"
                                    ? "Войти"
                                    : "Зарегистрироваться"}</Button>
                            {mode === "register"
                                ? (<p className={styles.paragraph}>Есть аккаунт? <a className={styles.switchMode}
                                                                                    onClick={onSwitchMode}>Вход в
                                    аккаунт</a></p>)
                                : (<p className={styles.paragraph}>Нет аккаунта? <a className={styles.switchMode}
                                                                                    onClick={onSwitchMode}>Зарегистрироваться</a>
                                </p>)}
                        </form>
                    </>
                )}
            </div>
        </>
    )
}