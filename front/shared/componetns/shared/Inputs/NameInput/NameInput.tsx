import {ChangeEvent, KeyboardEvent} from "react";
import {FieldError, FieldValues, UseFormClearErrors, UseFormRegister} from "react-hook-form";
import styles from "./NameInput.module.scss";
import {Input} from "@/shared/componetns/ui";

interface NameInputProps {
    label: string;
    name: string;
    register: UseFormRegister<FieldValues>;
    errors: Record<string, FieldError | undefined>;
    clearErrors: UseFormClearErrors<FieldValues>;
}

export function NameInput(props: NameInputProps) {
    const translateToCyrillic = (input: string): string => {
        const translationMap: { [key: string]: string } = {
            q: 'й', w: 'ц', e: 'у', r: 'к', t: 'е', y: 'н', u: 'г', i: 'ш', o: 'щ', p: 'з', "[": "х", "]": "ъ",
            a: 'ф', s: 'ы', d: 'в', f: 'а', g: 'п', h: 'р', j: 'о', k: 'л', l: 'д', ";": "ж", "'": "э",
            z: 'я', x: 'ч', c: 'с', v: 'м', b: 'и', n: 'т', m: 'ь', ".": "ю", ",": "б",

            Q: 'Й', W: 'Ц', E: 'У', R: 'К', T: 'Е', Y: 'Н', U: 'Г', I: 'Ш', O: 'Щ', P: 'З', "{": "Х", "}": "Ъ",
            A: 'Ф', S: 'Ы', D: 'В', F: 'А', G: 'П', H: 'Р', J: 'О', K: 'Л', L: 'Д', ":": "Ж", "\"": "Э",
            Z: 'Я', X: 'Ч', C: 'С', V: 'М', B: 'И', N: 'Т', M: 'Ь', "<": "Ю", ">": "Б",
        };

        return input.split('').map(char => translationMap[char] || char).join('');
    };

    const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
        const fullValue = e.target.value.slice(0, -1);
        const lastChar = e.target.value.slice(-1);
        e.target.value = fullValue + translateToCyrillic(lastChar).replace(/[^а-яА-Я-]/g, '');

        if (e.target.value.length > 0) {
            e.target.value = e.target.value[0].toUpperCase() + e.target.value.slice(1);
        }

        props.clearErrors(props.name);
    };

    const handleKeyDown = (e: KeyboardEvent<HTMLInputElement>) => {
        if (e.key === 'Backspace' && e.currentTarget.value.length === 1) {
            e.currentTarget.value = '';
        }
    };

    return (
        <div className={styles.row}>
            <h2 className={styles.title}>{props.label}</h2>
            <Input
                type="text"
                placeholder={props.label}
                onKeyDown={handleKeyDown}
                maxLength={255}
                {...props.register(props.name, {
                    required: "Это поле обязательное",
                    pattern: {
                        value: /^[a-zA-Zа-яА-Я-]*$/,
                        message: "Допустимы только буквы и дефис",
                    },
                    onChange: handleChange,
                })}
            />
            {props.errors[props.name] && <p className={styles.error}>{(props.errors[props.name] as FieldError).message}</p>}
        </div>
    );
}
