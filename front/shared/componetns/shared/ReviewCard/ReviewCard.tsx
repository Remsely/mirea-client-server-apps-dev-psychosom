import styles from "./ReviewCard.module.scss";
import {Review} from "@/@types/types";
import {Star} from "lucide-react";
import Skeleton from "react-loading-skeleton";
import "react-loading-skeleton/dist/skeleton.css";

interface ReviewCardProps extends Review {
    isLoading?: boolean;
}

export function ReviewCard({name, rating, text, date, isLoading = false}: ReviewCardProps) {
    return (
        <div className={styles.review}>
            {isLoading ? (
                <>
                    <div className={styles.info}>
                        <Skeleton width={120} height={20}/>
                        <Skeleton width={60} height={20}/>
                    </div>
                    <Skeleton count={3}/>
                    <Skeleton width={100} height={20}/>
                </>
            ) : (
                <>
                    <div className={styles.info}>
                        <h3>{name}</h3>
                        <span>{rating} </span>
                        <Star/>
                    </div>
                    <p>{text}</p>
                    <h4>{date}</h4>
                </>
            )}
        </div>
    );
}