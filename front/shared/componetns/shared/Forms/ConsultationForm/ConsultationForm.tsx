"use client";

import {CircleAlert, ChevronLeft, ChevronRight} from "lucide-react";
import {toast} from "react-hot-toast";
import {useSession} from "next-auth/react";
import styles from "./ConsultationForm.module.scss";
import {cn} from "@/shared/utils";
import {useCallback, useMemo, useState} from "react";
import {isSameDay} from "date-fns";
import {
    FieldValues,
    SubmitHandler,
    useForm,
    FieldError,
} from "react-hook-form";
import {
    BackendSlot,
    usePsychologistSchedule,
    useScheduleConsultation,
} from "@/shared/hooks";
import {
    Button,
    Calendar,
    Dialog,
    DialogContent,
    DialogHeader,
    DialogTitle,
    FrameTitle,
    TextInput,
    SlotList, DisableFn, WeekStep
} from "@/shared/componetns";

interface ConsultationFormProps {
    id: number;
    specialistName: string;
    setIsOpenAuthModal: (v: boolean) => void;
}

export function ConsultationForm({
                                     id,
                                     specialistName,
                                     setIsOpenAuthModal,
                                 }: ConsultationFormProps) {
    const {data: schedule, isLoading} = usePsychologistSchedule(id);
    const {data: session} = useSession();
    const {scheduleConsultation, isScheduling} = useScheduleConsultation({psychologistId: id});

    const [selectedDate, setSelectedDate] = useState<Date | undefined>();
    const [selectedSlot, setSelectedSlot] = useState<BackendSlot | null>(null);
    const [weekOffset, setWeekOffset] = useState(0);
    const [dialog, toggleDialog] = useState(false);

    const {
        register,
        handleSubmit,
        reset,
        formState: {errors},
    } = useForm({mode: "onBlur"});

    const availableDates = useMemo(
        () => schedule?.map((d) => d.jsDate) ?? [],
        [schedule],
    );

    const disableDate = useCallback<DisableFn>(
        (d) => !availableDates.some((av) => isSameDay(av, d)),
        [availableDates],
    );

    const daySlots = useMemo<BackendSlot[]>(() => {
        if (!selectedDate || !schedule) return [];
        return schedule.find((d) => isSameDay(d.jsDate, selectedDate))?.slots ?? [];
    }, [selectedDate, schedule]);

    const onSubmit: SubmitHandler<FieldValues> = (data) => {
        if (!session) {
            setIsOpenAuthModal(true);
            toast(
                "Прежде чем записаться к специалисту, пожалуйста, войдите в аккаунт",
                {icon: <CircleAlert/>, className: styles.toast},
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
                date: datePart,
                startTm: `${selectedSlot.start}:00`,
                endTm: `${selectedSlot.end}:00`,
                problemDescription: data.message,
            },
            {
                onSuccess: () => {
                    reset();
                    setSelectedDate(undefined);
                    setSelectedSlot(null);
                    setWeekOffset(0);
                    toggleDialog(true);
                },
            },
        );
    };

    return (
        <>
            <FrameTitle id="consultation">{specialistName} | Запись на консультацию</FrameTitle>

            <Dialog open={dialog} onOpenChange={toggleDialog}>
                <DialogContent>
                    <DialogHeader>
                        <DialogTitle>Поздравляем, вы записаны!</DialogTitle>
                        <p className={styles.textModal}>
                            Вы успешно записаны на консультацию к специалисту <b>{specialistName}</b>. Перейди в телеграмм-бот, чтобы продолжить запись.
                        </p>
                    </DialogHeader>
                </DialogContent>
            </Dialog>

            <div className={styles.formWrapper}>
                <form onSubmit={handleSubmit(onSubmit)} className={styles.form}>
                    <div className={cn(styles.leftColumn, styles.block)}>
                        {!selectedDate && (
                            <Calendar
                                mode="single"
                                disabled={disableDate}
                                onSelect={(d) => {
                                    setSelectedDate(d);
                                    setWeekOffset(0);
                                }}
                                className={styles.calendar}
                            />
                        )}

                        {selectedDate && (
                            <div>
                                <div className={styles.weekHeader}>
                                    <button
                                        type="button"
                                        className={styles.backBtn}
                                        onClick={() => {
                                            setSelectedDate(undefined);
                                            setSelectedSlot(null);
                                            setWeekOffset(0);
                                        }}
                                    >
                                        <ChevronLeft size={26}/> Назад
                                    </button>
                                    <div className={styles.weekButtons}>
                                        <button
                                            type="button"
                                            className="rdp-button_next"
                                            onClick={() => setWeekOffset(weekOffset - 1)}
                                        >
                                            <ChevronLeft size={24}/>
                                        </button>
                                        <button
                                            type="button"
                                            className="rdp-button_previous"
                                            onClick={() => setWeekOffset(weekOffset + 1)}
                                        >
                                            <ChevronRight size={24}/>
                                        </button>
                                    </div>
                                </div>
                                <WeekStep
                                    baseDate={selectedDate}
                                    offset={weekOffset}
                                    onOffsetChange={setWeekOffset}
                                    disableDate={disableDate}
                                    selectedDate={selectedDate}
                                    onSelectDate={(d) => {
                                        setSelectedDate(d);
                                        setSelectedSlot(null);
                                        setWeekOffset(0);
                                    }}
                                />
                                <SlotList
                                    loading={isLoading}
                                    slots={daySlots}
                                    selectedSlot={selectedSlot}
                                    onSelect={setSelectedSlot}
                                />
                            </div>
                        )}
                    </div>

                    <div className={cn(styles.textarea, styles.block)}>
                        <TextInput
                            label="Опишите свою проблему (необязательно)"
                            name="message"
                            register={register}
                            errors={errors as Record<string, FieldError | undefined>}
                        />
                        <Button
                            type="submit"
                            className={styles.buttonForm}
                            loading={isScheduling}
                            disabled={isScheduling}
                        >
                            Записаться
                        </Button>
                    </div>
                </form>
            </div>
        </>
    );
}