"use client";

import React, {useState} from "react";
import {
    Dialog,
    DialogContent,
    DialogHeader,
    DialogTitle,
    HighlightInfo,
    PhotoMentorInfo,
    CertificatesInfo
} from "@/shared/componetns";
import "./MentorInfo.scss"
import {PsychologistProfileType} from "@/@types/types";

export function MentorInfo({specialist}: PsychologistProfileType) {
    const [openCertificate, setOpenCertificate] = useState(false);
    const [currentCertificate, setCurrentCertificate] = useState<string | null>(null);

    const handleOpenCertificate = (url: string) => {
        setCurrentCertificate(url);
        setOpenCertificate(true);
    };

    return (
        <div className="psychologist-profile">
            <PhotoMentorInfo specialist={specialist}/>
            <div className="psychologist-profile__fio">
                {specialist.firstName} {specialist.lastName}
            </div>
            {specialist.article.map((block, idx) => {
                if (block.type === "TIP") {
                    return (
                        <HighlightInfo key={idx}>{block.content}</HighlightInfo>
                    );
                }
                if (block.type === "PARAGRAPH") {
                    return (
                        <div className="psychologist-profile__paragraph" key={idx}>
                            {block.content}
                        </div>
                    );
                }
                return null;
            })}
            <CertificatesInfo certificates={specialist.educationFiles} handleOpenCertificate={handleOpenCertificate}/>
            <Dialog open={openCertificate} onOpenChange={() => setOpenCertificate(false)}>
                <DialogContent>
                    <DialogHeader>
                        <DialogTitle>Документ о квалификации</DialogTitle>
                        {currentCertificate && (
                            <img
                                src={currentCertificate}
                                alt="Сертификат"
                                style={{maxWidth: "100%", height: "auto", borderRadius: 12}}
                            />
                        )}
                    </DialogHeader>
                </DialogContent>
            </Dialog>
        </div>
    )
}
