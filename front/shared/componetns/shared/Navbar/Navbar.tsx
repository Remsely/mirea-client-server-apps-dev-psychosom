"use client";

import {NavbarItem} from "./NavbarItem/NavbarItem";
import {scroller} from "react-scroll";
import {useEffect} from "react";
import {Navigation} from "@/@types/types";

export function Navbar() {
    const navigations : Navigation[]  = [
        {
            id: 1,
            name: "Главная",
            link: "head",
        },
        {
            id: 2,
            name: "Записаться на консультацию",
            link: "consultation",
        },
        {
            id: 3,
            name: "Отзывы",
            link: "reviews",
        },
    ]

    useEffect(() => {
        const params = new URLSearchParams(location.search);
        const section = params.get('section');
        if (section) {
            scroller.scrollTo(section,
                { duration: 800, delay: 0, smooth: 'easeInOutQuart', });
        }
    }, []);

    return (
        <ul>
            {navigations.map((navigation) => (
                <NavbarItem key={navigation.id} link={navigation.link} >{navigation.name}</NavbarItem>
            ))}
        </ul>
    )
}
