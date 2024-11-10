"use client";

import styles from "./ReviewForm.module.scss"
import {useEffect, useState} from "react";

import {ButtonMaster, PopupModal} from "@/shared/componetns/ui";
import {ModalReviewForm} from "@/shared/componetns/ui/ReviewForm";

export function ReviewForm() {
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
            <div className={styles.buttonWrapper}>
                <ButtonMaster className={styles.button} type="button" onClick={() => setIsOpenReviewModalForm(true)}>
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
