"use client";

import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

import {CSSProperties, FC, useEffect, useState} from "react";
import { Review, ServerReview } from "@/@types/types";
import Slider from "react-slick";
import { ReviewCard } from "../ReviewCard/ReviewCard";

interface ArrowProps {
    className?: string;
    style?: CSSProperties;
    onClick?: () => void;
}

const SampleArrow: FC<ArrowProps> = ({ className, style, onClick }) => (
    <div className={className} style={style} onClick={onClick} />
);

const mapReviewData = (data: ServerReview): Review => ({
    id: data.id,
    name: data.patient.firstName,
    rating: data.rating,
    text: data.text,
    date: data.date,
});

export const SliderReview: FC = () => {
    const [reviews, setReviews] = useState<Review[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const fetchReviews = async () => {
            try {
                const response = await fetch(`${process.env.NEXT_PUBLIC_REST_URL}/api/v1/psychologists/1/reviews`);
                if (!response.ok) {
                    throw new Error("Не удалось загрузить отзывы");
                }
                const data: ServerReview[] = await response.json();
                setReviews(data.map(mapReviewData));
            } catch (e) {
                console.error(e);
                setError("Ошибка при загрузке отзывов. Попробуйте позже.");
            } finally {
                setLoading(false);
            }
        };

        fetchReviews();
    }, []);

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

    if (loading) {
        return (
            <Slider {...sliderSettings}>
                {Array.from({ length: 4 }).map((_, index) => (
                    <ReviewCard key={index} isLoading />
                ))}
            </Slider>
        );
    }

    if (error) {
        return <div style={{ color: "red" }}>{error}</div>;
    }

    if (!reviews.length) {
        return <div>Отзывов пока нет.</div>;
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