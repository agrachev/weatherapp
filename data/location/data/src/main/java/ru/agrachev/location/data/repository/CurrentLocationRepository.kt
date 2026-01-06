package ru.agrachev.location.data.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flowOf
import ru.agrachev.core.domain.model.GeoLocation
import ru.agrachev.location.domain.ListenableLocationRepository
import ru.agrachev.location.domain.LocationRepository
import ru.agrachev.location.domain.ReadOnlyLocationRepository
import ru.agrachev.location.domain.WriteableLocationRepository

internal class CurrentLocationRepository(
    storedLocationProvider: ReadOnlyLocationRepository,
    private val fusedLocationProvider: ListenableLocationRepository,
    private val manualLocationProvider: WriteableLocationRepository,
) : LocationRepository {

    override fun startListenToLocationUpdates() =
        fusedLocationProvider.startListenToLocationUpdates()

    override suspend fun stopListenToLocationUpdates() =
        fusedLocationProvider.stopListenToLocationUpdates()

    override fun submitGeoLocation(geoLocation: GeoLocation) =
        manualLocationProvider.submitGeoLocation(geoLocation)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val locations = flowOf(
        storedLocationProvider.locations,
        fusedLocationProvider.locations,
        manualLocationProvider.locations,
    )
        .flattenMerge()
}
