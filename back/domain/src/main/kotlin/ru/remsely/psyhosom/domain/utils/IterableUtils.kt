package ru.remsely.psyhosom.domain.utils

inline fun <T> Iterable<T>.replaceAllIf(
    predicate: (T) -> Boolean,
    transform: (T) -> T
): List<T> = map { element ->
    if (predicate(element)) transform(element) else element
}
