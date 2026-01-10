package ru.agrachev.location.component.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.agrachev.geocode.domain.repository.GeocodeRepository
import ru.agrachev.location.domain.LocationRepository

class GetCurrentAddressUseCase(
    private val geocodeRepository: GeocodeRepository,
    private val locationRepository: LocationRepository,
    private val ioDispatcher: CoroutineDispatcher,
) {

    operator fun invoke() =
        locationRepository.locations
            .map {
                geocodeRepository
                    .getAddressesFromLocation(it)
                    .firstOrNull()
            }
            .flowOn(ioDispatcher)
}
