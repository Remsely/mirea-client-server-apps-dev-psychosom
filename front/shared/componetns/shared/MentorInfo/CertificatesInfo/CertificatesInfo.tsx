import React from "react";
import "./CertificatesInfo.scss";

interface CertificateProps {
    certificates: string[];
    handleOpenCertificate: (url: string) => void;
}

export function CertificatesInfo({ certificates, handleOpenCertificate }: CertificateProps) {
    return (
        <div className="psychologist-profile__certificates">
            <div className="psychologist-profile__cert-title">
                Сертификаты и дипломы:
            </div>
            <div className="psychologist-profile__cert-list">
                {certificates.map((file, i) => (
                    <img
                        className="psychologist-profile__cert-thumb"
                        src={file}
                        alt={`Диплом ${i + 1}`}
                        key={i}
                        onClick={() => handleOpenCertificate(file)}
                        loading="lazy"
                    />
                ))}
            </div>
        </div>
    )
}