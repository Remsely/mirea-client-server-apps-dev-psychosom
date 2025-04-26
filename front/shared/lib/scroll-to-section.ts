"use client";

import { useEffect } from "react";
import { useSearchParams } from "next/navigation";
import { scroller } from "react-scroll";

export function ScrollToSection() {
    const searchParams = useSearchParams();

    useEffect(() => {
        const section = searchParams.get("section");
        if (section) {
            scroller.scrollTo(section, {
                duration: 800,
                delay: 0,
                smooth: "easeInOutQuart",
                offset: -100,
            });
        }
    }, [searchParams]);

    return null;
}
