"use client";

import {useState} from "react";
import styles from "./MentorInfo.module.scss";
import {HighlightInfo, PhotoMentorInfo, TextMentorInfo} from "@/shared/componetns/ui/MentorInfo";
import {PopupModal} from "@/shared/componetns/ui";
import Image from "next/image";

export function MentorInfo() {
    const [isOpenCertificate, setIsOpenCertificate] = useState(false)

    return (
        <main className={styles.main}>
            <div className={styles.description}>
                <TextMentorInfo>Продолжается много текста Продолжается много текста Продолжается много текста Продолжается много
                    текста Продолжается много текста Продолжается много текста Продолжается много текста
                    Продолжается много текста Продолжается много текста Продолжается много текста</TextMentorInfo>
                <HighlightInfo>Блок важное (сноска/заметка) со ссылкой на инфу о психосоматике</HighlightInfo>
                <TextMentorInfo>Продолжается много текста Продолжается много текста Продолжается много текста Продолжается много
                    текста Продолжается много текста Продолжается много текста Продолжается много текста
                    Продолжается много текста Продолжается много текста Продолжается много текста</TextMentorInfo>
                <HighlightInfo onLinkClick={() => setIsOpenCertificate(true)}><a id='important-link'>Инфа о дипломе</a> кнопка,
                    открывающая модальное окно с файлом диплома</HighlightInfo>
                <PopupModal isOpen={isOpenCertificate} setIsOpen={setIsOpenCertificate} > <Image src="/certificate.jpg" alt="" width={533} height={750}/> </PopupModal>
            </div>
            <PhotoMentorInfo>specialist.jpg</PhotoMentorInfo>
        </main>
    )
}
