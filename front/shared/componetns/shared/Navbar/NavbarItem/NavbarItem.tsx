import {usePathname, useRouter} from "next/navigation";
import {scroller} from "react-scroll";

interface NavbarItemProps {
    children: string;
    link: string;
}

export function NavbarItem(props : NavbarItemProps) {
    const router = useRouter();
    const pathname = usePathname();

    const handleScroll = (section: string) => {
        scroller.scrollTo(section, {
            duration: 800,
            delay: 0,
            smooth: "easeInOutQuart",
            offset: -100,
        });
    };

    const handleClick = () => {
        if (pathname !== "/") {
            router.push(`/?section=${props.link}`);
        } else {
            handleScroll(props.link);
        }
    };

    return (
        <li><a onClick={handleClick}>{props.children}</a></li>
    )
}
