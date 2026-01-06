package ru.agrachev.location.domain

import ru.agrachev.core.domain.model.GeoLocation

interface WriteableLocationRepository : ReadOnlyLocationRepository {

    fun submitGeoLocation(geoLocation: GeoLocation)
}
