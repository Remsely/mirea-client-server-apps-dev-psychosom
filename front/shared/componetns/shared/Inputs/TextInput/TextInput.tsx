import {FieldError, FieldValues, UseFormRegister} from "react-hook-form";
import styles from "./TextInput.module.scss";

interface TextInputProps {
    label: string;
    name: string;
    register: UseFormRegister<FieldValues>;
    errors: Record<string, FieldError | undefined>;
}

export function TextInput(props: TextInputProps) {
    return (
        <>
            <h2 className={styles.title}>{props.label}</h2>
            <textarea className={styles.textarea}
                maxLength={2047}
                {...props.register(props.name)}
                placeholder={props.label}
            ></textarea>
            {props.errors[props.name] && <p className={styles.error}>{(props.errors[props.name] as FieldError).message}</p>}
        </>
    );
}
