package ru.agrachev.feature.weather.forecast.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

internal sealed class WeatherForecastUiState(
    val content: WeatherForecastUiModel? = null,
) {

    @Immutable
    object Loading : WeatherForecastUiState()

    @Stable
    class Error(
        content: WeatherForecastUiModel?,
        val throwable: Throwable,
    ) : WeatherForecastUiState(content)

    @Stable
    class DataAvailable(content: WeatherForecastUiModel) : WeatherForecastUiState(content)
}
