import type {Metadata} from "next";
import {ReactNode, Suspense} from "react";
import "@/shared/styles/index.scss"
import {Footer, Header} from "@/shared/componetns/shared";
import {ScrollToSection} from "@/shared/lib/scroll-to-section";

export const metadata: Metadata = {
    title: "Психосоматика",
};

export default function PsychologistLayout({children}: Readonly<{ children: ReactNode }>) {
    return (
        <>
            <Header/>
            <Suspense fallback={null}>
                <ScrollToSection/>
            </Suspense>
            {children}
            <Footer/>
        </>
    );
}
