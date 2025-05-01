import styles from "./SubmitMessage.module.scss";

interface SubmitMessageProps {
    title?: string
    children?: string
}

export function SubmitMessage(props: SubmitMessageProps) {
    return (
        <div className={styles.success} style={{padding: props.title === "Вы уже записаны на консультацию!" ? "225px" +
                " 0" : "20px 0"}}>
            <h2 className="">{props.title}</h2>
            <p style={{ width: props.title === "Вы уже записаны на консультацию!" ? "auto" : "700px"}}>{props.children}</p>
        </div>
    )
}
