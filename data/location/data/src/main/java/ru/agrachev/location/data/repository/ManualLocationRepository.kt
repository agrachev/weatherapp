package ru.agrachev.location.data.repository

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import ru.agrachev.core.domain.model.GeoLocation
import ru.agrachev.location.domain.WriteableLocationRepository

internal class ManualLocationRepository : WriteableLocationRepository {

    private val _manualLocations = MutableSharedFlow<GeoLocation>()

    override val locations = flow {
        _manualLocations.collect {
            emit(it)
        }
    }

    override suspend fun submitGeoLocation(geoLocation: GeoLocation) {
        _manualLocations.emit(geoLocation)
    }
}
