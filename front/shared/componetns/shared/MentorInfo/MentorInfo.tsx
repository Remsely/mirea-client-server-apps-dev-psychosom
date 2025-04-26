"use client";

import {useState} from "react";
import styles from "./MentorInfo.module.scss";
import {HighlightInfo, PhotoMentorInfo, TextMentorInfo} from "@/shared/componetns/shared/MentorInfo";
import Image from "next/image";
import {Dialog, DialogContent, DialogHeader, DialogTitle} from "@/shared/componetns/ui";

export function MentorInfo() {
    const [isOpenCertificate, setIsOpenCertificate] = useState(false)

    return (
        <main className={styles.main}>
            <div className={styles.description}>
                <TextMentorInfo>Добро пожаловать! Меня зовут [Имя специалиста], и я специализируюсь на психосоматике —
                    области, которая помогает понять, как наши мысли и эмоции влияют на тело. Мой подход основан на
                    многолетнем опыте работы и личных исследованиях, которые я провожу, чтобы найти индивидуальное
                    решение для каждого клиента.</TextMentorInfo>
                <HighlightInfo>Если вас беспокоят хронические симптомы, которые сложно объяснить медицинскими причинами,
                    психосоматика может стать ключом к разгадке.</HighlightInfo>
                <TextMentorInfo>На протяжении своей практики я помог 20 клиентам в течении 2-х лет людям избавиться от
                    внутренних блоков, восстановить баланс и обрести гармонию. Имею диплом в высшем образовании
                    Института Психосоматики.</TextMentorInfo>
                <HighlightInfo onLinkClick={() => setIsOpenCertificate(true)}><a id='important-link'>Диплом</a>
                    : нажмите, чтобы открыть модальное окно и ознакомиться с моим дипломом.</HighlightInfo>
                <Dialog open={isOpenCertificate} onOpenChange={() => setIsOpenCertificate(false)}>
                    <DialogContent>
                        <DialogHeader>
                            <DialogTitle>
                               Сертификат
                            </DialogTitle>
                            <Image src="/certificate.jpg"
                                   alt="Сертификат" width={1200}
                                   height={750}/>
                        </DialogHeader>
                    </DialogContent>
                </Dialog>
            </div>
            <PhotoMentorInfo>specialist.jpg</PhotoMentorInfo>
        </main>
    )
}
