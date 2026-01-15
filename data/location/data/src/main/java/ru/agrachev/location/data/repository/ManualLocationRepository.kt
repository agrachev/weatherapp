package ru.agrachev.location.data.repository

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onSubscription
import ru.agrachev.core.domain.model.GeoLocation
import ru.agrachev.location.domain.GeoLocationResult
import ru.agrachev.location.domain.asResult
import ru.agrachev.location.domain.repository.WriteableLocationRepository
import timber.log.Timber

internal class ManualLocationRepository : WriteableLocationRepository {

    private val manualLocations = MutableSharedFlow<GeoLocationResult>()

    override val locations = manualLocations
        .asSharedFlow()
        .onSubscription {
            Timber.d("(Re)started manual locations flow")
        }

    override suspend fun submitGeoLocation(geoLocation: GeoLocation) {
        manualLocations.emit(geoLocation.asResult())
    }
}
