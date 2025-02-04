"use client";

import { ChangeEvent, useState } from "react";
import { FieldError, FieldValues, UseFormClearErrors, UseFormRegister } from "react-hook-form";
import styles from "./ContactInput.module.scss";
import {Input} from "@/shared/componetns/ui";

interface ContactInputProps {
    contactValue?: string;
    setContactValue?: (value: string) => void;
    register: UseFormRegister<FieldValues>;
    errors: Record<string, FieldError | undefined>;
    clearErrors: UseFormClearErrors<FieldValues>;
}

export function ContactInput(props: ContactInputProps) {
    const [isTelegram, setIsTelegram] = useState<boolean>(false);

    const [localContactValue, setLocalContactValue] = useState<string>("");
    const contactValue = props.contactValue ?? localContactValue;
    const setContactValue = props.setContactValue ?? setLocalContactValue;

    const phonePattern = /^(\+7|8)\s?\(?\d{3}\)?\s?\d{3}-?\d{2}-?\d{2}$/;
    const telegramPattern = /^@(?=.{5,})[a-zA-Z0-9]+$/;

    const telegramInputPattern = /[^a-zA-Z0-9]/g;
    const phoneInputPattern = /[^0-9]/g;

    const maskPhoneNumber = (value: string) => {
        const inputNumbersValue = value.replace(phoneInputPattern, '');
        let formatted: string = '';
        formatted += `(${inputNumbersValue.substring(1, 4)}`;
        if (inputNumbersValue.length > 4) formatted += `) ${inputNumbersValue.substring(4, 7)}`;
        if (inputNumbersValue.length > 7) formatted += `-${inputNumbersValue.substring(7, 9)}`;
        if (inputNumbersValue.length > 9) formatted += `-${inputNumbersValue.substring(9, 11)}`;
        return formatted;
    };

    const handleTelegramMode = (value: string) => {
        if (!value.startsWith('@')) {
            return '@' + value.replace(telegramInputPattern, '');
        } if (value.includes('+') && value.length < 3) {
            setIsTelegram(false);
            return maskPhoneNumber(value);
        } else {
            return '@' + value.slice(1).replace(telegramInputPattern, '');
        }
    };

    const handlePhoneMode = (value: string) => {
        if (value.includes('@') && value.length < 6) {
            setIsTelegram(true);
            return '@' + '';
        }
        return maskPhoneNumber(value);
    };

    const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
        let value = e.target.value;
        props.clearErrors('contact');

        if (isTelegram) {
            value = handleTelegramMode(value);
        } else {
            value = handlePhoneMode(value);
        }

        setContactValue(value);
    };

    const handleModeChange = (mode: 'phone' | 'telegram') => {
        setIsTelegram(mode === 'telegram');
        setContactValue(mode === 'telegram' ? '@' : '');
        props.clearErrors('contact');
    };

    return (
        <div className={styles.row}>
            <h2 className={styles.title}>
                <a className={!isTelegram ? styles.active : ""} onClick={() => handleModeChange('phone')}>
                    Телефон
                </a>
                {" "}/{" "}
                <a className={isTelegram ? styles.active : ""} onClick={() => handleModeChange('telegram')}>
                    Telegram
                </a>
            </h2>
            <Input
                type={isTelegram ? "text" : "tel"}
                placeholder={isTelegram ? "Telegram" : "Телефон"}
                maxLength={isTelegram ? 32 : 18}
                minLength={5}
                value={isTelegram ? contactValue : '+7 ' + contactValue}
                {...props.register("contact", {
                    required: "Это поле обязательное",
                    pattern: {
                        value: isTelegram
                            ? telegramPattern
                            : phonePattern,
                        message: isTelegram
                            ? "В Telegram допустимы только латинские буквы и цифры, не менее 5 символов"
                            : "Номер телефона должен быть в формате +7 (XXX) XXX-XX-XX",
                    },
                    onChange: handleChange,
                })}
            />
            {props.errors.contact && <p className={styles.error}>{props.errors.contact.message}</p>}
        </div>
    );
}