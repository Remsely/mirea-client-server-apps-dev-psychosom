import HighlightInfo from "./HighlightInfo/HighlightInfo.tsx";
import PopupModal from "../PopupModal/PopupModal.tsx";
import {useState} from "react";
import TextMentorInfo from "./TextMentorInfo/TextMentorInfo.tsx";
import PhotoMentorInfo from "./PhotoMentorInfo/PhotoMentorInfo.tsx";
import styles from "./MentorInfo.module.scss";

export default function MentorInfo() {
    const [isOpenCertificate, setIsOpenCertificate] = useState(false)

    return (
        <main className={`${styles.main} container`}>
            <div className={styles.description}>
                <TextMentorInfo>Продолжается много текста Продолжается много текста Продолжается много текста Продолжается много
                    текста Продолжается много текста Продолжается много текста Продолжается много текста
                    Продолжается много текста Продолжается много текста Продолжается много текста</TextMentorInfo>
                <HighlightInfo>Блок важное (сноска/заметка) со ссылкой на инфу о психосоматике"</HighlightInfo>
                <TextMentorInfo>Продолжается много текста Продолжается много текста Продолжается много текста Продолжается много
                    текста Продолжается много текста Продолжается много текста Продолжается много текста
                    Продолжается много текста Продолжается много текста Продолжается много текста</TextMentorInfo>
                <HighlightInfo onLinkClick={() => setIsOpenCertificate(true)}><a id='important-link'>Инфа о дипломе</a> кнопка,
                    открывающая модальное окно с файлом диплома</HighlightInfo>
                <PopupModal isOpen={isOpenCertificate} setIsOpen={setIsOpenCertificate} image="certificate.jpg"/>
            </div>
            <PhotoMentorInfo>specialist.jpg</PhotoMentorInfo>
        </main>
    )
}
