package ru.agrachev.core.domain.util

fun interface FailureHandler {

    suspend operator fun invoke(throwable: Throwable)
}
