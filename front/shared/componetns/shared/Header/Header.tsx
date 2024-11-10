import styles from "./Header.module.scss"
import {Navbar} from "@/shared/componetns/ui";

export function Header() {
    return (
        <>
            <header id="head" className={styles.header}>
                <h1 className={styles.title}>НАЗВАНИЕ</h1>
                <nav className={styles.navbar}>
                    <Navbar/>
                </nav>
            </header>
        </>
    )
}