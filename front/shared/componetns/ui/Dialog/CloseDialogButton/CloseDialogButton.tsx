import styles from "../Dialog.module.scss"
import {X} from "lucide-react";

export function CloseDialogButton(props: {setIsOpen: (isOpen: boolean) => void;}) {
    return (
        <X width={30} className={styles.svg} onClick={() => {props.setIsOpen(false)}}/>
    )
}
