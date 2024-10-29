import {Dialog, DialogPanel, DialogTitle} from "@headlessui/react";
import styles from "./PopupModal.module.scss"
import CloseModalButton from "./CloseModalButton/CloseModalButton.tsx";
import {ReactNode} from "react";

interface PopupModalProps {
    isOpen: boolean
    setIsOpen: (isOpen: boolean) => void;
    title?: string;
    children?: string | ReactNode;
    isSuccessSubmitForm?: boolean
}

export default function PopupModal({isOpen, setIsOpen, title, children, isSuccessSubmitForm}: PopupModalProps) {
    return (
        <Dialog open={isOpen} onClose={() => setIsOpen(false)} className={styles.dialog}>
            <div className={styles.background}>
                <DialogPanel className={styles.panel}>
                    {title && !isSuccessSubmitForm &&
                        <div className={styles.header}>
                            <DialogTitle className={styles.title}>{title}</DialogTitle>
                        </div>
                    }
                    <CloseModalButton setIsOpen={setIsOpen}/>
                    {children}
                </DialogPanel>
            </div>
        </Dialog>
    )
}
