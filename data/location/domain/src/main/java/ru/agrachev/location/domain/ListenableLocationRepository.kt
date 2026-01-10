package ru.agrachev.location.domain

import kotlinx.coroutines.flow.Flow

interface ListenableLocationRepository : ReadOnlyLocationRepository {

    val isListeningToLocationUpdates: Flow<Boolean>

    fun startListenToLocationUpdates()

    fun stopListenToLocationUpdates()
}
