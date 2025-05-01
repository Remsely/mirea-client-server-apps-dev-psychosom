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

const TEST_SCHEDULE: BackendDaySchedule[] = [
    {
        jsDate: new Date(),
        slots: [
            { start: '09:00', end: '09:50' },
            { start: '11:00', end: '11:50' },
            { start: '14:00', end: '14:50' },
        ],
    },
    {
        jsDate: new Date(Date.now() + 24 * 60 * 60 * 1000),
        slots: [
            { start: '10:00', end: '10:50' },
            { start: '15:00', end: '15:50' },
        ],
    },
];

const fetchScheduleMock = async (): Promise<BackendDaySchedule[]> => {
    await new Promise((r) => setTimeout(r, 400));
    return TEST_SCHEDULE;
};

export const usePsychologistSchedule = (id: number) =>
    useQuery<BackendDaySchedule[]>({
        queryKey: ['psychologistSchedule', id],
        queryFn: fetchScheduleMock,
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
        error,
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
        error,
    };
}