package ru.agrachev.presentation.core

import kotlinx.coroutines.flow.Flow

internal interface BaseViewModel<S, I> {

    val currentUiState: S
    val uiStates: Flow<S>

    fun accept(intent: I)
}
