package ru.agrachev.geocode.domain.model

import ru.agrachev.core.domain.model.GeoLocation

data class Address(
    val geoLocation: GeoLocation,
    val addressLines: List<String?> = emptyList(),
    val featureName: String? = null,
    val thoroughfare: String? = null,
    val subThoroughfare: String? = null,
    val locality: String? = null,
    val subLocality: String? = null,
    val adminArea: String? = null,
    val subAdminArea: String? = null,
    val countryName: String? = null,
    val countryCode: String? = null,
    val postalCode: String? = null,
)
