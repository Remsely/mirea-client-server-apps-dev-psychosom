import {keepPreviousData, useQuery} from "@tanstack/react-query";
import {PsychologistProfileData} from "@/@types/types";
import {psychologistService} from "../services";
import {FetchError} from "@/shared/utils";

export interface BackendSlot {
    start: string;
    end: string;
}

export interface BackendDaySchedule {
    jsDate: Date;
    slots: BackendSlot[];
}

export const usePsychologistSchedule = (id: number) =>
    useQuery<BackendDaySchedule[]>({
        queryKey: ['psychologistSchedule', id],
        queryFn: () => psychologistService.getPsychologistSchedule(id),
        enabled: !!id,
        staleTime: 5 * 60_000,
    });

type UsePsychologistProfileOptions = {
    psychologistId: number | undefined;
    enabled?: boolean;
    staleTime?: number;
    onSuccess?: (data: PsychologistProfileData) => void;
};

export function usePsychologistProfile({
                                           psychologistId,
                                           enabled = true,
                                           staleTime = 5 * 60 * 1000,
                                       }: UsePsychologistProfileOptions) {
    const {
        data,
        isPending,
        isError,
    } = useQuery<PsychologistProfileData, FetchError>({
        queryKey: ['psychologist-profile', psychologistId],
        queryFn: () => {
            if (psychologistId === undefined) return Promise.reject();
            return psychologistService.getPsychologistProfile(psychologistId);
        },
        enabled: enabled && psychologistId !== undefined,
        staleTime,
        placeholderData: keepPreviousData,
    });

    return {
        profile: data,
        isLoading: isPending,
        isError,
    };
}