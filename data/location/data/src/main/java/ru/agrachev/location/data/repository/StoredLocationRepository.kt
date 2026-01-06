package ru.agrachev.location.data.repository

import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import ru.agrachev.location.data.remote.impl.WhoIsRepository
import ru.agrachev.location.domain.ReadOnlyLocationRepository
import ru.agrachev.weather.forecast.domain.WeatherForecastLocalRepository

internal class StoredLocationRepository(
    weatherForecastLocalRepository: WeatherForecastLocalRepository,
    whoIsRepository: WhoIsRepository,
) : ReadOnlyLocationRepository {

    override val locations = flow {
        emit(
            weatherForecastLocalRepository.data
                .firstOrNull()
                ?.geoLocation ?: whoIsRepository.requestGeoLocation()
        )
    }
}
