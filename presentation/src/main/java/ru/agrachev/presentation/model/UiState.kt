package ru.agrachev.presentation.model

import androidx.compose.runtime.Stable

@Stable
internal data class UiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val forecast: WeatherForecastUiModel? = null,
)
