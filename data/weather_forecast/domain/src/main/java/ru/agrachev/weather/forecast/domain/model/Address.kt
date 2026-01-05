package ru.agrachev.weather.forecast.domain.model

import ru.agrachev.core.domain.entity.GeoLocation

data class Address(
    val description: String,
    val geoLocation: GeoLocation,
)
