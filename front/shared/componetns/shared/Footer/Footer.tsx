import styles from "./Footer.module.scss"
import {Navbar} from "@/shared/componetns/shared";
import {ContactsItem} from "./ContactsItem/ContactsItem";
import Image from "next/image";

export function Footer() {
    return (
        <>
            <div className={styles.background}>
                <footer className={`${styles.footer} container`}>
                    <div className={styles.logo}>
                        <Image src="/logo-without-bg.svg" alt="" width={40} height={40}/>
                        <h1 className={styles.title}>Психосоматика</h1>
                    </div>
                    <div className={styles.important}>
                        <h2>Важное</h2>
                        <Navbar/>
                    </div>
                    <div className={styles.socials}>
                        <h2>Соцсети</h2>
                        <ContactsItem image="telegram">psychosomatic</ContactsItem>
                        <ContactsItem image="whatsapp">8 (999)-888-77-66</ContactsItem>
                        <ContactsItem image="instagram">psychosomatic</ContactsItem>
                    </div>
                </footer>
            </div>

        </>
    )
}