import styles from "./ReviewCard.module.scss";
import {Review} from "@/@types/types";
import { parse, format } from "date-fns";
import { ru } from "date-fns/locale";
import {Star} from "lucide-react";
import Skeleton from "react-loading-skeleton";
import "react-loading-skeleton/dist/skeleton.css";

interface ReviewCardProps extends Review {
    isLoading?: boolean;
}

export function ReviewCard({name, rating, text, date, isLoading = false}: ReviewCardProps) {
    const parsedDate = parse(date, "dd-MM-yyyy", new Date());
    const readableDate = format(parsedDate, "d MMMM yyyy", { locale: ru });

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
                        <h3 className="">{name}</h3>
                        <div className={styles.rating}>
                            <span className="">{rating} </span>
                            <Star size={26}/>
                        </div>
                    </div>
                    <p className="">{text}</p>
                    <h4 className="">{readableDate} г</h4>
                </>
            )}
        </div>
    );
}