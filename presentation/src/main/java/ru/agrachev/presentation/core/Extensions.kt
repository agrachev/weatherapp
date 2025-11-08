package ru.agrachev.presentation.core

import androidx.compose.runtime.staticCompositionLocalOf
import ru.agrachev.presentation.model.UiState
import java.time.format.DateTimeFormatter

internal const val DATE_PATTERN = "EEEE, dd MMM"

internal val LocalDateFormatter = staticCompositionLocalOf {
    DateTimeFormatter.ofPattern(DATE_PATTERN)
}

internal typealias WeatherViewModelDefinition = BaseViewModel<UiState, MainIntent>
