"use client";

import {StarIcon} from "lucide-react";
import Link from "next/link";
import styles from "./PsychologistCard.module.scss";
import {IPsychologist} from "@/@types/types";

export function PsychologistCard({ id, firstName, lastName, profileImage, rating }: IPsychologist) {
    return (
        <div className={styles.psychologistCard}>
            <Link
                href={`/psychologist/${id}`}
                className={styles.photoWrapper}
                aria-label={`Открыть профиль ${firstName} ${lastName}`}
            >
                <img
                    className={styles.photo}
                    src={profileImage}
                    alt={`${firstName} ${lastName}`}
                />
                {rating !== null && (
                    <span className={styles.badge}>
                        {rating.toFixed(2)}
                        <StarIcon size={22}/>
              </span>
                )}
            </Link>

            <div className={styles.name}>
                {firstName} {lastName}
            </div>

            <Link href={`/psychologist/${id}`}>
                <button className={styles.profileButton}>К специалисту</button>
            </Link>
        </div>
    );
}