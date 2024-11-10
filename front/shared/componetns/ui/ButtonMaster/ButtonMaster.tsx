import styles from "./ButtonMaster.module.scss";

interface ButtonMasterProps {
    type: "button" | "submit";
    onClick?: () => void;
    children: string;
    className?: string;
}

export function ButtonMaster({children, type, onClick, className} : ButtonMasterProps) {
    return (
        <button className={`${styles.button} ${className}`} type={type} onClick={onClick}>{children}</button>
    )
}
