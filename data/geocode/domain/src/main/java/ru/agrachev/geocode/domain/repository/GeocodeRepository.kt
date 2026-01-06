package ru.agrachev.geocode.domain.repository

import ru.agrachev.core.domain.model.GeoLocation
import ru.agrachev.geocode.domain.model.Address

interface GeocodeRepository {

    suspend fun getAddressesFromLocationName(locationName: String): List<Address>

    suspend fun getAddressesFromLocation(geoLocation: GeoLocation): List<Address>
}
