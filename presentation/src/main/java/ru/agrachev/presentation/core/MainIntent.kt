package ru.agrachev.presentation.core

internal sealed interface MainIntent {
    object RequestForecast : MainIntent
    object DismissAlert : MainIntent
    object StartListenToLocationUpdates : MainIntent
}
