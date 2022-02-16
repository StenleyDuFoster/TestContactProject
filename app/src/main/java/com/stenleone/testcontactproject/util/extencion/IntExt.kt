package com.stenleone.testcontactproject.util.exception

fun Int.nullIfZero() = if (this == 0) null else this

fun Int?.orZero() = this ?: 0