import { useMutation, useQueryClient } from '@tanstack/react-query'
import { toast } from 'react-hot-toast'
import {FetchError} from "@/shared/utils";
import {consultationService} from "@/shared/services";

type ConsultationVariables = {
    date: string;
    startTm: string;
    endTm: string;
    problemDescription? : string;
}

interface UseScheduleConsultationProps {
    psychologistId: number
    onSuccessCallback?: () => void
}

export function useScheduleConsultation({onSuccessCallback, psychologistId}: UseScheduleConsultationProps) {
    const queryClient = useQueryClient()
    const {
        mutate: scheduleConsultation,
        isPending: isScheduling,
    } = useMutation<void, FetchError, ConsultationVariables>({
        mutationKey: ['schedule-consultation'],
        mutationFn: (body) =>
            consultationService.scheduleConsultation(psychologistId, body),
        onSuccess: () => {
            toast.success('Вы успешно записаны на консультацию!')
            queryClient.invalidateQueries({
                queryKey: ['consultations', psychologistId],
            })
            if (onSuccessCallback) onSuccessCallback()
        },
        onError: (error) => {
            const statusErrorMessages: Record<number, string> = {
                401: 'Пожалуйста, войдите в аккаунт, чтобы записаться на консультацию.',
                400: 'Вы уже записаны на консультацию. Перейди в телеграм-бота, чтобы продолжить.',
                500: 'Внутренняя ошибка сервера. Попробуйте позже.',
            }

            if (error.statusCode && statusErrorMessages[error.statusCode]) {
                toast.error(statusErrorMessages[error.statusCode])
                return
            }

            toast.error(
                error.message ||
                'Произошла ошибка при записи на консультацию. Пожалуйста, попробуйте позже.'
            )
        },
    })

    return { scheduleConsultation, isScheduling }
}