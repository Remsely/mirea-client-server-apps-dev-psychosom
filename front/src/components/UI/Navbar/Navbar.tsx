import NavbarItem from "./NavbarItem/NavbarItem.tsx";
import {Navigation} from "../../../../@types/types.ts";

export default function Navbar() {
    const navigations : Navigation[]  = [
        {
            id: 1,
            name: "Главная",
            link: "root",
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

    return (
        <ul>
            {navigations.map((navigation) => (
                <NavbarItem key={navigation.id} link={navigation.link} >{navigation.name}</NavbarItem>
            ))}
        </ul>
    )
}
