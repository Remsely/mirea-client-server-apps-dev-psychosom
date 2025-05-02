"use client";

import {
    ConsultationForm, LoadingSpinner,
    MentorInfo,
    SliderReview,
} from "@/shared/componetns";
import { useAuth, usePsychologistProfile } from "@/shared/hooks";
import { notFound } from "next/navigation";
import Head from "next/head";

interface PsychologistProfileProps {
    psychologistId: number;
}

export function PsychologistProfile({ psychologistId }: PsychologistProfileProps) {
    const { setIsOpenAuthModal } = useAuth();

    const {
        profile: specialist,
        isLoading,
        isError,
    } = usePsychologistProfile({ psychologistId });

    if (isLoading) return (
        <div className="loader-center-wrapper">
            <LoadingSpinner/>
        </div>
        );

    if (isError) return <p>{"Не удалось получить данные"}</p>;

    if (!specialist) return notFound();

    return (
        <>
            <Head>
                <title>Психосоматика | {specialist.firstName} {specialist.lastName}</title>
            </Head>
            <MentorInfo specialist={specialist} />

            <ConsultationForm
                id={psychologistId}
                specialistName={`${specialist.firstName} ${specialist.lastName}`}
                setIsOpenAuthModal={setIsOpenAuthModal}
            />

            <SliderReview psychologistId={psychologistId} />
        </>
    );
}