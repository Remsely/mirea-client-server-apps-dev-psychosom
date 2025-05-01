package ru.remsely.psyhosom.api.dto

import io.swagger.v3.oas.annotations.media.Schema
import ru.remsely.psyhosom.domain.psychologist.Article

@Schema(description = "Запрос на публикацию статьи")
data class ArticleBlockDto(
    @field:Schema(
        description = "Тип блока",
        example = "PARAGRAPH"
    )
    val type: Article.ArticleBlock.Type,

    @field:Schema(
        description = "Дата и время начала консультации",
        example = "Я очень люблю психологию!"
    )
    val content: String
)
