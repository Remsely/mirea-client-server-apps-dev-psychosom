'use client';

import Link from 'next/link';
import {Ban} from "lucide-react";
import {Button, Footer, Header} from "@/shared/componetns"
import "./not-found.scss";

export default function NotFoundPage() {
    return (
        <>
            <Header hasNavbar={false}/>
            <div className="not-found__page">
                <Ban size={150}/>
                <h1>404 - Страница не найдена</h1>
                <p>Извините, но эта страница не существует.</p>
                <Link href="/">
                    <Button>Вернуться на главную</Button>
                </Link>
            </div>
            <Footer/>
        </>
    );
}
