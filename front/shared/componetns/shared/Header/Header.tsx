"use client";

import styles from "./Header.module.scss"
import {AuthButton, Navbar} from "@/shared/componetns/shared";
import Image from "next/image";
import Link from "next/link";

export function Header() {
    return (
        <>
            <header id="head" className={`${styles.header} container`}>
                <div className={styles.logo}>
                    <Image src="/logo-without-bg.svg" alt="" width={50} height={50}/>
                    <Link href="/"><h1 className={styles.title}>Психосоматика</h1></Link>
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