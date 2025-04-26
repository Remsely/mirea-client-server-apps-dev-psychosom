"use client";

import {FieldError, FieldValues, SubmitHandler, useForm} from "react-hook-form";
import {useState} from "react";
import styles from "./ConsultationForm.module.scss";
import {Button, Calendar, Dialog, DialogContent, DialogHeader, DialogTitle} from "@/shared/componetns/ui";
import {TextInput} from "@/shared/componetns/shared/Inputs";
import {FrameTitle} from "@/shared/componetns/shared";
import {useSession} from "next-auth/react";
import {toast} from "react-hot-toast";
import {CircleAlert} from "lucide-react";

interface ConsultationFormProps {
    setIsOpenAuthModal: (isOpenAuthModal: boolean) => void;
}

export function ConsultationForm(props: ConsultationFormProps) {
    const [isOpen, setIsOpen] = useState(false);
    const {setIsOpenAuthModal} = props;
    const {data: session} = useSession();
    const [date, setDate] = useState<Date | undefined>(new Date())
    const {register, handleSubmit, reset, formState: {errors}} = useForm({
        mode: "onBlur",
    });

    const onSubmit: SubmitHandler<FieldValues> = async () => {
        if (session) {
            try {
                const response = await fetch(`/api/proxy/api/v1/psychologists/1/consultations`, {
                    method: "POST",
                });

                if (!response.ok) throw new Error('Не удалось записать на консультацию!');

                reset();
                setIsOpen(true);
                toast.success("Вы успешно записаны на консультацию!");
            } catch (error) {
                console.error(error);
                toast.error("Произошла ошибка при записи на консультацию. Пожалуйста, попробуйте позже.");
            }
        } else {
            setIsOpenAuthModal(true);
            toast("Прежде чем записаться к специалисту, пожалуйста, войдите в аккаунт", {
                icon: <CircleAlert/>,
                duration: 3000,
                className: styles.toast
            })
        }
    };

    return (
        <>
            <FrameTitle id="consultation">Запишитесь на консультацию</FrameTitle>

            <Dialog open={isOpen} onOpenChange={() => setIsOpen(false)}>
                <DialogContent>
                    <DialogHeader>
                        <DialogTitle>Поздравляем, вы записаны!</DialogTitle>
                        <p className={styles.textModal}>Вы записались на консультацию к специалисту.
                        Скоро с вами свяжется специалист по методу связи, который вы указали.</p>
                    </DialogHeader>
                </DialogContent>
            </Dialog>

            <div className={styles.formWrapper}>
                <form onSubmit={handleSubmit(onSubmit)} className={styles.form}>
                    <div className={`${styles.leftColumn} ${styles.block}`}>
                        <div className={styles.inputs}>
                            <Calendar mode="single"
                                      selected={date}
                                      onSelect={setDate}/>
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
    );
}
