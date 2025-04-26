package ru.remsely.psyhosom.db.dao

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import arrow.core.toOption
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.remsely.psyhosom.db.extensions.toDomain
import ru.remsely.psyhosom.db.extensions.toEntity
import ru.remsely.psyhosom.db.repository.PsychologistRepository
import ru.remsely.psyhosom.domain.error.DomainError
import ru.remsely.psyhosom.domain.psychologist.Psychologist
import ru.remsely.psyhosom.domain.psychologist.dao.PsychologistCreator
import ru.remsely.psyhosom.domain.psychologist.dao.PsychologistFinder
import ru.remsely.psyhosom.domain.psychologist.dao.PsychologistMissingError

import ru.remsely.psyhosom.monitoring.log.logger
import kotlin.jvm.optionals.getOrNull

@Component
open class PsychologistDao(
    private val repository: PsychologistRepository
) : PsychologistCreator, PsychologistFinder {
    private val log = logger()

    @Transactional
    override fun createPsychologist(psychologist: Psychologist): Either<DomainError, Psychologist> =
        repository.save(psychologist.toEntity())
            .toDomain()
            .right()
            .also {
                log.info("Psychologist with id ${psychologist.id} successfully created in DB.")
            }

    @Transactional(readOnly = true)
    override fun findPsychologistById(id: Long): Either<DomainError, Psychologist> =
        repository.findById(id)
            .getOrNull()
            .toOption()
            .fold(
                { PsychologistMissingError.NotFoundById(id).left() },
                {
                    it.toDomain().right().also {
                        log.info("Psychologist with id $id successfully found by id in DB.")
                    }
                }
            )
}
