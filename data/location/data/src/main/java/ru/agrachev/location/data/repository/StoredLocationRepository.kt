package ru.agrachev.location.data.repository

import kotlinx.coroutines.flow.firstOrNull
import ru.agrachev.core.domain.flowOf
import ru.agrachev.location.data.remote.impl.WhoIsRepository
import ru.agrachev.location.domain.repository.RestartableLocationRepository
import ru.agrachev.weather.forecast.domain.WeatherForecastLocalRepository
import timber.log.Timber

internal class StoredLocationRepository(
    weatherForecastLocalRepository: WeatherForecastLocalRepository,
    whoIsRepository: WhoIsRepository,
) : RestartableLocationRepository() {

    override val locations = flowOf {
        Timber.d("(Re)started stored locations flow")
        weatherForecastLocalRepository.data
            .firstOrNull()
            ?.geoLocation ?: whoIsRepository.requestGeoLocation()
    }.asRestartable()
}
