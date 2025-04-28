import {useMutation, useQuery, useQueryClient} from '@tanstack/react-query'
import {reviewService, ReviewSubmitData} from "@/shared/services";
import {toast} from "react-hot-toast";
import {FetchError} from "@/shared/utils";

interface UseSubmitReviewProps {
    psychologistId: number
    onSuccessCallback?: () => void
}

export function useReviews(psychologistId: number) {
    const {
        data: reviews,
        isLoading,
        error
    } = useQuery({
        queryKey: ['reviews', psychologistId],
        queryFn: () => reviewService.getReviews(psychologistId)
    })

    return {reviews, isLoading, error}
}

export function useSubmitReviewMutation({
                                            psychologistId,
                                            onSuccessCallback
                                        }: UseSubmitReviewProps) {
    const queryClient = useQueryClient()

    const {mutate: submitReview, isPending: isSubmitting} = useMutation({
        mutationKey: ['submit-review', psychologistId],
        mutationFn: (data: ReviewSubmitData) =>
            reviewService.submitReview(psychologistId, data),
        onSuccess: () => {
            toast.success('Вы успешно оставили отзыв!')
            queryClient.invalidateQueries({
                queryKey: ['reviews', psychologistId]
            })
            if (onSuccessCallback) onSuccessCallback()
        },
        onError: (error: FetchError) => {
            const statusErrorMessages: Record<number, string> = {
                409: "Ваш аккаунт не заполнен. Заполните данные, прежде чем оставить отзыв.",
                401: "Прежде чем оставить отзыв, пожалуйста, войдите в аккаунт.",
                400: "Сеанс со специалистом должен состояться или вы уже оставляли отзыв.",
            };

            if (statusErrorMessages[error.statusCode]) {
                toast.error(statusErrorMessages[error.statusCode]);
                return;
            }

            toast.error("Не удалось отправить отзыв. Попробуйте позже.");
        }
    })

    return {submitReview, isSubmitting}
}