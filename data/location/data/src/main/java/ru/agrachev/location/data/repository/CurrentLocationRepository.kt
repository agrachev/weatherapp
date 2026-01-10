package ru.agrachev.location.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.shareIn
import ru.agrachev.core.domain.model.GeoLocation
import ru.agrachev.location.domain.ListenableLocationRepository
import ru.agrachev.location.domain.LocationRepository
import ru.agrachev.location.domain.ReadOnlyLocationRepository
import ru.agrachev.location.domain.WriteableLocationRepository

internal class CurrentLocationRepository(
    sharedLocationsCoroutineScope: CoroutineScope,
    private val fusedLocationProvider: ListenableLocationRepository,
    private val manualLocationProvider: WriteableLocationRepository,
    storedLocationProvider: ReadOnlyLocationRepository,
) : LocationRepository {

    override fun startListenToLocationUpdates() =
        fusedLocationProvider.startListenToLocationUpdates()

    override fun stopListenToLocationUpdates() =
        fusedLocationProvider.stopListenToLocationUpdates()

    override suspend fun submitGeoLocation(geoLocation: GeoLocation) =
        manualLocationProvider.submitGeoLocation(geoLocation)

    override val isListeningToLocationUpdates =
        fusedLocationProvider.isListeningToLocationUpdates

    @OptIn(ExperimentalCoroutinesApi::class)
    override val locations = flowOf(
        storedLocationProvider.locations,
        fusedLocationProvider.locations,
        manualLocationProvider.locations,
    )
        .flattenMerge()
        .distinctUntilChanged()
        .shareIn(
            scope = sharedLocationsCoroutineScope,
            started = SharingStarted.WhileSubscribed(),
            replay = 1,
        )
}
