package ru.agrachev.domain.repository

import ru.agrachev.domain.model.WeatherForecast

interface WeatherForecastRepository {

    suspend fun getWeatherForecast(latitude: Float, longitude: Float): WeatherForecast
}
