import styles from "./SubmitMessage.module.scss";

interface SubmitMessageProps {
    title?: string
    children?: string
}

export function SubmitMessage({title, children}: SubmitMessageProps) {
    return (
        <div className={styles.success} style={{padding: title === "Вы уже записаны на консультацию!" ? "225px 0" : "20px 0"}}>
            <h2>{title}</h2>
            <p>{children}</p>
        </div>
    )
}
