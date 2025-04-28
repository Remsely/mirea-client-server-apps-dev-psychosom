"use client";

import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

import {CSSProperties, FC} from "react";
import Slider from "react-slick";
import {LoadingSpinner, ReviewCard} from "@/shared/componetns";
import {useReviews} from "@/shared/hooks";

interface ArrowProps {
    className?: string;
    style?: CSSProperties;
    onClick?: () => void;
}

const SampleArrow: FC<ArrowProps> = ({ className, style, onClick }) => (
    <div className={className} style={style} onClick={onClick} />
);

export const SliderReview: FC<{ psychologistId?: number }> = ({ psychologistId = 1 }) => {
    const { reviews = [], isLoading, error } = useReviews(psychologistId);

    const sliderSettings = {
        slidesToShow: 3,
        variableWidth: true,
        infinite: false,
        nextArrow: <SampleArrow />,
        prevArrow: <SampleArrow />,
        responsive: [
            { breakpoint: 1024, settings: { slidesToShow: 3 } },
            { breakpoint: 600, settings: { slidesToShow: 2 } },
            { breakpoint: 480, settings: { slidesToShow: 1 } },
        ],
    };

    if (isLoading) {
        return (
            <div style={{ display: "flex", justifyContent: "center", alignItems: "center" }}>
                <LoadingSpinner />
            </div>
        );
    }

    if (error) {
        return <div style={{ color: "red" }}>Ошибка при загрузке отзывов. Попробуйте позже.</div>;
    }

    if (!reviews.length) {
        return <div style={{ height: "50px" }}>Отзывов пока нет.</div>;
    }

    return (
        <Slider {...sliderSettings}>
            {reviews.map((review) => (
                <ReviewCard
                    key={review.id}
                    id={review.id}
                    name={review.name}
                    rating={review.rating}
                    text={review.text}
                    date={review.date}
                />
            ))}
        </Slider>
    );
};