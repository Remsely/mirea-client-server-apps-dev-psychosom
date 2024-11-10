"use client";

import styles from "./ModalReviewForm.module.scss";
import { FieldError, FieldValues, SubmitHandler, useForm } from "react-hook-form";
import { useEffect, useState} from "react";
import Cookies from "js-cookie";
import { Cookie } from "@/shared/enums/cookie";
import { NameInputForm, TextareaForm } from "@/shared/componetns/ui/ConsultationForm";
import { StarRating } from "@/shared/componetns/ui/ReviewForm";
import { ButtonMaster, SubmitMessage } from "@/shared/componetns/ui";

interface ModalReviewFormProps {
    setIsSuccess: (isSuccess: boolean) => void;
}

export function ModalReviewForm( props : ModalReviewFormProps) {
    const [isSubmitted, setIsSubmitted] = useState<boolean>(false);
    const [isCookies, setIsCookies] = useState<boolean>(false);

    const { register, handleSubmit, reset, formState: { errors }, clearErrors, setValue } = useForm({
        mode: "onBlur",
    });

    useEffect(() => {
        const formSubmitted = Cookies.get(Cookie.reviewFormSubmitted);
        if (formSubmitted === 'true') {
            setIsSubmitted(true);
            setIsCookies(true);
            props.setIsSuccess(true);
        }
    }, []);

    const onSubmit: SubmitHandler<FieldValues> = (data: object) => {
        console.log(data);
        props.setIsSuccess(true);
        setIsSubmitted(true);
        Cookies.set(Cookie.reviewFormSubmitted, "true", { expires: 1 });
        reset();
    };

    const handleRatingSelect = (rating: number) => {
        setValue("rating", rating);
    };

    return (
        <>
            {!isSubmitted ? (
                <form onSubmit={handleSubmit(onSubmit)} method="POST" className={`${styles.form} ${styles.block}`}>
                    <div className={styles.inputs}>
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
                        <StarRating onRatingSelect={handleRatingSelect} />
                    </div>
                    <div className={`${styles.textarea} block-modal`}>
                        <TextareaForm
                            label="Комментарий к отзыву"
                            name="message"
                            register={register}
                            errors={errors as Record<string, FieldError | undefined>}
                        />
                        <div className={styles.buttonWrapper}>
                            <ButtonMaster className={styles.button} type="submit">Оставить отзыв</ButtonMaster>
                        </div>
                    </div>
                </form>
            ) : (!isCookies ? (
                    <SubmitMessage title="Вы успешно оставили отзыв!">Спасибо за отзыв! Мы очень ценим это!</SubmitMessage>
                ) : (
                    <SubmitMessage title="Вы уже оставили отзыв!">Если вам надо поменять отзыв, пожалуйста, обратитесь к тех. поддержку</SubmitMessage>
                )
            )}
        </>
    );
}
