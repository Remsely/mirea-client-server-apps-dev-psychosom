import {BackendSlot} from "@/shared/hooks";
import {cn} from "@/shared/utils";
import styles from "./SlotList.module.scss";

interface SlotListProps {
    loading: boolean;
    slots: BackendSlot[];
    selectedSlot: BackendSlot | null;
    onSelect: (s: BackendSlot) => void;
}
export function SlotList({slots, selectedSlot, onSelect,}: SlotListProps) {
    if (!slots.length)
        return <p className={styles.noTimes}>Нет свободных слотов</p>;

    return (
        <div className={styles.timesWrapper}>
            {slots.map((s) => (
                <button
                    key={s.start}
                    type="button"
                    className={cn(
                        styles.timeBtn,
                        selectedSlot?.start === s.start && styles.timeBtnActive,
                    )}
                    onClick={() => onSelect(s)}
                >
                    {s.start} – {s.end}
                </button>
            ))}
        </div>
    );
};