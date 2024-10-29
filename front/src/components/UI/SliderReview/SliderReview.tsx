import Slider from "react-slick";
import ReviewCard from "../ReviewCard/ReviewCard.tsx";

import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import {CSSProperties} from "react";
import {reviews} from "../../../!!!data-for-dev.ts";

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
        />
    );
}

export default function SliderReview() {
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
