package ru.remsely.psyhosom.usecase.psychologist

import arrow.core.Either
import arrow.core.raise.either
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.psychologist.Psychologist
import ru.remsely.psyhosom.domain.psychologist.dao.PsychologistFinder
import ru.remsely.psyhosom.domain.psychologist.dao.PsychologistUpdater
import ru.remsely.psyhosom.domain.psychologist.event.AddPsychologistEducationEvent
import ru.remsely.psyhosom.usecase.s3.S3FileCreator

@Component
open class AddPsychologistEducationCommandImpl(
    private val s3FileCreator: S3FileCreator,
    private val psychologistFinder: PsychologistFinder,
    private val psychologistUpdater: PsychologistUpdater
) : AddPsychologistEducationCommand {

    @Transactional
    override fun execute(
        psychologistId: Long,
        event: AddPsychologistEducationEvent
    ): Either<DomainError, Psychologist> = either {
        val psychologist = psychologistFinder.findPsychologistById(psychologistId).bind()

        val urls = event.files.map { s3FileCreator.createFile(it).bind().url }

        val educations = psychologist.educations + Psychologist.Education(
            id = 0L,
            files = urls.map {
                Psychologist.Education.File(
                    id = 0L,
                    url = it
                )
            }
        )
        psychologistUpdater.updatePsychologist(
            psychologist.copy(
                educations = educations
            )
        ).bind()
    }
}
