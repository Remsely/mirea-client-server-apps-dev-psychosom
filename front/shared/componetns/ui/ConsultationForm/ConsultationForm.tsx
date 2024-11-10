"use client";

import {FieldError, FieldValues, SubmitHandler, useForm} from "react-hook-form";
import {useEffect, useState} from "react";
import Cookies from "js-cookie";
import styles from "./ConsultationForm.module.scss";
import {ButtonMaster, FrameTitle, SubmitMessage} from "@/shared/componetns/ui";
import {ContactInputForm, NameInputForm, TextareaForm} from "@/shared/componetns/ui/ConsultationForm";
import {Cookie} from "@/shared/enums/cookie";

interface ConsultationFormProps {
    setIsOpen: (isOpen: boolean) => void;
    isOpen: boolean;
}

export function ConsultationForm(props : ConsultationFormProps) {
    const {register, handleSubmit, reset, formState: {errors}, clearErrors} = useForm({
        mode: "onBlur",
    });

    const [isTelegram, setIsTelegram] = useState<boolean>(false);
    const [contactValue, setContactValue] = useState<string>("");
    const [isSubmitted, setIsSubmitted] = useState<boolean>(false);

    useEffect(() => {
        const formSubmitted = Cookies.get(Cookie.consultationFormSubmitted);
        if (formSubmitted === 'true') {
            setIsSubmitted(true);
        }
    }, []);

    const onSubmit: SubmitHandler<FieldValues> = (data: object) => {
        console.log(data);
        props.setIsOpen(true);

        setIsSubmitted(true);
        Cookies.set(Cookie.consultationFormSubmitted, 'true', {expires: 1});

        reset();
        setContactValue("");
    };

    return (
        isSubmitted && !props.isOpen ? (
            <SubmitMessage title="Вы уже записаны на консультацию!">Мы свяжемся с вами в ближайшее время</SubmitMessage>
        ) : (
            <>
                <FrameTitle id="consultation">Запишитесь на консультацию</FrameTitle>
                <form onSubmit={handleSubmit(onSubmit)} method="POST" className={styles.form}>
                    <div className={`${styles.inputs} ${styles.block}`}>
                        <NameInputForm
                            label="Имя"
                            name="firstname"
                            register={register}
                            errors={errors as Record<string, FieldError | undefined>}
                            clearErrors={clearErrors}
                        />
                        <NameInputForm
                            label="Фамилия"
                            name="lastname"
                            register={register}
                            errors={errors as Record<string, FieldError | undefined>}
                            clearErrors={clearErrors}
                        />
                        <ContactInputForm
                            isTelegram={isTelegram}
                            setIsTelegram={setIsTelegram}
                            contactValue={contactValue}
                            setContactValue={setContactValue}
                            register={register}
                            errors={errors as Record<string, FieldError | undefined>}
                            clearErrors={clearErrors}
                        />
                    </div>
                    <div className={`${styles.textarea} ${styles.block}`}>
                        <TextareaForm
                            label="Опишите свою проблему"
                            name="message"
                            register={register}
                            errors={errors as Record<string, FieldError | undefined>}
                        />
                        <ButtonMaster className={styles.buttonForm} type="submit">Записаться</ButtonMaster>
                    </div>
                </form>
            </>
        )
    );
}
