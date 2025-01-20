"use client";

import styles from "./AuthButton.module.scss"
import {AuthForm} from "@/shared/componetns/shared/Forms";
import {useEffect, useState} from "react";
import {Dialog} from "@/shared/componetns/ui";
import {LogIn, LogOut} from "lucide-react";
import useDialogStore from "@/shared/stores/dialogStore";
import {signOut, useSession} from "next-auth/react";

export function AuthButton() {
    const {data: session} = useSession();
    const [isOpen, setIsOpen] = useState(false);
    const setTitle = useDialogStore((state) => state.setTitle);
    useEffect(() => {
        if (!isOpen) {
            setTitle("")
        }
    }, [isOpen, setTitle]);

    return (
        <>
            <div className={styles.button}>
                {!session ? <LogIn className={styles.svg} onClick={() => setIsOpen(!isOpen)} width={24}/> :
                    <LogOut className={styles.svg} onClick={() => signOut()}/>}
                <span>{session ? "Выйти" : "Войти"}</span>
            </div>

            {isOpen && <Dialog isOpen={isOpen}
                               setIsOpen={setIsOpen}><AuthForm/></Dialog>}
        </>
    );
}