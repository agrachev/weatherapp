package ru.agrachev.location.data.remote.api

import retrofit2.http.GET
import ru.agrachev.location.data.remote.dto.WhoIsDto

internal interface WhoIsApi {

    @GET(".")
    suspend fun getWhoIsData(): WhoIsDto
}
