"use client";

import { FieldError, FieldValues, SubmitHandler, useForm } from "react-hook-form";
import { useState } from "react";
import styles from "./ConsultationForm.module.scss";
import { Button, Calendar, Dialog, DialogContent, DialogHeader, DialogTitle } from "@/shared/componetns/ui";
import { TextInput } from "@/shared/componetns/shared/Inputs";
import { FrameTitle } from "@/shared/componetns/shared";
import { useSession } from "next-auth/react";
import { CircleAlert } from "lucide-react";
import {toast} from "react-hot-toast";
import {useScheduleConsultation} from "@/shared/hooks";

interface ConsultationFormProps {
    setIsOpenAuthModal: (isOpenAuthModal: boolean) => void;
}

export function ConsultationForm(props: ConsultationFormProps) {
    const [isOpen, setIsOpen] = useState(false);
    const { setIsOpenAuthModal } = props;
    const { data: session } = useSession();
    const [date, setDate] = useState<Date | undefined>(new Date());
    const { register, handleSubmit, reset, formState: { errors } } = useForm({
        mode: "onBlur",
    });

    const { scheduleConsultation, isScheduling } = useScheduleConsultation();

    const onSubmit: SubmitHandler<FieldValues> = (data) => {
        if (session) {
            if (!date) {
                toast.error("Пожалуйста, выберите дату консультации.");
                return;
            }

            scheduleConsultation(
                {
                    id: 1,
                    startDtTm: "28-04-2025 13:00:00.000",
                    endDtTm: "28-04-2025 13:50:00.000",
                    problemDescription: data.message,
                },
                {
                    onSuccess: () => {
                        reset();
                        setIsOpen(true);
                    },
                }
            );
        } else {
            setIsOpenAuthModal(true);
            toast("Прежде чем записаться к специалисту, пожалуйста, войдите в аккаунт", {
                icon: <CircleAlert />,
                duration: 3000,
                className: styles.toast,
            });
        }
    };

    return (
        <>
            <FrameTitle id="consultation">Запишитесь на консультацию</FrameTitle>

            <Dialog open={isOpen} onOpenChange={() => setIsOpen(false)}>
                <DialogContent>
                    <DialogHeader>
                        <DialogTitle>Поздравляем, вы записаны!</DialogTitle>
                        <p className={styles.textModal}>
                            Вы записались на консультацию к специалисту. Скоро с вами свяжется специалист по методу связи, который вы указали.
                        </p>
                    </DialogHeader>
                </DialogContent>
            </Dialog>

            <div className={styles.formWrapper}>
                <form onSubmit={handleSubmit(onSubmit)} className={styles.form}>
                    <div className={`${styles.leftColumn} ${styles.block}`}>
                        <div className={styles.inputs}>
                            <Calendar mode="single" selected={date} onSelect={setDate} />
                        </div>
                    </div>

                    <div className={`${styles.textarea} ${styles.block}`}>
                        <TextInput
                            label="Опишите свою проблему"
                            name="message"
                            register={register}
                            errors={errors as Record<string, FieldError | undefined>}
                        />
                        <Button className={styles.buttonForm} type="submit" disabled={isScheduling} loading={isScheduling}>
                            Записаться
                        </Button>
                    </div>
                </form>
            </div>
        </>
    );
}
