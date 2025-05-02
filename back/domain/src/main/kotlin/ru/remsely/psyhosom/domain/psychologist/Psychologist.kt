package ru.remsely.psyhosom.domain.psychologist

import ru.remsely.psyhosom.domain.account.Account
import ru.remsely.psyhosom.domain.review.Review
import ru.remsely.psyhosom.domain.schedule.Schedule

data class Psychologist(
    val id: Long,
    val account: Account,
    val firstName: String,
    val lastName: String,
    val profileImage: String,
    val article: Article,
    val schedule: Schedule,
    val educations: List<Education>,
    val reviews: List<Review>
) {
    data class Education(
        val id: Long,
        val files: List<File>
    ) {
        data class File(
            val id: Long,
            val url: String
        )
    }
}
