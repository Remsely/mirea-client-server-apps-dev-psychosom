import styles from "./Button.module.scss";
import {ButtonHTMLAttributes, ReactNode} from "react";

interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
    children: ReactNode;
}

export function Button(props : ButtonProps) {
    return (
        <button className={`${styles.button} ${props.className}`} type={props.type} onClick={props.onClick} disabled={props.disabled}>{props.children}</button>
    )
}
