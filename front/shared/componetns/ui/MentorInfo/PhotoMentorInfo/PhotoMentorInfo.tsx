import styles from "./PhotoMentorInfo.module.scss"
import Image from "next/image";

interface PhotoMainProps {
    children: string
}

export function PhotoMentorInfo(props : PhotoMainProps) {
    return (
        <Image className={styles.photo} src={`/${props.children}`} alt="" width={400} height={533} priority/>
    )
}
