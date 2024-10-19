import styles from "./FrameTitle.module.scss"

interface FrameTitleProps {
    children: string;
    id?: string;
}

export default function FrameTitle({ children, id } : FrameTitleProps) {
    return (
        <h1 className={`${styles.title_block} container`} id={id}>{children}</h1>
    )
}

