package ru.agrachev.weather.forecast.component.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import ru.agrachev.core.domain.util.FailureHandler
import ru.agrachev.location.domain.repository.LocationRepository
import ru.agrachev.weather.forecast.domain.repository.WeatherForecastRepository

class RefreshWeatherForecastUseCase(
    private val getRefreshUpdateStatusUseCase: GetRefreshUpdateStatusUseCase,
    private val weatherForecastRepository: WeatherForecastRepository,
    private val locationRepository: LocationRepository,
    private val ioDispatcher: CoroutineDispatcher,
    private val failureHandler: FailureHandler,
) {

    suspend operator fun invoke() =
        withContext(ioDispatcher) {
            getRefreshUpdateStatusUseCase(true)
            try {
                val geoLocationResult = locationRepository.locations.first()
                weatherForecastRepository.requestWeatherForecast(
                    geoLocation = geoLocationResult.getOrThrow(),
                )
            } catch (e: Exception) {
                failureHandler(e.cause ?: e)
            } finally {
                getRefreshUpdateStatusUseCase(false)
            }
        }
}
