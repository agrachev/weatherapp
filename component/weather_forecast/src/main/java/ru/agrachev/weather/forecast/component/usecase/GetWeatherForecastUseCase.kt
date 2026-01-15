package ru.agrachev.weather.forecast.component.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.merge
import ru.agrachev.core.domain.util.FailureHandler
import ru.agrachev.location.domain.repository.LocationRepository
import ru.agrachev.weather.forecast.domain.repository.WeatherForecastRepository

@OptIn(ExperimentalCoroutinesApi::class)
class GetWeatherForecastUseCase(
    private val weatherForecastRepository: WeatherForecastRepository,
    private val locationRepository: LocationRepository,
    private val ioDispatcher: CoroutineDispatcher,
    private val failureHandler: FailureHandler,
) {

    private val updateWeatherForecastOnLocationChangeFlow
        get() = locationRepository.locations
            .flatMapLatest { geoLocationResult ->
                flow<Nothing> {
                    weatherForecastRepository.requestWeatherForecast(
                        geoLocationResult.getOrThrow()
                    )
                }
                    .catch {
                        failureHandler(it)
                    }
            }

    operator fun invoke() = merge(
        updateWeatherForecastOnLocationChangeFlow,
        weatherForecastRepository.weatherForecast,
    )
        .flowOn(ioDispatcher)
}
