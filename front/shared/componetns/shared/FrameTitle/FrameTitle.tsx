import styles from "./FrameTitle.module.scss"

interface FrameTitleProps {
    children: string | string[];
    id?: string;
}

export function FrameTitle(props : FrameTitleProps) {
    return (
        <h1 className={styles.title_block} id={props.id}>{props.children}</h1>
    )
}

