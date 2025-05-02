import {useMemo} from "react";
import {addDays, isSameDay, startOfWeek} from "date-fns";
import {cn} from "@/shared/utils";
import styles from "./WeekStep.module.scss";

const weekDays = (base: Date, offset: number) =>
    Array.from({length: 7}, (_, i) =>
        addDays(addDays(startOfWeek(base, {weekStartsOn: 1}), offset * 7), i),
    );

export type DisableFn = (d: Date) => boolean;

interface WeekStepProps {
    baseDate: Date;
    offset: number;
    onOffsetChange: (n: number) => void;
    disableDate: DisableFn;
    selectedDate: Date;
    onSelectDate: (d: Date) => void;
}

export function WeekStep({
                      baseDate,
                      offset,
                      disableDate,
                      selectedDate,
                      onSelectDate,
                  }: WeekStepProps) {
    const days = useMemo(() => weekDays(baseDate, offset), [baseDate, offset]);

    return (
        <>
            <div className={styles.weekStrip}>
                {days.map((d) => {
                    const disabled = disableDate(d);
                    const active = isSameDay(d, selectedDate);
                    return (
                        <button
                            key={d.toISOString()}
                            type="button"
                            className={cn(styles.dayBtn, active && styles.dayBtnActive, disabled && styles.dayBtnDisabled)}
                            onClick={() => !disabled && onSelectDate(d)}
                        >
                            {d.toLocaleDateString("ru-RU", {
                                weekday: "short",
                                day: "2-digit",
                            })}
                        </button>
                    );
                })}
            </div>
        </>
    );
};