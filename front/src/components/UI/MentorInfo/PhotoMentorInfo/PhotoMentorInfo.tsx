import styles from "./PhotoMentorInfo.module.scss"

interface PhotoMainProps {
    children: string
}

export default function PhotoMentorInfo(props : PhotoMainProps) {
    return (
        <img className={styles.photo} src={`/${props.children}`} alt=""/>
    )
}
