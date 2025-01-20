"use client";

import styles from "./ReviewButton.module.scss"
import {useEffect, useState} from "react";
import {Button, Dialog} from "@/shared/componetns/ui";
import {ReviewForm} from "@/shared/componetns/shared/Forms";
import useDialogStore from "@/shared/stores/dialogStore";

export function ReviewButton() {
    const [isOpenReviewModalForm, setIsOpenReviewModalForm] = useState(false);
    const setTitle = useDialogStore((state) => state.setTitle);
    useEffect(() => {
        const params = new URLSearchParams(window.location.search);
        if (params.get('review') === 'true') {
            setIsOpenReviewModalForm(true);
        }
        if (isOpenReviewModalForm) {
            setTitle("Оставить отзыв")
        } else {
            setTitle("")
        }
    }, [isOpenReviewModalForm, setTitle]);

    return (
        <>
            <div className={styles.buttonWrapper}>
                <Button type="button" onClick={() => setIsOpenReviewModalForm(true)}>
                    Оставить отзыв
                </Button>
            </div>
            <Dialog isOpen={isOpenReviewModalForm}
                    setIsOpen={setIsOpenReviewModalForm}>
                <ReviewForm/>
            </Dialog>
        </>
    )
}
