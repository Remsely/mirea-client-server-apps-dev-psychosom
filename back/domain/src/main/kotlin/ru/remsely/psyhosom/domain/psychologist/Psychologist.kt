package ru.remsely.psyhosom.domain.psychologist

import ru.remsely.psyhosom.domain.account.Account

data class Psychologist(
    val id: Long,
    val account: Account,
    val firstName: String,
    val lastName: String,
    val profileImage: String,
    val article: Article,
    val educations: List<Education>
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
