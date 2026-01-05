package ru.agrachev.weather.forecast.component.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import ru.agrachev.location.domain.LocationRepository
import ru.agrachev.weather.forecast.domain.repository.WeatherForecastRepository

@OptIn(ExperimentalCoroutinesApi::class)
class GetWeatherForecastUseCase(
    weatherForecastRepository: WeatherForecastRepository,
    locationRepository: LocationRepository,
    ioDispatcher: CoroutineDispatcher,
) {

    private val updateWeatherForecastOnLocationChangeFlow: Flow<Nothing> =
        locationRepository.locations
            .flatMapLatest { sourceGeoLocation ->
                flow {
                    weatherForecastRepository.requestWeatherForecast(sourceGeoLocation)
                }
            }

    private val weatherForecastFlow = flowOf(
        updateWeatherForecastOnLocationChangeFlow,
        weatherForecastRepository.weatherForecast,
    )
        .flattenMerge()
        .flowOn(ioDispatcher)

    operator fun invoke() = weatherForecastFlow
}
