import styles from "./ReviewCard.module.scss";
import {Review} from "@/@types/types";

export function ReviewCard({name, star, message, date}: Review) {
    return (
        <>
            <div className={styles.review}>
                <div className={styles.info}>
                    <h3>{name}</h3>
                    <span>{star} </span><img src="/star.svg" alt=""/>
                </div>
                <p>{message}</p>
                <h4>{date}</h4>
            </div>
        </>
    )
}
