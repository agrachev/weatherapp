package ru.agrachev.location.component.usecase

import ru.agrachev.core.domain.model.GeoLocation
import ru.agrachev.location.domain.repository.LocationRepository

class UpdateSelectedLocationUseCase(
    private val locationRepository: LocationRepository,
) {

    suspend operator fun invoke(geoLocation: GeoLocation) =
        with(locationRepository) {
            stopListenToLocationUpdates()
            submitGeoLocation(geoLocation)
        }
}
