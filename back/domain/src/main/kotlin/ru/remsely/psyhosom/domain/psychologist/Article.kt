package ru.remsely.psyhosom.domain.psychologist

@JvmInline
value class Article(
    val values: List<ArticleBlock>
) {
    companion object {
        fun empty() = Article(emptyList())
    }

    data class ArticleBlock(
        val type: Type,
        val content: String
    ) {
        enum class Type {
            HEADER1,
            HEADER2,
            PARAGRAPH,
            TIP
        }
    }
}
