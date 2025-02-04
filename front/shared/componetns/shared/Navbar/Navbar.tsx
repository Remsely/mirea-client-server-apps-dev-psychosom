"use client";

import { NavbarItem } from "./NavbarItem/NavbarItem";
import { Navigation } from "@/@types/types";

export function Navbar() {
    const navigations: Navigation[] = [
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
    ];

    return (
        <ul className="flex gap-8">
            {navigations.map((navigation) => (
                <NavbarItem key={navigation.id} link={navigation.link}>
                    {navigation.name}
                </NavbarItem>
            ))}
        </ul>
    );
}