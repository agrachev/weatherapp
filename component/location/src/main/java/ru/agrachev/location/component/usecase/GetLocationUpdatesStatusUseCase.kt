package ru.agrachev.location.component.usecase

import ru.agrachev.location.domain.LocationRepository

class GetLocationUpdatesStatusUseCase(
    private val locationRepository: LocationRepository,
) {

    operator fun invoke() = locationRepository.isListeningToLocationUpdates
}
