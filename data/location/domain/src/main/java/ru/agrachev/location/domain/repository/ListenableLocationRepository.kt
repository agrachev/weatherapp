package ru.agrachev.location.domain.repository

import kotlinx.coroutines.flow.Flow

interface ListenableLocationRepository : ReadOnlyLocationRepository {

    val isListeningToLocationUpdates: Flow<Boolean>

    fun startListenToLocationUpdates()

    fun stopListenToLocationUpdates()
}
