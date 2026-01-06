package ru.agrachev.location.component.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.agrachev.geocode.domain.repository.GeocodeRepository
import ru.agrachev.location.domain.LocationRepository

class GetCurrentAddressUseCase(
    geocodeRepository: GeocodeRepository,
    locationRepository: LocationRepository,
    ioDispatcher: CoroutineDispatcher,
) {

    private val currentAddressFlow = locationRepository
        .locations
        .map {
            geocodeRepository
                .getAddressesFromLocation(it)
                .firstOrNull()
        }
        .flowOn(ioDispatcher)

    operator fun invoke() = currentAddressFlow
}
