package ru.agrachev.feature.weather.forecast.core

import androidx.compose.runtime.staticCompositionLocalOf
import ru.agrachev.core.presentation.BaseViewModel
import ru.agrachev.feature.weather.forecast.model.WeatherForecastUiState
import ru.agrachev.feature.weather.forecast.model.WeatherForecastUiState.Error
import java.time.format.DateTimeFormatter

internal const val DATE_PATTERN = "EEEE, dd MMM"

internal val LocalDateFormatter = staticCompositionLocalOf {
    DateTimeFormatter.ofPattern(DATE_PATTERN)
}

internal typealias WeatherViewModelDefinition =
        BaseViewModel<WeatherForecastUiState, WeatherForecastIntent, WeatherForecastLabel>

internal typealias RepeatRequestCallback = () -> Unit

internal fun WeatherForecastUiState.asFailureState(throwable: Throwable) = Error(
    content = this.content,
    isRefreshing = false,
    throwable = throwable,
)
