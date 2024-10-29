import Header from "./components/UI/Header/Header.tsx";
import Footer from "./components/UI/Footer/Footer.tsx";
import SliderReview from "./components/UI/SliderReview/SliderReview.tsx";
import PopupModal from "./components/UI/PopupModal/PopupModal";
import ConsultationForm from "./components/UI/ConsultationForm/ConsultationForm.tsx";
import FrameTitle from "./components/UI/FrameTitle/FrameTitle.tsx";
import {useState} from "react";
import MentorInfo from "./components/UI/MentorInfo/MentorInfo.tsx";
import ReviewForm from "./components/UI/ReviewForm/ReviewForm.tsx";
import SubmitMessage from "./components/UI/SubmitMessages/SubmitMessage.tsx";

export default function App() {
    const [isOpenForm, setIsOpenForm] = useState(false)

    return (
        <>
            <div className="container">
                <Header/>

                <MentorInfo/>

                <ConsultationForm setIsOpen={setIsOpenForm} isOpen={isOpenForm}/>
                <PopupModal isOpen={isOpenForm} setIsOpen={setIsOpenForm}> <SubmitMessage title="Поздравляем, вы записаны!"> Вы записались на консультацию к специалисту.
                    Скоро с вами свяжется специалист по методу связи, который вы указали. </SubmitMessage> </PopupModal>

                <FrameTitle id="reviews">Отзывы</FrameTitle>

                <SliderReview/>

                <ReviewForm/>
            </div>

            <Footer/>
        </>
    )
}