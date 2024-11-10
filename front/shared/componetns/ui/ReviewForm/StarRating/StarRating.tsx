"use client";

import {useState} from 'react';
import styles from './StarRating.module.scss';

interface StarRatingProps {
    onRatingSelect: (rating: number) => void;
}

export function StarRating({onRatingSelect}: StarRatingProps) {
    const [rating, setRating] = useState(0);
    const [hover, setHover] = useState(0);

    const handleClick = (index: number) => {
        setRating(index);
        onRatingSelect(index);
    };

    return (
        <div className={styles.row}>
            <div className={styles.starRating}>
                {[...Array(5)].map((_, index) => {
                    const starIndex = index + 1;

                    return (
                        <a
                            key={starIndex}
                            type="button"
                            className={`${styles.star} ${starIndex <= (hover || rating) ? styles.filled : ''}`}
                            onClick={() => handleClick(starIndex)}
                            onMouseEnter={() => setHover(starIndex)}
                            onMouseLeave={() => setHover(0)}
                            aria-label={`Оценка ${starIndex} звезд`}
                        >
                            <svg xmlns="http://www.w3.org/2000/svg" width="38" height="auto" viewBox="0 0 24 24"
                                 fill="currentColor" stroke="currentColor" strokeWidth="2" strokeLinecap="round"
                                 strokeLinejoin="round" className={styles.starIcon}>
                                <polygon
                                    points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2"/>
                            </svg>
                        </a>
                    )
                        ;
                })}
            </div>
            <h4 className={styles.title}>Оставьте свою оценку</h4>
        </div>
    );
}
