package ru.agrachev.feature.weather.forecast.core

internal sealed interface WeatherForecastIntent {

    object RequestForecast : WeatherForecastIntent
}
