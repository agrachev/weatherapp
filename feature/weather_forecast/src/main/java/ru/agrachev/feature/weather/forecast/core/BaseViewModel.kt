package ru.agrachev.feature.weather.forecast.core

import kotlinx.coroutines.flow.Flow

internal interface BaseViewModel<out S, in I> {

    val currentUiState: S
    val uiStates: Flow<S>

    fun accept(intent: I)
}
