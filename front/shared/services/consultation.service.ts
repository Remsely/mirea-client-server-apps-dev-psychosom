import {api} from "@/shared/utils";

interface ScheduleConsultationRequest {
    startDtTm: string;
    endDtTm: string;
    problemDescription? : string;
}

class ConsultationService {
    public async scheduleConsultation(params: { id: number; body: ScheduleConsultationRequest; }) {
        await api.post(`psychologists/${params.id}/consultations`, params.body);
    }
}

export const consultationService = new ConsultationService();