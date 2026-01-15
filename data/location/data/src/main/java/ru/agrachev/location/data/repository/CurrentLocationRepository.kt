package ru.agrachev.location.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.shareIn
import ru.agrachev.core.domain.model.GeoLocation
import ru.agrachev.location.domain.exceptions.RestartableFlowException
import ru.agrachev.location.domain.repository.ListenableLocationRepository
import ru.agrachev.location.domain.repository.LocationRepository
import ru.agrachev.location.domain.repository.RestartableLocationRepository
import ru.agrachev.location.domain.repository.WriteableLocationRepository

internal class CurrentLocationRepository(
    sharedLocationsCoroutineScope: CoroutineScope,
    private val fusedLocationProvider: ListenableLocationRepository,
    private val manualLocationProvider: WriteableLocationRepository,
    private val storedLocationProvider: RestartableLocationRepository,
) : LocationRepository {

    override fun startListenToLocationUpdates() =
        fusedLocationProvider.startListenToLocationUpdates()

    override fun stopListenToLocationUpdates() =
        fusedLocationProvider.stopListenToLocationUpdates()

    override suspend fun submitGeoLocation(geoLocation: GeoLocation) =
        manualLocationProvider.submitGeoLocation(geoLocation)

    override val isListeningToLocationUpdates =
        fusedLocationProvider.isListeningToLocationUpdates

    private val locationSharedFlow = merge(
        storedLocationProvider.locations,
        fusedLocationProvider.locations,
        manualLocationProvider.locations,
    )
        .shareIn(
            scope = sharedLocationsCoroutineScope,
            started = SharingStarted.WhileSubscribed(),
            replay = 1,
        )

    override val locations
        get() = (locationSharedFlow.throwable?.let {
            locationSharedFlow
                .onSubscription {
                    if (it is RestartableFlowException) {
                        it.repository.retry()
                    }
                }
                .drop(count = 1)
        } ?: locationSharedFlow)
            .distinctUntilChanged()
}

private inline val SharedFlow<Result<*>>.throwable
    get() = this.replayCache.lastOrNull()?.exceptionOrNull()
