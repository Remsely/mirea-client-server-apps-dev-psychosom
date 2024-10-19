import styles from "./ButtonMaster.module.scss";

interface ButtonMasterProps {
    type: "button" | "submit";
    children: string;
}

export default function ButtonMaster({children, type} : ButtonMasterProps) {
    return (
        <button className={styles.button} type={type}>{children}</button>
    )
}