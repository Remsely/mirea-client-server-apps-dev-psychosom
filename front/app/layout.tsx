import type {Metadata} from "next";
import {Manrope} from 'next/font/google';
import {ReactNode} from "react";
import "@/shared/styles/index.scss"
import {Footer, Header, Providers} from "@/shared/componetns/shared";
import Head from "next/head";

const manrope = Manrope({
    subsets: ['cyrillic'],
    variable: '--font-manrope',
    weight: ['500', '600', '700'],
});

export const metadata: Metadata = {
    title: "Психосоматика",
};

export default function RootLayout({children}: Readonly<{ children: ReactNode }>) {
    return (
        <html lang="ru">
        <Head>
            <link rel="icon" type="image/png" href="/favicons/favicon-96x96.png" sizes="96x96"/>
            <link rel="icon" type="image/svg+xml" href="/favicons/favicon.svg"/>
            <link rel="shortcut icon" href="/favicons/favicon.ico"/>
            <link rel="apple-touch-icon" sizes="180x180" href="/favicons/apple-touch-icon.png"/>
            <meta name="apple-mobile-web-app-title" content="Psyhosom"/>
            <link rel="manifest" href="/favicons/site.webmanifest"/>
        </Head>
        <body className={`${manrope.variable} wrapper`}>
        <Providers>
            <Header/>
            {children}
            <Footer/>
        </Providers>
        </body>
        </html>
    );
}
