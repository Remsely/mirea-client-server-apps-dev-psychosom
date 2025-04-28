"use client";

import './StarRatingInput.scss';
import {useState} from "react";

interface StarRatingInputProps {
    value: number;
    onChange: (value: number) => void;
    className?: string;
}

export function StarRatingInput({ value, onChange, className }: StarRatingInputProps) {
    const [hover, setHover] = useState(0);

    return (
        <div className={`star-rating-row ${className || ''}`}>
            <div className="star-rating">
                {[...Array(5)].map((_, index) => {
                    const starIndex = index + 1;
                    return (
                        <button
                            key={starIndex}
                            type="button"
                            className={`star ${starIndex <= (hover || value) ? 'star--filled' : ''}`}
                            onClick={() => onChange(starIndex)}
                            onMouseEnter={() => setHover(starIndex)}
                            onMouseLeave={() => setHover(0)}
                            aria-label={`Оценка ${starIndex} звезд`}
                        >
                            <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24"
                                 fill="currentColor" stroke="currentColor" strokeWidth="2" strokeLinecap="round"
                                 strokeLinejoin="round" className="star-rating__icon">
                                <polygon
                                    points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2"/>
                            </svg>
                        </button>
                    );
                })}
            </div>
        </div>
    );
}