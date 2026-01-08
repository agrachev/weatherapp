package ru.agrachev.feature.weather.forecast.core

import androidx.compose.runtime.staticCompositionLocalOf
import ru.agrachev.core.presentation.BaseViewModel
import ru.agrachev.feature.weather.forecast.model.UiState
import java.time.format.DateTimeFormatter

internal const val DATE_PATTERN = "EEEE, dd MMM"

internal val LocalDateFormatter = staticCompositionLocalOf {
    DateTimeFormatter.ofPattern(DATE_PATTERN)
}

internal typealias WeatherViewModelDefinition = BaseViewModel<UiState, MainIntent>
