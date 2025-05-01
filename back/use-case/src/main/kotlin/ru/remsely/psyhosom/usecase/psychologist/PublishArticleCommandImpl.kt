package ru.remsely.psyhosom.usecase.psychologist

import arrow.core.Either
import arrow.core.flatMap
import org.springframework.stereotype.Component
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.psychologist.Article
import ru.remsely.psyhosom.domain.psychologist.Psychologist
import ru.remsely.psyhosom.domain.psychologist.dao.PsychologistFinder
import ru.remsely.psyhosom.domain.psychologist.dao.PsychologistUpdater

@Component
class PublishArticleCommandImpl(
    private val psychologistFinder: PsychologistFinder,
    private val psychologistUpdater: PsychologistUpdater
) : PublishArticleCommand {
    override fun execute(
        psychologistId: Long,
        article: Article
    ): Either<DomainError, Psychologist> =
        psychologistFinder.findPsychologistById(psychologistId)
            .flatMap { psychologist ->
                psychologistUpdater.updatePsychologist(
                    psychologist.copy(
                        article = article
                    )
                )
            }
}
