"use client";

import {FieldError, FieldValues, SubmitHandler, useForm} from "react-hook-form";
import {useEffect, useState} from "react";
import Cookies from "js-cookie";
import styles from "./ConsultationForm.module.scss";
import {Button} from "@/shared/componetns/ui";
import {ContactInput, NameInput, TextInput} from "@/shared/componetns/shared/Inputs";
import {Cookie} from "@/shared/enums/cookie";
import {FrameTitle, SubmitMessage} from "@/shared/componetns/shared";
import {useSession} from "next-auth/react";
import {toast} from "react-hot-toast";
import {CircleAlert} from "lucide-react";

interface ConsultationFormProps {
    setIsOpen: (isOpen: boolean) => void;
    isOpen: boolean;
}

export function ConsultationForm(props: ConsultationFormProps) {
    const {data: session} = useSession();
    const {register, handleSubmit, reset, formState: {errors}, clearErrors} = useForm({
        mode: "onBlur",
    });

    const [contactValue, setContactValue] = useState<string>("");
    const [isSubmitted, setIsSubmitted] = useState<boolean>(false);

    useEffect(() => {
        const formSubmitted = Cookies.get(Cookie.consultationFormSubmitted);
        if (formSubmitted === 'true') {
            setIsSubmitted(true);
        }
    }, []);

    const onSubmit: SubmitHandler<FieldValues> = (data: FieldValues) => {
        if (session) {
            if (data.phone) {
                data.phone = data.phone.replace(/[^0-9]/g, "");
            }
            props.setIsOpen(true);
            setIsSubmitted(true);
            Cookies.set(Cookie.consultationFormSubmitted, 'true', {expires: 1});
            reset();
            setContactValue("");
        } else {
            props.setIsOpen(true);
            toast("Прежде чем записаться на консультацию, пожалуйста, войдите в аккаунт", {
                icon: <CircleAlert />,
                duration: 3000,
                className: styles.toast
            })
        }
    };

    return (
        isSubmitted && !props.isOpen ? (
            <>
                <i id="consultation"></i>
                <SubmitMessage title="Вы уже записаны на консультацию!">Мы свяжемся с вами в ближайшее
                    время</SubmitMessage>
            </>
        ) : (
            <>
                <FrameTitle id="consultation">Запишитесь на консультацию</FrameTitle>
                <div className={styles.formWrapper}>
                    <form onSubmit={handleSubmit(onSubmit)} className={styles.form}>
                        <div className={`${styles.leftColumn} ${styles.block}`}>
                            <div className={styles.inputs}>
                                <NameInput
                                    label="Имя"
                                    name="firstname"
                                    register={register}
                                    errors={errors as Record<string, FieldError | undefined>}
                                    clearErrors={clearErrors}
                                />
                                <NameInput
                                    label="Фамилия"
                                    name="lastname"
                                    register={register}
                                    errors={errors as Record<string, FieldError | undefined>}
                                    clearErrors={clearErrors}
                                />
                                <ContactInput
                                    contactValue={contactValue}
                                    setContactValue={setContactValue}
                                    register={register}
                                    errors={errors as Record<string, FieldError | undefined>}
                                    clearErrors={clearErrors}
                                />
                            </div>
                        </div>

                        <div className={`${styles.textarea} ${styles.block}`}>
                            <TextInput
                                label="Опишите свою проблему"
                                name="message"
                                register={register}
                                errors={errors as Record<string, FieldError | undefined>}
                            />
                            <Button className={styles.buttonForm} type="submit">Записаться</Button>
                        </div>
                    </form>
                </div>
            </>
        )
    );
}
