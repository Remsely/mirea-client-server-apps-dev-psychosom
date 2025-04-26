"use client";

import {
    FrameTitle,
    MentorInfo,
    ReviewButton,
    SliderReview
} from "@/shared/componetns/shared";
import {Suspense, useState} from "react";
import {AuthModalForm, ConsultationForm} from "@/shared/componetns/shared/Forms";
import {ScrollToSection} from "@/shared/lib/scroll-to-section";

export default function Home() {
    const [isOpenAuthModal, setIsOpenAuthModal] = useState(false);

    return (
        <div className="container">
            <Suspense fallback={null}>
                <ScrollToSection/>
            </Suspense>

            <MentorInfo/>

            <AuthModalForm isOpen={isOpenAuthModal} onClose={() => setIsOpenAuthModal(false)}/>
            <ConsultationForm setIsOpenAuthModal={setIsOpenAuthModal}/>

            <FrameTitle id="reviews">Отзывы</FrameTitle>

            <SliderReview/>

            <ReviewButton setIsOpenAuthModal={setIsOpenAuthModal}/>
        </div>
    );
}