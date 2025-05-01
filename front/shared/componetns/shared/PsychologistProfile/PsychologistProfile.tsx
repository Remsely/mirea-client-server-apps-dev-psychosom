"use client";

import {
    ConsultationForm, LoadingSpinner,
    MentorInfo,
    SliderReview,
} from "@/shared/componetns";
import { useAuth, usePsychologistProfile } from "@/shared/hooks";
import { notFound } from "next/navigation";

interface PsychologistProfileProps {
    psychologistId: number;
}

export function PsychologistProfile({ psychologistId }: PsychologistProfileProps) {
    const { setIsOpenAuthModal } = useAuth();

    const {
        profile: specialist,
        isLoading,
        isError,
        error,
    } = usePsychologistProfile({ psychologistId });

    if (isLoading) return (
        <div className="loader-center-wrapper">
            <LoadingSpinner/>
        </div>
        );

    if (isError) return <p>{error?.message ?? "Не удалось получить данные"}</p>;

    if (!specialist) return notFound();

    return (
        <>
            <MentorInfo specialist={specialist} />

            <ConsultationForm
                id={psychologistId}
                setIsOpenAuthModal={setIsOpenAuthModal}
            />

            <SliderReview psychologistId={psychologistId} />
        </>
    );
}