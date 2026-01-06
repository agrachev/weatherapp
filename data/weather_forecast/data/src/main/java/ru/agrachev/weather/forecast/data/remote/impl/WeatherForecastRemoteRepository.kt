package ru.agrachev.weather.forecast.data.remote.impl

import ru.agrachev.core.domain.model.GeoLocation
import ru.agrachev.core.domain.repository.RemoteRepository
import ru.agrachev.weather.forecast.data.remote.api.WeatherForecastApi
import ru.agrachev.weather.forecast.data.remote.mappers.toDomainModel
import ru.agrachev.weather.forecast.data.remote.util.WeatherApiConfig

internal class WeatherForecastRemoteRepository(
    override val api: WeatherForecastApi,
    private val weatherApiConfig: WeatherApiConfig,
) : RemoteRepository<WeatherForecastApi> {

    suspend fun requestWeatherForecast(geoLocation: GeoLocation) =
        with(geoLocation) {
            api
                .getForecast(weatherApiConfig.asQueryMap(latitude, longitude))
                .toDomainModel(this)
        }
}
