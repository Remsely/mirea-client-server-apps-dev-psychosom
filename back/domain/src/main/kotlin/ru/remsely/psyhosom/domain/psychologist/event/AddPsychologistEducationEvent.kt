package ru.remsely.psyhosom.domain.psychologist.event

import ru.remsely.psyhosom.domain.file.UploadedFile

data class AddPsychologistEducationEvent(
    val files: List<UploadedFile>
)
