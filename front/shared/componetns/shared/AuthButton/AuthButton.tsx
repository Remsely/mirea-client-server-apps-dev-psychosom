"use client";

import styles from "./AuthButton.module.scss"
import {AuthForm} from "@/shared/componetns/shared/Forms";
import {useEffect, useState} from "react";
import {Dialog, Popover, PopoverContent, PopoverTrigger} from "@/shared/componetns/ui";
import {LogIn, LogOut, PenLine, User} from "lucide-react";
import useDialogStore from "@/shared/stores/dialogStore";
import {signOut, useSession} from "next-auth/react";
import Link from "next/link";

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
            <Popover>
                <PopoverTrigger asChild>
                    <div className={styles.button} onClick={() => !session && setIsOpen(!isOpen)}>
                        {!session ? <LogIn className={styles.svg} width={24}/> :
                            <User className={styles.svg} width={24}/>}
                        <span>{session ? "Профиль" : "Войти"}</span>
                    </div>
                </PopoverTrigger>
                <PopoverContent>
                    <ul className={styles.authList}>
                        <li className={styles.authItem}>
                            <Link href="/profile">
                                <PenLine /> Изменить
                            </Link>
                        </li>
                        <li onClick={() => signOut()} className={styles.authItem}>
                            <LogOut/> Выход
                        </li>
                    </ul>
                </PopoverContent>
            </Popover>

            {isOpen && <Dialog isOpen={isOpen}
                               setIsOpen={setIsOpen}><AuthForm/></Dialog>}
        </>
    );
}