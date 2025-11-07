package ru.agrachev.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.agrachev.domain.repository.LocationProvider
import ru.agrachev.domain.repository.WeatherForecastRepository

class GetWeatherForecastUseCase(
    private val weatherForecastRepository: WeatherForecastRepository,
    private val locationProvider: LocationProvider,
    private val ioDispatcher: CoroutineDispatcher,
) {

    suspend operator fun invoke() = with(locationProvider.currentLocation) {
        withContext(ioDispatcher) {
            weatherForecastRepository.getWeatherForecast(latitude, longitude)
        }
    }
}
