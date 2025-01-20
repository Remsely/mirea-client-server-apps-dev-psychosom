import { Link } from "react-scroll";

interface NavbarItemProps {
    children: string;
    link: string;
}

export function NavbarItem(props : NavbarItemProps) {
    return (
        <li><Link to={props.link} smooth={true} duration={500}>{props.children}</Link></li>
    )
}
