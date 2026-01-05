package ru.agrachev.weather.forecast.data.remote.util

internal data class WeatherApiConfig(
    val apiKey: String,
    val days: Int,
) {
    fun asQueryMap(latitude: Double, longitude: Double) = mapOf(
        "key" to apiKey,
        "q" to "$latitude,$longitude",
        "days" to days.toString(),
    )
}
