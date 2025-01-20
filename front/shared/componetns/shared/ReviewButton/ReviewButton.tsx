"use client";

import styles from "./ReviewButton.module.scss"
import {useEffect, useState} from "react";
import {Button, Dialog} from "@/shared/componetns/ui";
import {AuthForm, ReviewForm} from "@/shared/componetns/shared/Forms";
import useDialogStore from "@/shared/stores/dialogStore";
import {useSession} from "next-auth/react";
import {toast} from "react-hot-toast";
import {CircleAlert} from "lucide-react";

export function ReviewButton() {
    const [isOpenReviewModalForm, setIsOpenReviewModalForm] = useState(false);
    const setTitle = useDialogStore((state) => state.setTitle);
    const {data: session} = useSession();

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

    const onOpen = () => {
        if (session) {
            setIsOpenReviewModalForm(true)
        } else {
            setIsOpenReviewModalForm(true)
            toast("Прежде чем оставить отзыв, пожалуйста, войдите в аккаунт", {
                icon: <CircleAlert />,
                duration: 3000,
                className: styles.toast
            })
        }

    }

    return (
        <>
            <div className={styles.buttonWrapper}>
                <Button type="button" onClick={onOpen}>
                    Оставить отзыв
                </Button>
            </div>
            <Dialog isOpen={isOpenReviewModalForm}
                    setIsOpen={setIsOpenReviewModalForm}>
                {session ? <ReviewForm/> : <AuthForm/>}
            </Dialog>
        </>
    )
}
