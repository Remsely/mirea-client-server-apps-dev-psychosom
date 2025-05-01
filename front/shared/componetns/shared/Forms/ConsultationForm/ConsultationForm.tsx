"use client";

import { useState, useMemo } from "react";
import {
    FieldValues,
    SubmitHandler,
    useForm,
    FieldError,
} from "react-hook-form";

import styles from "./ConsultationForm.module.scss";
import {
    Button,
    Calendar,
    Dialog,
    DialogContent,
    DialogHeader,
    DialogTitle,
} from "@/shared/componetns/ui";
import { TextInput } from "@/shared/componetns/shared/Inputs";
import { FrameTitle } from "@/shared/componetns/shared";

import { CircleAlert } from "lucide-react";
import { toast } from "react-hot-toast";
import { useSession } from "next-auth/react";

import Skeleton from "react-loading-skeleton";
import {BackendSlot, usePsychologistSchedule, useScheduleConsultation} from "@/shared/hooks";

interface ConsultationFormProps {
    setIsOpenAuthModal: (v: boolean) => void;
    id: number;
}

export function ConsultationForm({setIsOpenAuthModal, id}: ConsultationFormProps) {
    const { data: session } = useSession();

    const { data: schedule, isLoading } = usePsychologistSchedule(id);

    const [selectedDate, setSelectedDate] = useState<Date>();
    const [selectedSlot, setSelectedSlot] = useState<BackendSlot | null>(null);

    const availableDates = useMemo<Date[]>(
        () => schedule?.map((d) => d.jsDate) ?? [],
        [schedule],
    );

    const daySlots = useMemo<BackendSlot[]>(() => {
        if (!selectedDate || !schedule) return [];
        const day = schedule.find(
            (d) => d.jsDate.toDateString() === selectedDate.toDateString(),
        );
        return day?.slots ?? [];
    }, [selectedDate, schedule]);

    const {
        register,
        handleSubmit,
        reset,
        formState: { errors },
    } = useForm({ mode: "onBlur" });

    const { scheduleConsultation, isScheduling } = useScheduleConsultation();
    const [isOpen, setIsOpen] = useState(false);

    const onSubmit: SubmitHandler<FieldValues> = (data) => {
        if (!session) {
            setIsOpenAuthModal(true);
            toast(
                "Прежде чем записаться к специалисту, пожалуйста, войдите в аккаунт",
                { icon: <CircleAlert />, className: styles.toast },
            );
            return;
        }
        if (!selectedDate || !selectedSlot) {
            toast.error("Выберите дату и время консультации");
            return;
        }

        const [d, m, y] = selectedDate.toLocaleDateString("ru-RU").split(".");
        const datePart = `${d}-${m}-${y}`;

        scheduleConsultation(
            {
                id,
                startDtTm: `${datePart} ${selectedSlot.start}:00.000`,
                endDtTm: `${datePart} ${selectedSlot.end}:00.000`,
                problemDescription: data.message,
            },
            {
                onSuccess: () => {
                    reset();
                    setSelectedDate(undefined);
                    setSelectedSlot(null);
                    setIsOpen(true);
                },
            },
        );
    };

    const disableUnavailable = (d: Date) =>
        !availableDates.some((av) => av.toDateString() === d.toDateString());

    return (
        <>
            <FrameTitle id="consultation">Запишитесь на консультацию</FrameTitle>

            <Dialog open={isOpen} onOpenChange={setIsOpen}>
                <DialogContent>
                    <DialogHeader>
                        <DialogTitle>Поздравляем, вы записаны!</DialogTitle>
                        <p className={styles.textModal}>
                            Скоро с вами свяжется специалист по выбранному способу связи.
                        </p>
                    </DialogHeader>
                </DialogContent>
            </Dialog>

            <div className={styles.formWrapper}>
                <form onSubmit={handleSubmit(onSubmit)} className={styles.form}>
                    <div className={`${styles.leftColumn} ${styles.block}`}>
                        {isLoading ? (
                            <Skeleton className={styles.calendarSkeleton} height={320} />
                        ) : (
                            <Calendar
                                mode="single"
                                selected={selectedDate}
                                onSelect={(d) => {
                                    setSelectedDate(d);
                                    setSelectedSlot(null);
                                }}
                                disabled={disableUnavailable}
                                className={styles.calendar}
                            />
                        )}
                    </div>
                    <div className={`${styles.rightColumn} ${styles.block}`}>
                        <div className={styles.timesWrapper}>
                            {isLoading && (
                                <Skeleton
                                    className={styles.timeSkeleton}
                                    width={220}
                                    height={20}
                                    count={3}
                                />
                            )}

                            {!isLoading && daySlots.length === 0 && (
                                <p className={styles.noTimes}>Нет свободных слотов</p>
                            )}

                            {daySlots.map((s) => (
                                <button
                                    key={s.start}
                                    type="button"
                                    onClick={() => setSelectedSlot(s)}
                                    className={`${styles.timeBtn} ${
                                        selectedSlot?.start === s.start ? styles.timeBtnActive : ""
                                    }`}
                                >
                                    {s.start}
                                </button>
                            ))}
                        </div>
                        <TextInput
                            label="Опишите свою проблему"
                            name="message"
                            register={register}
                            errors={errors as Record<string, FieldError | undefined>}
                        />
                        <Button
                            className={styles.buttonForm}
                            type="submit"
                            disabled={isScheduling}
                            loading={isScheduling}
                        >
                            Записаться
                        </Button>
                    </div>
                </form>
            </div>
        </>
    );
}