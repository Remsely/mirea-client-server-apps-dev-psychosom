import {Dialog as DialogBlock, DialogPanel, DialogTitle} from "@headlessui/react";
import styles from "./Dialog.module.scss"
import {CloseDialogButton} from "./CloseDialogButton/CloseDialogButton";
import {ReactNode} from "react";
import useDialogStore from "@/shared/stores/dialogStore";

interface DialogProps {
    isOpen: boolean
    setIsOpen: (isOpen: boolean) => void;
    children?: string | ReactNode;
}

export function Dialog(props: DialogProps) {
    const { title } = useDialogStore();

    return (
        <DialogBlock open={props.isOpen} onClose={() => props.setIsOpen(false)} className={styles.dialog}>
            <div className={styles.background}>
                <DialogPanel className={styles.panel}>
                    {title &&
                        <div className={styles.header}>
                            <DialogTitle className={styles.title}>{title}</DialogTitle>
                        </div>
                    }
                    <CloseDialogButton setIsOpen={props.setIsOpen}/>
                    {props.children}
                </DialogPanel>
            </div>
        </DialogBlock>
    )
}
