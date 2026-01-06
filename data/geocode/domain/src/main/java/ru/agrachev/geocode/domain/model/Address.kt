package ru.agrachev.geocode.domain.model

import ru.agrachev.core.domain.model.GeoLocation

data class Address(
    val description: String,
    val geoLocation: GeoLocation,
)
