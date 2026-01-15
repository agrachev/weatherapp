package ru.agrachev.location.component.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.agrachev.core.domain.util.FailureHandler
import ru.agrachev.geocode.domain.repository.GeocodeRepository
import ru.agrachev.location.domain.repository.LocationRepository

class GetCurrentAddressUseCase(
    private val geocodeRepository: GeocodeRepository,
    private val locationRepository: LocationRepository,
    private val ioDispatcher: CoroutineDispatcher,
    private val failureHandler: FailureHandler,
) {

    operator fun invoke() =
        locationRepository.locations
            .map {
                geocodeRepository
                    .getAddressesFromLocation(it.getOrThrow())
                    .firstOrNull()
            }
            .catch {
                failureHandler(it)
            }
            .flowOn(ioDispatcher)
}
