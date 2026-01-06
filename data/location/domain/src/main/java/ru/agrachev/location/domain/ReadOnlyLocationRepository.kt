package ru.agrachev.location.domain

import kotlinx.coroutines.flow.Flow
import ru.agrachev.core.domain.model.GeoLocation

interface ReadOnlyLocationRepository {

    val locations: Flow<GeoLocation>
}
