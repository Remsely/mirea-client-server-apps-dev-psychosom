"use client";

import styles from "./PasswordInput.module.scss"
import {useState} from "react";
import {Eye, EyeOff} from "lucide-react";
import {FieldError, FieldValues, UseFormRegister, UseFormWatch} from "react-hook-form";

interface PasswordInputProps {
    register: UseFormRegister<FieldValues>;
    errors: Record<string, FieldError | undefined>;
    watch: UseFormWatch<FieldValues>;
    mode?: string;
}

export function PasswordInput(props: PasswordInputProps) {
    const [showPassword, setShowPassword] = useState(false);

    const toggleShowPassword = () => {
        setShowPassword((prev) => !prev);
    };

    return (
        <>
            <div className={styles.row}>
                <h2 className={styles.title}>{props.mode === "again" ? "Повторите пароль" : "Пароль"}</h2>
                <div className={styles.input}>
                    <input
                        type={showPassword ? "text" : "password"}
                        {...props.register(
                            props.mode === "again" ? "passwordAgain" : "password",
                            props.mode === "again"
                                ? {
                                    required: "Это поле обязательное",
                                    validate: (value) =>
                                        value === props.watch("password") || "Пароли не совпадают",
                                }
                                : {
                                    required: "Это поле обязательное",
                                    pattern: {
                                        value: /^.{8,}$/,
                                        message: "Пароль должен быть не менее 8 символов",
                                    },
                                }
                        )}
                    />
                    {props.errors[props.mode === "again" ? "passwordAgain" : "password"] && (
                        <p className={styles.error}>{props.errors[props.mode === "again" ? "passwordAgain" : "password"]?.message}</p>
                    )}
                    <i className={styles.eye} onClick={toggleShowPassword}>
                        {showPassword ? <EyeOff /> : <Eye />}
                    </i>
                </div>
            </div>
        </>
    );
}