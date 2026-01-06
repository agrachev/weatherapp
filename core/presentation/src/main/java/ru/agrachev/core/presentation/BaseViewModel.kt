package ru.agrachev.core.presentation

import kotlinx.coroutines.flow.Flow

interface BaseViewModel<out S, in I> {

    val currentUiState: S
    val uiStates: Flow<S>

    fun accept(intent: I)
}
