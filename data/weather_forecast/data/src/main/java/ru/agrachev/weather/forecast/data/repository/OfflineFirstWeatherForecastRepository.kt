package ru.agrachev.weather.forecast.data.repository

import kotlinx.coroutines.flow.filterNotNull
import ru.agrachev.core.domain.entity.GeoLocation
import ru.agrachev.weather.forecast.data.remote.impl.WeatherForecastRemoteRepository
import ru.agrachev.weather.forecast.domain.WeatherForecastLocalRepository
import ru.agrachev.weather.forecast.domain.repository.WeatherForecastRepository

internal class OfflineFirstWeatherForecastRepository(
    private val weatherForecastLocalRepository: WeatherForecastLocalRepository,
    private val weatherForecastRemoteRepository: WeatherForecastRemoteRepository,
) : WeatherForecastRepository {

    override val weatherForecast = weatherForecastLocalRepository.data
        .filterNotNull()

    override suspend fun requestWeatherForecast(geoLocation: GeoLocation) {
        val weatherForecast = weatherForecastRemoteRepository
            .requestWeatherForecast(geoLocation)
        weatherForecastLocalRepository.updateData(weatherForecast)
    }
}
