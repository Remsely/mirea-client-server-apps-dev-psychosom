"use client";

import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

import React, {CSSProperties, FC} from "react";
import Slider from "react-slick";
import {FrameTitle, LoadingSpinner, ReviewButton, ReviewCard} from "@/shared/componetns";
import {useAuth, useReviews} from "@/shared/hooks";
import "./SliderReview.scss"
import {StarIcon} from "lucide-react";

interface ArrowProps {
    className?: string;
    style?: CSSProperties;
    onClick?: () => void;
}

const SampleArrow: FC<ArrowProps> = ({className, style, onClick}) => (
    <div className={className} style={style} onClick={onClick}/>
);

export function SliderReview({psychologistId, rating}: { psychologistId: number, rating: number | null }) {
    const {reviews = [], isLoading, error} = useReviews(psychologistId);

    const sliderSettings = {
        slidesToShow: 3,
        variableWidth: true,
        infinite: false,
        nextArrow: <SampleArrow/>,
        prevArrow: <SampleArrow/>,
        responsive: [
            {breakpoint: 1024, settings: {slidesToShow: 3}},
            {breakpoint: 600, settings: {slidesToShow: 2}},
            {breakpoint: 480, settings: {slidesToShow: 1}},
        ],
    };

    const {setIsOpenAuthModal} = useAuth();

    return (
        <>
            <div className="slider-review__header">
                <FrameTitle id="reviews">Отзывы</FrameTitle>
                {rating !== null && (
                    <h2 className="slider-review__header__rating">
                        {rating.toFixed(2)}
                        <StarIcon size={32}/>
                    </h2>
                )}
            </div>
            {isLoading ?
                <div style={{display: "flex", justifyContent: "center", alignItems: "center"}}>
                    <LoadingSpinner/>
                </div> :
                error ?
                    <div style={{color: "red"}}>Ошибка при загрузке отзывов. Попробуйте позже.</div> :
                    !reviews.length ?
                        <div style={{height: "50px"}}>Отзывов пока нет.</div> :
                        <Slider {...sliderSettings}>
                            {reviews.map((review) => (
                                <ReviewCard
                                    key={review.id}
                                    id={review.id}
                                    name={review.patient.firstName}
                                    rating={review.rating}
                                    text={review.text}
                                    date={review.date}
                                />
                            ))}
                        </Slider>
            }
            <ReviewButton psychologistId={psychologistId} setIsOpenAuthModal={setIsOpenAuthModal}/>
        </>
    );
}