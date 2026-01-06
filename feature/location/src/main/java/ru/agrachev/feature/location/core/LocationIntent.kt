package ru.agrachev.feature.location.core

internal sealed interface LocationIntent {

    class RequestLocationUpdates(val toggleOn: Boolean) : LocationIntent

    class RequestAddressList(val locationName: String) : LocationIntent
}
