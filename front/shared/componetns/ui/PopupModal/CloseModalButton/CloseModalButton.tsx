import styles from "./../PopupModal.module.scss"

interface CloseModalButtonProps {
    setIsOpen: (isOpen: boolean) => void;
}

export function CloseModalButton({setIsOpen}: CloseModalButtonProps) {
    return (
        <svg onClick={() => setIsOpen(false)} xmlns="http://www.w3.org/2000/svg" width="28" height="auto"
             viewBox="0 0 24 24" className={styles.svg}
             fill="none" stroke="none" strokeWidth="3" strokeLinecap="round" strokeLinejoin="round">
            <path d="M18 6 6 18"/>
            <path d="m6 6 12 12"/>
        </svg>
    )
}
