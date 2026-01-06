package ru.agrachev.core.presentation

import kotlinx.coroutines.flow.SharingStarted

@Suppress("FunctionName")
fun SharingStarted.Companion.WhileSubscribedWithDelay() =
    SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS)

private const val STOP_TIMEOUT_MILLIS = 5_000L
