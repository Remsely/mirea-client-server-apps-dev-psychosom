import styles from "./Footer.module.scss"
import {Navbar} from "@/shared/componetns/ui";
import {ContactsItem} from "./ContactsItem/ContactsItem";

export function Footer() {
    return (
        <>
            <div className={styles.background}>
                <footer className={`${styles.footer} container`}>
                    <div>
                        <h1 className={styles.title}>НАЗВАНИЕ</h1>
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