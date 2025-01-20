"use client";

import styles from "./Header.module.scss"
import {AuthButton, Navbar} from "@/shared/componetns/shared";
import Image from "next/image";

export function Header() {
    return (
        <>
            <header id="head" className={`${styles.header} container`}>
                <div className={styles.logo}>
                    <Image src="/logo-without-bg.svg" alt="" width={50} height={50}/>
                    <h1 className={styles.title}>Психосоматика</h1>
                </div>
                <nav className={styles.navbar}>
                    <Navbar/>
                    <div className={styles.authButton}>
                        <AuthButton/>
                    </div>
                </nav>
            </header>
        </>
    )
}