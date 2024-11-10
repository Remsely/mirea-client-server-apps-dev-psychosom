"use client";

import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

import {CSSProperties} from "react";
import {ReviewCard} from "@/shared/componetns/ui";
import {reviews} from "@/shared/dataForDev";
import dynamic from "next/dynamic";

interface ArrowProps {
    className?: string;
    style?: CSSProperties;
    onClick?: () => void;
}

function SampleArrow(props : ArrowProps) {
    const {className, style, onClick} = props;
    return (
        <div
            className={className}
            style={{...style}}
            onClick={onClick}
        >

        </div>
    );
}

const Slider = dynamic(() => import('react-slick'), { ssr: false });

export function SliderReview() {
    const settings = {
        slidesToShow: 3,
        variableWidth: true,
        nextArrow: <SampleArrow/>,
        prevArrow: <SampleArrow/>,
        responsive: [
            {
                breakpoint: 1024,
                settings: {
                    slidesToShow: 3,
                }
            },
            {
                breakpoint: 600,
                settings: {
                    slidesToShow: 2,
                }
            },
            {
                breakpoint: 480,
                settings: {
                    slidesToShow: 1,
                }
            }
        ]
    };

    return (
        <Slider {...settings}>
            {reviews.map(review => (
                <ReviewCard
                    key={review.id}
                    id={review.id}
                    name={review.name}
                    star={review.star}
                    message={review.message}
                    date={review.date}
                />
            ))}
        </Slider>
    )
}
