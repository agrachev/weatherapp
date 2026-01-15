package ru.agrachev.weather.forecast.component.usecase

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update

class GetRefreshUpdateStatusUseCase {

    private val writableUpdateStatus = MutableStateFlow(false)

    operator fun invoke() = flow {
        writableUpdateStatus.collect {
            emit(it)
        }
    }

    internal operator fun invoke(status: Boolean) =
        writableUpdateStatus.update {
            status
        }
}
