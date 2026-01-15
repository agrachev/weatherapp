package ru.agrachev.feature.weather.forecast.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

internal sealed class WeatherForecastUiState(
    open val content: WeatherForecastUiModel? = null,
    open val isRefreshing: Boolean = false,
) {

    abstract fun duplicate(
        content: WeatherForecastUiModel? = this.content,
        isRefreshing: Boolean = this.isRefreshing,
    ): WeatherForecastUiState

    @Immutable
    data object Loading : WeatherForecastUiState() {

        override fun duplicate(
            content: WeatherForecastUiModel?,
            isRefreshing: Boolean,
        ) = this
    }

    @Stable
    data class Error(
        override val content: WeatherForecastUiModel?,
        override val isRefreshing: Boolean,
        val throwable: Throwable,
    ) : WeatherForecastUiState(content, isRefreshing) {

        override fun duplicate(
            content: WeatherForecastUiModel?,
            isRefreshing: Boolean,
        ): Error = this.copy(
            content = content,
            isRefreshing = isRefreshing,
        )
    }

    @Stable
    data class DataAvailable(
        override val content: WeatherForecastUiModel,
        override val isRefreshing: Boolean = false,
    ) : WeatherForecastUiState(content, isRefreshing) {

        override fun duplicate(
            content: WeatherForecastUiModel?,
            isRefreshing: Boolean,
        ): DataAvailable = this.copy(
            content = content ?: this.content,
            isRefreshing = isRefreshing,
        )
    }
}
