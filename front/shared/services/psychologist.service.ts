import {api} from "@/shared/utils";
import {PsychologistProfileData} from "@/@types/types";
import {BackendSlot} from "@/shared/hooks";

interface BackendDayScheduleDto {
    date: string;
    slots: BackendSlot[];
}

class PsychologistService {
    public async getPsychologistProfile(psychologistId: number) {
        return await api.get<PsychologistProfileData>(`psychologists/${psychologistId}`)
    }

    public async getPsychologistSchedule(psychologistId: number) {
        const response = await api.get<BackendDayScheduleDto[]>(`/api/psychologists/${psychologistId}/schedule`);

        return response.map((d) => ({
            jsDate: new Date(d.date),
            slots: d.slots,
        }));
    }
}

export const psychologistService = new PsychologistService()