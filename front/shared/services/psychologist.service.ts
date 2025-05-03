import { api } from "@/shared/utils";
import {IPsychologist, PsychologistProfileData} from "@/@types/types";
import {BackendDaySchedule, BackendSlot} from "../hooks/usePsychologist";

interface BackendSlotDto {
    startTm: string;
    endTm:   string;
}

interface BackendDayScheduleDto {
    date:  string;
    slots: BackendSlotDto[];
}

class PsychologistService {
    public async getPsychologistProfile(psychologistId: number) {
        return api.get<PsychologistProfileData>(`psychologists/${psychologistId}`);
    }

    public async getPsychologistSchedule(psychologistId: number) {
        const response = await api.get<BackendDayScheduleDto[]>(
            `psychologists/${psychologistId}/schedule`,
        );

        return response.map<BackendDaySchedule>((d) => {
            const [day, month, year] = d.date.split("-").map(Number);
            const jsDate = new Date(year, month - 1, day);

            const slots: BackendSlot[] = d.slots.map((s) => ({
                start: s.startTm.slice(0, 5),
                end:   s.endTm.slice(0, 5),
            }));

            return { jsDate, slots };
        });
    }

    public async getPsychologistsCatalog() {
        return api.get<IPsychologist[]>("psychologists/catalog");
    }
}

export const psychologistService = new PsychologistService();