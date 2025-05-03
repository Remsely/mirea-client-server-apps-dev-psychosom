import type {Metadata} from "next";
import {ReactNode} from "react";
import "@/shared/styles/index.scss"
import {Footer, Header} from "@/shared/componetns/shared";

export const metadata: Metadata = {
    title: "Psychosom",
};

export default function HomeLayout({children}: Readonly<{ children: ReactNode }>) {
    return (
        <>
            <Header hasNavbar={false}/>
            {children}
            <Footer/>
        </>
    );
}
