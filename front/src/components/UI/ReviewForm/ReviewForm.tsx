import styles from "./ReviewForm.module.scss"
import ButtonMaster from "../ButtonMaster/ButtonMaster.tsx";
import PopupModal from "../PopupModal/PopupModal.tsx";
import {useEffect, useState} from "react";
import ModalReviewForm from "./ModalReviewForm/ModalReviewForm.tsx";

export default function ReviewForm() {
    const [isOpenReviewModalForm, setIsOpenReviewModalForm] = useState(false);
    const [isSuccess, setIsSuccess] = useState(false);

    useEffect(() => {
        const params = new URLSearchParams(window.location.search);
        if (params.get('review') === 'true') {
            setIsOpenReviewModalForm(true);
        }
    }, []);

    return (
        <>
            <div className={styles.button}>
                <ButtonMaster type="button" onClick={() => setIsOpenReviewModalForm(true)}>
                    Оставить отзыв
                </ButtonMaster>
            </div>

            <PopupModal isOpen={isOpenReviewModalForm}
                        setIsOpen={setIsOpenReviewModalForm}
                        title="Оставить отзыв"
                        isSuccessSubmitForm={isSuccess}>
                <ModalReviewForm setIsSuccess={setIsSuccess}/>
            </PopupModal>
        </>
    )
}
