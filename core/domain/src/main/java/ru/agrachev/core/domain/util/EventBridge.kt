package ru.agrachev.core.domain.util

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class EventBridge<T> {

    private val eventFlow = MutableSharedFlow<T>()

    operator fun invoke() = eventFlow.asSharedFlow()

    suspend fun post(event: T) = eventFlow.emit(event)
}
