"use client";

import {
    ConsultationForm,
    FrameTitle,
    MentorInfo,
    PopupModal, ReviewForm,
    SliderReview,
    SubmitMessage
} from "@/shared/componetns/ui";
import {useState} from "react";

export default function Home() {
    const [isOpenForm, setIsOpenForm] = useState(false)

    return (
        <>
            <div className="container">
                <MentorInfo/>

                <ConsultationForm setIsOpen={setIsOpenForm} isOpen={isOpenForm}/>
                <PopupModal isOpen={isOpenForm} setIsOpen={setIsOpenForm}> <SubmitMessage
                    title="Поздравляем, вы записаны!"> Вы записались на консультацию к специалисту.
                    Скоро с вами свяжется специалист по методу связи, который вы указали. </SubmitMessage> </PopupModal>

                <FrameTitle id="reviews">Отзывы</FrameTitle>

                <SliderReview/>

                <ReviewForm/>
            </div>
        </>
    );
}
