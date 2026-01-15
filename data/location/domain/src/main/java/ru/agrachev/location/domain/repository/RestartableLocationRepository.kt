package ru.agrachev.location.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen
import ru.agrachev.location.domain.exceptions.RestartableFlowException
import ru.agrachev.location.domain.asResult

abstract class RestartableLocationRepository : ReadOnlyLocationRepository {

    private val restartTrigger = MutableSharedFlow<Unit>()

    protected fun <T> Flow<T>.asRestartable() = this
        .map { it.asResult() }
        .retryWhen { cause, _ ->
            emit(
                Result.failure(
                    RestartableFlowException(
                        reason = cause,
                        repository = this@RestartableLocationRepository,
                    )
                )
            )
            restartTrigger.first()
            true
        }

    suspend fun retry() = restartTrigger.emit(Unit)
}
