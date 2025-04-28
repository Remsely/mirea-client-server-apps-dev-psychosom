import {Review} from "@/@types/types";
import {api} from "@/shared/utils";

export interface ReviewSubmitData {
    rating: number
    text: string
}


class ReviewService {
    public async submitReview(psychologistId: number, data: ReviewSubmitData) {
        return await api.post<Review>(
            `psychologists/${psychologistId}/reviews`,
            data
        )
    }

    public async getReviews(psychologistId: number) {
        return await api.get<Review[]>(
            `psychologists/${psychologistId}/reviews`
        )
    }
}

export const reviewService = new ReviewService()