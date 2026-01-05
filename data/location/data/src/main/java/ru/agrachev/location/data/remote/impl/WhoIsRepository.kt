package ru.agrachev.location.data.remote.impl

import ru.agrachev.core.domain.entity.GeoLocation
import ru.agrachev.core.domain.repository.RemoteRepository
import ru.agrachev.location.data.remote.api.WhoIsApi

internal class WhoIsRepository(
    override val api: WhoIsApi,
) : RemoteRepository<WhoIsApi> {

    suspend fun requestGeoLocation() =
        api.getWhoIsData().let {
            GeoLocation(
                latitude = it.latitude,
                longitude = it.longitude,
            )
        }
}
