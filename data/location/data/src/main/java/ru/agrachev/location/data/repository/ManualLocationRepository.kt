package ru.agrachev.location.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import ru.agrachev.core.domain.model.GeoLocation
import ru.agrachev.location.domain.WriteableLocationRepository

internal class ManualLocationRepository : WriteableLocationRepository {

    private val _manualLocations = MutableSharedFlow<GeoLocation>()

    override val locations: Flow<GeoLocation> = _manualLocations.asSharedFlow()

    override fun submitGeoLocation(geoLocation: GeoLocation) {
        _manualLocations.tryEmit(geoLocation)
    }
}
