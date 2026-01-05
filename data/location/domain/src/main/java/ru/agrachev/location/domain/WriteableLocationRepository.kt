package ru.agrachev.location.domain

import ru.agrachev.core.domain.entity.GeoLocation

interface WriteableLocationRepository : ReadOnlyLocationRepository {

    fun submitGeoLocation(geoLocation: GeoLocation)
}
