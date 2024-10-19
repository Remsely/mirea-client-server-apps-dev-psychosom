import styles from "./Header.module.scss"
import Navbar from "../Navbar/Navbar";

export default function Header() {
    return (
        <>
            <header className={`${styles.header} container`}>
                <h1 className={styles.title}>НАЗВАНИЕ</h1>
                <nav className={styles.navbar}>
                    <Navbar/>
                </nav>
            </header>
        </>
    )
}