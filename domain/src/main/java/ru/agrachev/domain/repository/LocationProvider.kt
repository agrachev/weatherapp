package ru.agrachev.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.agrachev.domain.model.GeoLocation

interface LocationProvider {

    val currentLocation: GeoLocation

    val locations: Flow<GeoLocation>

    fun startListenToLocationUpdates()

    suspend fun stopListenToLocationUpdates()
}
