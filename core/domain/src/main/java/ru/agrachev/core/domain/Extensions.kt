package ru.agrachev.core.domain

import kotlinx.coroutines.flow.flow
import ru.agrachev.core.domain.util.EventBridge

typealias FailureBridge = EventBridge<Throwable>

inline fun <T> flowOf(
    crossinline producer: suspend () -> T,
) = flow {
    emit(producer())
}
