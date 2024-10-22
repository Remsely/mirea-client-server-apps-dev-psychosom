import {FieldError, FieldValues, SubmitHandler, useForm} from "react-hook-form";
import {useEffect, useState} from "react";
import Cookies from "js-cookie";
import styles from "./ConsultationForm.module.scss";
import ContactInputForm from "./ContactInputForm/ContactInputForm.tsx";
import NameInputForm from "./NameInputForm/NameInputForm.tsx";
import TextareaForm from "./TextareaForm/TextareaForm.tsx";
import ButtonMaster from "../ButtonMaster/ButtonMaster.tsx";
import FrameTitle from "../FrameTitle/FrameTitle.tsx";
import {Cookie} from "../../../enums/cookie.ts";

interface ConsultationFormProps {
    setIsOpen: (isOpen: boolean) => void;
    isOpen: boolean;
}

export default function ConsultationForm({setIsOpen, isOpen}: ConsultationFormProps) {
    const {register, handleSubmit, reset, formState: {errors}, clearErrors} = useForm({
        mode: "onBlur",
    });

    const [isTelegram, setIsTelegram] = useState<boolean>(false);
    const [contactValue, setContactValue] = useState<string>("");
    const [isSubmitted, setIsSubmitted] = useState<boolean>(false);

    useEffect(() => {
        const formSubmitted = Cookies.get(Cookie.formSubmitted);
        if (formSubmitted === 'true') {
            setIsSubmitted(true);
        }
    }, []);

    const onSubmit: SubmitHandler<FieldValues> = (data: object) => {
        console.log(data);
        setIsOpen(true);

        setIsSubmitted(true);
        Cookies.set(Cookie.formSubmitted, 'true', {expires: 1});

        reset();
        setContactValue("");
    };

    return (
        isSubmitted && !isOpen ? (
            <div id="consultation" className={`${styles.success} container`}>
                <h2 className={styles.title}>Вы уже записаны на консультацию!</h2>
                <p className={styles.text}>Мы свяжемся с вами в ближайшее время.</p>
            </div>
        ) : (
            <>
                <FrameTitle id="consultation">Запишитесь на консультацию</FrameTitle>
                <form onSubmit={handleSubmit(onSubmit)} method="POST" className={`${styles.form} container`}>
                    <div className={`${styles.inputs} block`}>
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
                    <div className={`${styles.textarea} block`}>
                        <TextareaForm
                            label="Опишите свою проблему"
                            name="message"
                            register={register}
                            errors={errors as Record<string, FieldError | undefined>}
                        />
                        <ButtonMaster type="submit">Записаться</ButtonMaster>
                    </div>
                </form>
            </>
        )
    );
}
