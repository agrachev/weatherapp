package ru.agrachev.feature.weather.forecast.core

internal sealed interface WeatherForecastLabel {

    class Failure(
        val throwable: Throwable,
        val hasContent: Boolean,
    ) : WeatherForecastLabel
}
