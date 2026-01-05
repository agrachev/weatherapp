package ru.agrachev.weather.forecast.domain.model

import ru.agrachev.core.domain.entity.GeoLocation

data class WeatherForecast(
    val current: Current,
    val forecast: Forecast,
    val location: Location
) {
    val geoLocation
        get() = with(location) {
            GeoLocation(
                latitude = this.latitude,
                longitude = this.longitude,
            )
        }
}

data class Current(
    val cloudCoverPercentage: Int,
    val condition: Condition,
    val dewPointC: Double,
    val dewPointF: Double,
    val feelsLikeC: Double,
    val feelsLikeF: Double,
    val gustKph: Double,
    val gustMph: Double,
    val heatIndexC: Double,
    val heatIndexF: Double,
    val humidity: Int,
    val isDay: Int,
    val lastUpdated: String,
    val lastUpdatedEpoch: Int,
    val precipitationIn: Double,
    val precipitationMm: Double,
    val pressureIn: Double,
    val pressureMb: Double,
    val temperatureC: Double,
    val temperatureF: Double,
    val uvIndex: Double,
    val visibilityKm: Double,
    val visibilityMiles: Double,
    val windDegree: Int,
    val windDir: String,
    val windKph: Double,
    val windMph: Double,
    val windchillC: Double,
    val windchillF: Double
)

data class Forecast(
    val forecastDay: List<ForecastDay>
)

data class Location(
    val country: String,
    val latitude: Double,
    val longitude: Double,
    val localTime: String,
    val localTimeEpoch: Int,
    val name: String,
    val region: String,
    val timeZoneId: String
)

data class Condition(
    val code: Int,
    val icon: String,
    val text: String
)

data class ForecastDay(
    val astro: Astro,
    val date: String,
    val dateEpoch: Int,
    val day: Day,
    val hour: List<Hour>
)

data class Astro(
    val isMoonUp: Boolean,
    val isSunUp: Boolean,
    val moonIllumination: Double,
    val moonPhase: String,
    val moonrise: String,
    val moonset: String,
    val sunrise: String,
    val sunset: String
)

data class Day(
    val averageHumidity: Int,
    val averageTemperatureC: Double,
    val averageTemperatureF: Double,
    val averageVisibilityKm: Double,
    val averageVisibilityMiles: Double,
    val condition: Condition,
    val dailyChanceOfRain: Int,
    val dailyChanceOfSnow: Int,
    val dailyWillItRain: Int,
    val dailyWillItSnow: Int,
    val maxTemperatureC: Double,
    val maxTemperatureF: Double,
    val maxWindKph: Double,
    val maxWindMph: Double,
    val minTemperatureC: Double,
    val minTemperatureF: Double,
    val totalPrecipitationIn: Double,
    val totalPrecipitationMm: Double,
    val totalSnowCm: Double,
    val uvIndex: Double
)

data class Hour(
    val chanceOfRain: Int,
    val chanceOfSnow: Int,
    val cloudCoverPercentage: Int,
    val condition: Condition,
    val dewPointC: Double,
    val dewPointF: Double,
    val feelsLikeC: Double,
    val feelsLikeF: Double,
    val gustKph: Double,
    val gustMph: Double,
    val heatIndexC: Double,
    val heatIndexF: Double,
    val humidity: Int,
    val isDay: Int,
    val precipitationIn: Double,
    val precipitationMm: Double,
    val pressureIn: Double,
    val pressureMb: Double,
    val snowCm: Double,
    val temperatureC: Double,
    val temperatureF: Double,
    val time: String,
    val timeEpoch: Int,
    val uvIndex: Double,
    val visibilityKm: Double,
    val visibilityMiles: Double,
    val willItRain: Int,
    val willItSnow: Int,
    val windDegree: Int,
    val windDir: String,
    val windKph: Double,
    val windMph: Double,
    val windchillC: Double,
    val windchillF: Double
)
