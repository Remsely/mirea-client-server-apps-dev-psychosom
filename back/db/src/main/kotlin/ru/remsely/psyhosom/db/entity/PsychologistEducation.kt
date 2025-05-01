package ru.remsely.psyhosom.db.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "psychologist_education")
data class PsychologistEducation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "psychologist_id", nullable = false)
    val psychologist: Psychologist,

    @OneToMany(
        mappedBy = "education",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    private val joinedFiles: MutableList<PsychologistEducationFile> = mutableListOf()
) {
    val files: List<PsychologistEducationFile>
        get() = joinedFiles

    fun setFiles(newList: List<PsychologistEducationFile>) {
        joinedFiles.clear()
        joinedFiles.addAll(newList)
    }
}
