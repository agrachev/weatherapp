package ru.agrachev.weather.forecast.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.agrachev.core.domain.entity.GeoLocation
import ru.agrachev.weather.forecast.domain.model.WeatherForecast

interface WeatherForecastRepository {

    val weatherForecast: Flow<WeatherForecast>

    suspend fun requestWeatherForecast(geoLocation: GeoLocation)
}
