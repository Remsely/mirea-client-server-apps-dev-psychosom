package ru.remsely.psyhosom.usecase.psychologist

import arrow.core.Either
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.psychologist.Article
import ru.remsely.psyhosom.domain.psychologist.Psychologist

interface PublishArticleCommand {
    fun execute(psychologistId: Long, article: Article): Either<DomainError, Psychologist>
}
