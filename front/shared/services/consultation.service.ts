import {api} from "@/shared/utils";

interface ScheduleConsultationRequest {
    startTm: string;
    endTm: string;
    date: string;
    problemDescription? : string;
}

class ConsultationService {
    public async scheduleConsultation( id: number, body: ScheduleConsultationRequest) {
        await api.post(`psychologists/${id}/consultations`, body);
    }
}

export const consultationService = new ConsultationService();