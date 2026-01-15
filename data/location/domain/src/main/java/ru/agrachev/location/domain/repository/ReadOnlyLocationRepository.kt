package ru.agrachev.location.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.agrachev.location.domain.GeoLocationResult

interface ReadOnlyLocationRepository {

    val locations: Flow<GeoLocationResult>
}
