"use client";

import styles from "./ReviewModalForm.module.scss"
import {FieldError, FieldValues, SubmitHandler, useForm} from "react-hook-form";
import {useState} from "react";
import {StarRatingInput, TextInput} from "@/shared/componetns/shared/Inputs";
import {Button, Dialog, DialogContent, DialogHeader, DialogTitle} from "@/shared/componetns/ui";
import {Review} from "@/@types/types";
import {toast} from "react-hot-toast";
import {SubmitMessage} from "@/shared/componetns/shared";

interface ReviewModalFormProps {
    isOpen: boolean;
    onClose: () => void;
}

export function ReviewModalForm({ isOpen, onClose } : ReviewModalFormProps) {
    const [isSubmitted, setIsSubmitted] = useState(false);

    const {register, handleSubmit, reset, formState: { errors }, setValue} = useForm({ mode: "onBlur" });

    const handleErrorResponse = (status: number) => {
        const errorMessages: Record<number, string> = {
            // TODO Убрать потом
            409: "Ваш аккаунт не заполнен. Заполните данные, прежде чем оставить отзыв.",
            401: "Прежде чем оставить отзыв, пожалуйста, войдите в аккаунт.",
            400: "Прежде чем оставить отзыв, сеанс со специалистом должен состояться или вы уже оставляли отзыв.",
        };

        const message = errorMessages[status] || "Не удалось отправить отзыв. Попробуйте позже.";
        toast.error(message, { duration: 3000 });
    };

    const onSubmit: SubmitHandler<FieldValues> = async (data: Review) => {
        try {
            if (data.rating === 0 || undefined) {
                toast.error("Пожалуйста, выберите рейтинг.", { duration: 3000 });
                return;
            }
            const reviewData = { rating: data.rating, text: data.text };
            const response = await fetch(`/api/proxy/api/v1/psychologists/1/reviews`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(reviewData),
            });

            if (!response.ok) {
                handleErrorResponse(response.status);
                return
            }

            setIsSubmitted(true);
            toast.success("Вы успешно оставили отзыв!")
            reset();
        } catch (error) {
            console.error("Ошибка при отправке отзыва:", error);
            toast.error("Произошла ошибка. Попробуйте позже.", { duration: 3000 });
        }
    };

    const handleClose = () => {
        onClose();
    };

    const handleRatingSelect = (rating: number) => setValue("rating", rating, { shouldValidate: true });

    return (
        <Dialog open={isOpen} onOpenChange={handleClose}>
            <DialogContent>
                <DialogHeader>
                    <DialogTitle>{isSubmitted ? "Спасибо за отзыв!" : "Оставить отзыв"}</DialogTitle>
                        {!isSubmitted ? (
                            <form onSubmit={handleSubmit(onSubmit)} method="POST" className={`${styles.form} ${styles.block}`}>
                                <div className={styles.flexDiv}>
                                    <div className={styles.inputs}>
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
                        )}
                </DialogHeader>
            </DialogContent>
        </Dialog>
    )
}