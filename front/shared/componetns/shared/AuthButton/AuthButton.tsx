"use client";

import styles from "./AuthButton.module.scss"
import {AuthModalForm} from "@/shared/componetns/shared/Forms";
import {useState} from "react";
import {Popover, PopoverContent, PopoverTrigger} from "@/shared/componetns/ui";
import {LogIn, LogOut, PenLine, User} from "lucide-react";
import {signOut, useSession} from "next-auth/react";
import Link from "next/link";

export function AuthButton() {
    const {data: session} = useSession();
    const [isOpen, setIsOpen] = useState(false);

    return (
        <>
            <AuthModalForm isOpen={isOpen} onClose={() => setIsOpen(false)}/>
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
        </>
    );
}