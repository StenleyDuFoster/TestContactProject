package com.stenleone.testcontactproject.util.extencion

inline fun <T, R> Iterable<T>.mapToArrayList(transform: (T) -> R): ArrayList<R> {
    return mapTo(ArrayList(collectionSizeOrDefault(10)), transform)
}

fun <T> Iterable<T>.collectionSizeOrDefault(default: Int): Int = if (this is Collection<*>) this.size else default