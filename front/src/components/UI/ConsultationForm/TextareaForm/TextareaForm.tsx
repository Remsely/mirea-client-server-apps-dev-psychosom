import {FieldError, FieldValues, UseFormRegister} from "react-hook-form";
import styles from "../ConsultationForm.module.scss";

interface TextareaFormProps {
    label: string;
    name: string;
    register: UseFormRegister<FieldValues>;
    errors: Record<string, FieldError | undefined>;
}

export default function TextareaForm({ label, name, register, errors }: TextareaFormProps) {
    return (
        <>
            <h2 className={styles.p}>{label}</h2>
            <textarea
                maxLength={2047}
                {...register(name)}
                placeholder={label}
            ></textarea>
            {errors[name] && <p className={styles.error}>{(errors[name] as FieldError).message}</p>}
        </>
    );
}
