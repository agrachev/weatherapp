package ru.agrachev.feature.location.core

import ru.agrachev.core.domain.model.GeoLocation

internal sealed interface LocationIntent {

    class RequestAddressSuggestions(val locationName: String) : LocationIntent

    class RequestLocationUpdates(val toggleOn: Boolean) : LocationIntent

    class UpdateSelectedLocation(val geoLocation: GeoLocation) : LocationIntent
}
