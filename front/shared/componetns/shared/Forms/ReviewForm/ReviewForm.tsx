"use client";

import styles from "./ReviewForm.module.scss";
import { FieldError, FieldValues, SubmitHandler, useForm } from "react-hook-form";
import { useState } from "react";
import { NameInput, StarRatingInput, TextInput } from "@/shared/componetns/shared/Inputs";
import { Button } from "@/shared/componetns/ui";
import { SubmitMessage } from "@/shared/componetns/shared";
import useDialogStore from "@/shared/stores/dialogStore";
import { Review } from "@/@types/types";
import { useSession } from "next-auth/react";
import { toast } from "react-hot-toast";

export function ReviewForm() {
    const { data: session } = useSession();
    const [isSubmitted, setIsSubmitted] = useState(false);
    const setTitle = useDialogStore((state) => state.setTitle);

    const {register, handleSubmit, reset, formState: { errors }, clearErrors, setValue} = useForm({ mode: "onBlur" });

    const handleErrorResponse = (status: number) => {
        const errorMessages: Record<number, string> = {
            409: "Ваш аккаунт не заполнен. Заполните данные, прежде чем оставить отзыв.",
            401: "Прежде чем оставить отзыв, пожалуйста, войдите в аккаунт.",
            400: "Прежде чем оставить отзыв, сеанс со специалистом должен состояться или вы уже оставляли отзыв.",
        };

        const message = errorMessages[status] || "Не удалось отправить отзыв. Попробуйте позже.";
        toast.error(message, { duration: 3000 });
    };

    const onSubmit: SubmitHandler<FieldValues> = async (data: Review) => {
        try {
            const reviewData = { rating: data.rating, text: data.text };
            const response = await fetch(`${process.env.NEXT_PUBLIC_REST_URL}/api/v1/psychologists/1/reviews`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${session?.user.jwtToken}`,
                },
                body: JSON.stringify(reviewData),
            });

            if (!response.ok) {
                handleErrorResponse(response.status);
                return;
            }

            setIsSubmitted(true);
            toast.success("Вы успешно оставили отзыв!")
            setTitle("");
            reset();
        } catch (error) {
            console.error("Ошибка при отправке отзыва:", error);
            toast.error("Произошла ошибка. Попробуйте позже.", { duration: 3000 });
        }
    };

    const handleRatingSelect = (rating: number) => setValue("rating", rating);

    return !isSubmitted ? (
        <form onSubmit={handleSubmit(onSubmit)} method="POST" className={`${styles.form} ${styles.block}`}>
            <div className={styles.flexDiv}>
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
                    <StarRatingInput onRatingSelect={handleRatingSelect} />
                </div>
                <div className={`${styles.textarea} block-modal`}>
                    <TextInput
                        label="Комментарий к отзыву"
                        name="text"
                        register={register}
                        errors={errors as Record<string, FieldError | undefined>}
                    />
                </div>
            </div>
            <Button className={styles.submitButton} type="submit">
                Оставить отзыв
            </Button>
        </form>
    ) : (
        <SubmitMessage title="Вы успешно оставили отзыв!">
            Спасибо за отзыв! Мы очень ценим это!
        </SubmitMessage>
    );
}