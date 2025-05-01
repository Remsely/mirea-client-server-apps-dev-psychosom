"use client";

import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

import React, {CSSProperties, FC} from "react";
import Slider from "react-slick";
import {FrameTitle, LoadingSpinner, ReviewButton, ReviewCard} from "@/shared/componetns";
import {useAuth, useReviews} from "@/shared/hooks";

interface ArrowProps {
    className?: string;
    style?: CSSProperties;
    onClick?: () => void;
}

const SampleArrow: FC<ArrowProps> = ({className, style, onClick}) => (
    <div className={className} style={style} onClick={onClick}/>
);

export function SliderReview({psychologistId}: { psychologistId: number }) {
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
            <FrameTitle id="reviews">Отзывы</FrameTitle>
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
                                    name={review.name}
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