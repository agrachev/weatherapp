package ru.agrachev.data.network.repository

import retrofit2.Retrofit
import retrofit2.create
import ru.agrachev.data.network.api.WeatherApi
import ru.agrachev.data.network.mappers.toDomainModel
import ru.agrachev.domain.model.WeatherApiConfig
import ru.agrachev.domain.repository.WeatherForecastRepository

internal class WeatherForecastRemoteRepository(
    private val retrofit: Retrofit,
    private val weatherApiConfig: WeatherApiConfig,
) : WeatherForecastRepository {
    private val weatherApi by lazy {
        retrofit.create<WeatherApi>()
    }

    override suspend fun getWeatherForecast(latitude: Float, longitude: Float) =
        weatherApi
            .getForecast(weatherApiConfig.toQueryMap(latitude, longitude))
            .toDomainModel()
}

private fun WeatherApiConfig.toQueryMap(latitude: Float, longitude: Float) = mapOf(
    "key" to this.apiKey,
    "q" to "$latitude,$longitude",
    "days" to this.days.toString(),
)
