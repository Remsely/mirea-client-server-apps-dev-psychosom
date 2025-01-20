import styles from "./ReviewCard.module.scss";
import {Review} from "@/@types/types";
import {Star} from "lucide-react";

export function ReviewCard(props: Review) {
    return (
        <>
            <div className={styles.review}>
                <div className={styles.info}>
                    <h3>{props.name}</h3>
                    <span>{props.star} </span><Star/>
                </div>
                <p>{props.message}</p>
                <h4>{props.date}</h4>
            </div>
        </>
    )
}
