"use client";

import "./Header.scss";
import {AuthButton, Navbar} from "@/shared/componetns/shared";
import Image from "next/image";
import Link from "next/link";

export function Header({hasNavbar = true}: { hasNavbar?: boolean }) {
    return (
        <header id="head" className="header__wrapper">
            <div className="header container">
                <div className="logo">
                    <Link href="/" className="link"><Image src="/logo.svg" alt="" width={70} height={70}/></Link>
                </div>

                {hasNavbar ? (
                    <nav className="navbar">
                        <Navbar/>
                    </nav>
                ) : <h2 className="navbar__placeholder">Psychosom - Поиск психологов</h2>}

                <div className="auth-button">
                    <AuthButton className="auth-button__button"/>
                </div>
            </div>
        </header>
    );
}