import styles from "./ButtonMaster.module.scss";

interface ButtonMasterProps {
    type: "button" | "submit";
    onClick?: () => void;
    children: string;
}

export default function ButtonMaster({children, type, onClick} : ButtonMasterProps) {
    return (
        <button className={styles.button} type={type} onClick={onClick}>{children}</button>
    )
}
