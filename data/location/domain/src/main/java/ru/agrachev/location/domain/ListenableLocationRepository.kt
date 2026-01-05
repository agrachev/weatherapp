package ru.agrachev.location.domain

interface ListenableLocationRepository : ReadOnlyLocationRepository {

    fun startListenToLocationUpdates()

    suspend fun stopListenToLocationUpdates()
}
