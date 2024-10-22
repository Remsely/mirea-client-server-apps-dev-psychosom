import { ChangeEvent, KeyboardEvent } from "react";
import {FieldError, FieldValues, UseFormClearErrors, UseFormRegister} from "react-hook-form";
import styles from "../ConsultationForm.module.scss";

interface NameInputProps {
    label: string;
    name: string;
    register: UseFormRegister<FieldValues>;
    errors: Record<string, FieldError | undefined>;
    clearErrors: UseFormClearErrors<FieldValues>;
}

export default function NameInputForm({ label, name, register, errors, clearErrors }: NameInputProps) {
    const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
        e.target.value = e.target.value.replace(/[^а-яА-Я-]/g, '');
        clearErrors(name);
    };

    const handleKeyDown = (e: KeyboardEvent<HTMLInputElement>) => {
        if (e.key === 'Backspace' && e.currentTarget.value.length === 1) {
            e.currentTarget.value = '';
        }
    };

    return (
        <div className={styles.row}>
            <h2>{label}</h2>
            <input
                type="text"
                placeholder={label}
                onKeyDown={handleKeyDown}
                maxLength={255}
                {...register(name, {
                    required: "Это поле обязательное",
                    pattern: {
                        value: /^[а-яА-Я-]*$/,
                        message: "Допустимы только буквы и дефис",
                    },
                    onChange: handleChange,
                })}
            />
            {errors[name] && <p className={styles.error}>{(errors[name] as FieldError).message}</p>}
        </div>
    );
}
