package ru.agrachev.weather.forecast.data.remote.api

import retrofit2.http.GET
import retrofit2.http.QueryMap
import ru.agrachev.weather.forecast.data.remote.dto.WeatherForecastDto

internal interface WeatherForecastApi {

    @GET("v1/forecast.json")
    suspend fun getForecast(@QueryMap parameters: Map<String, String>): WeatherForecastDto
}
