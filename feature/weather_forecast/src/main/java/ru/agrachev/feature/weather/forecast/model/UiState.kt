package ru.agrachev.feature.weather.forecast.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

internal sealed class UiState(
    val content: Content? = null,
) {

    @Immutable
    object Loading : UiState()

    @Stable
    class Error(
        content: Content?,
        val throwable: Throwable,
    ) : UiState(content)

    @Stable
    class DataAvailable(content: Content) : UiState(content)
}

@Stable
internal data class Content(
    val forecast: WeatherForecastUiModel,
)
