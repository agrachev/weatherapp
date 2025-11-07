package ru.agrachev.data.network.api

import retrofit2.http.GET
import retrofit2.http.QueryMap
import ru.agrachev.data.network.pojo.WeatherForecastPojo

internal interface WeatherApi {

    @GET("v1/forecast.json")
    suspend fun getForecast(@QueryMap parameters: Map<String, String>): WeatherForecastPojo
}
