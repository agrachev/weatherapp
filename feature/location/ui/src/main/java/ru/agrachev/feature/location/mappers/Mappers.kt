package ru.agrachev.feature.location.mappers

import ru.agrachev.feature.location.model.AddressUiModel
import ru.agrachev.geocode.domain.model.Address

fun Address.toUiModel() = AddressUiModel(
    formattedDescription = this.toFormattedDescription(),
    geoLocation = this.geoLocation,
)

private fun Address.toFormattedDescription() =
    this.addressLines.firstOrNull().orEmpty()
