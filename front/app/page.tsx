"use client";

import {
    FrameTitle,
    MentorInfo,
    ReviewButton,
    SubmitMessage,
    SliderReview
} from "@/shared/componetns/shared";
import {useEffect, useState} from "react";
import {AuthForm, ConsultationForm} from "@/shared/componetns/shared/Forms";
import {Dialog} from "@/shared/componetns/ui";
import {useSession} from "next-auth/react";
import useDialogStore from "@/shared/stores/dialogStore";
import { useSearchParams } from "next/navigation";
import {scroller} from "react-scroll";

export default function Home() {
    const {data: session} = useSession();
    const [isOpenForm, setIsOpenForm] = useState(false);
    const setTitle = useDialogStore((state) => state.setTitle);

    const searchParams = useSearchParams();

    useEffect(() => {
        const section = searchParams.get("section");
        if (section) {
            scroller.scrollTo(section, {
                duration: 800,
                delay: 0,
                smooth: "easeInOutQuart",
                offset: -100,
            });
        }
    }, [searchParams]);

    useEffect(() => {
        if (!isOpenForm) {
            setTitle("")
        }
    }, [isOpenForm, setTitle])

    return (
        <>
            <div className="container">
                <MentorInfo/>

                <ConsultationForm setIsOpen={setIsOpenForm} isOpen={isOpenForm}/>
                <Dialog isOpen={isOpenForm} setIsOpen={setIsOpenForm}>{ session ? <SubmitMessage
                    title="Поздравляем, вы записаны!"> Вы записались на консультацию к специалисту.
                    Скоро с вами свяжется специалист по методу связи, который вы указали. </SubmitMessage> : <AuthForm/>
                } </Dialog>

                <FrameTitle id="reviews">Отзывы</FrameTitle>

                <SliderReview/>

                <ReviewButton/>
            </div>
        </>
    );
}
