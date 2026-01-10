package ru.agrachev.feature.location.model

import androidx.compose.runtime.Immutable
import ru.agrachev.core.domain.model.GeoLocation

@Immutable
data class AddressUiModel(
    val formattedDescription: String,
    val geoLocation: GeoLocation,
)
