package ru.agrachev.core.presentation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

interface BaseViewModel<out S, in I, out L> {

    val currentUiState: S
    val uiStates: Flow<S>
    val labels: Flow<L>
        get() = emptyFlow()

    fun accept(intent: I)
}
