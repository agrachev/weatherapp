package ru.agrachev.feature.weather.forecast.core

internal sealed interface MainIntent {

    object RequestForecast : MainIntent

    object DismissAlert : MainIntent

//    class ListenToLocationUpdates(val isOn: Boolean) : MainIntent

//    class RequestAddressList(val locationName: String) : MainIntent
}
