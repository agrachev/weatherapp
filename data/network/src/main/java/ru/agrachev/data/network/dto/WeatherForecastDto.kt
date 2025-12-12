package ru.agrachev.data.network.dto

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
internal data class WeatherForecastDto(
    val current: CurrentDto,
    val forecast: ForecastDto,
    val location: LocationDto,
)

@Keep
@JsonClass(generateAdapter = true)
internal data class CurrentDto(
    @Json(name = "cloud") val cloudCoverPercentage: Int,
    val condition: ConditionDto,
    @Json(name = "dewpoint_c") val dewPointC: Double,
    @Json(name = "dewpoint_f") val dewPointF: Double,
    @Json(name = "feelslike_c") val feelsLikeC: Double,
    @Json(name = "feelslike_f") val feelsLikeF: Double,
    @Json(name = "gust_kph") val gustKph: Double,
    @Json(name = "gust_mph") val gustMph: Double,
    @Json(name = "heatindex_c") val heatIndexC: Double,
    @Json(name = "heatindex_f") val heatIndexF: Double,
    val humidity: Int,
    @Json(name = "is_day") val isDay: Int,
    @Json(name = "last_updated") val lastUpdated: String,
    @Json(name = "last_updated_epoch") val lastUpdatedEpoch: Int,
    @Json(name = "precip_in") val precipitationIn: Double,
    @Json(name = "precip_mm") val precipitationMm: Double,
    @Json(name = "pressure_in") val pressureIn: Double,
    @Json(name = "pressure_mb") val pressureMb: Double,
    @Json(name = "temp_c") val temperatureC: Double,
    @Json(name = "temp_f") val temperatureF: Double,
    @Json(name = "uv") val uvIndex: Double,
    @Json(name = "vis_km") val visibilityKm: Double,
    @Json(name = "vis_miles") val visibilityMiles: Double,
    @Json(name = "wind_degree") val windDegree: Int,
    @Json(name = "wind_dir") val windDir: String,
    @Json(name = "wind_kph") val windKph: Double,
    @Json(name = "wind_mph") val windMph: Double,
    @Json(name = "windchill_c") val windchillC: Double,
    @Json(name = "windchill_f") val windchillF: Double,
)

@Keep
@JsonClass(generateAdapter = true)
internal data class ForecastDto(
    @Json(name = "forecastday") val forecastDay: List<ForecastDayDto>
)

@Keep
@JsonClass(generateAdapter = true)
internal data class LocationDto(
    val country: String,
    @Json(name = "lat") val latitude: Double,
    @Json(name = "localtime") val localTime: String,
    @Json(name = "localtime_epoch") val localTimeEpoch: Int,
    @Json(name = "lon") val longitude: Double,
    val name: String,
    val region: String,
    @Json(name = "tz_id") val timeZoneId: String,
)

@Keep
@JsonClass(generateAdapter = true)
internal data class ConditionDto(
    val code: Int,
    val icon: String,
    val text: String,
)

@Keep
@JsonClass(generateAdapter = true)
internal data class ForecastDayDto(
    val astro: AstroDto,
    val date: String,
    @Json(name = "date_epoch") val dateEpoch: Int,
    val day: DayDto,
    val hour: List<HourDto>,
)

@Keep
@JsonClass(generateAdapter = true)
internal data class AstroDto(
    @Json(name = "is_moon_up") val isMoonUp: Int,
    @Json(name = "is_sun_up") val isSunUp: Int,
    @Json(name = "moon_illumination") val moonIllumination: Double,
    @Json(name = "moon_phase") val moonPhase: String,
    val moonrise: String,
    val moonset: String,
    val sunrise: String,
    val sunset: String,
)

@Keep
@JsonClass(generateAdapter = true)
internal data class DayDto(
    @Json(name = "avghumidity") val averageHumidity: Int,
    @Json(name = "avgtemp_c") val averageTemperatureC: Double,
    @Json(name = "avgtemp_f") val averageTemperatureF: Double,
    @Json(name = "avgvis_km") val averageVisibilityKm: Double,
    @Json(name = "avgvis_miles") val averageVisibilityMiles: Double,
    val condition: ConditionDto,
    @Json(name = "daily_chance_of_rain") val dailyChanceOfRain: Int,
    @Json(name = "daily_chance_of_snow") val dailyChanceOfSnow: Int,
    @Json(name = "daily_will_it_rain") val dailyWillItRain: Int,
    @Json(name = "daily_will_it_snow") val dailyWillItSnow: Int,
    @Json(name = "maxtemp_c") val maxTemperatureC: Double,
    @Json(name = "maxtemp_f") val maxTemperatureF: Double,
    @Json(name = "maxwind_kph") val maxWindKph: Double,
    @Json(name = "maxwind_mph") val maxWindMph: Double,
    @Json(name = "mintemp_c") val minTemperatureC: Double,
    @Json(name = "mintemp_f") val minTemperatureF: Double,
    @Json(name = "totalprecip_in") val totalPrecipitationIn: Double,
    @Json(name = "totalprecip_mm") val totalPrecipitationMm: Double,
    @Json(name = "totalsnow_cm") val totalSnowCm: Double,
    @Json(name = "uv") val uvIndex: Double,
)

@Keep
@JsonClass(generateAdapter = true)
internal data class HourDto(
    @Json(name = "chance_of_rain") val chanceOfRain: Int,
    @Json(name = "chance_of_snow") val chanceOfSnow: Int,
    @Json(name = "cloud") val cloudCoverPercentage: Int,
    val condition: ConditionDto,
    @Json(name = "dewpoint_c") val dewPointC: Double,
    @Json(name = "dewpoint_f") val dewPointF: Double,
    @Json(name = "feelslike_c") val feelsLikeC: Double,
    @Json(name = "feelslike_f") val feelsLikeF: Double,
    @Json(name = "gust_kph") val gustKph: Double,
    @Json(name = "gust_mph") val gustMph: Double,
    @Json(name = "heatindex_c") val heatIndexC: Double,
    @Json(name = "heatindex_f") val heatIndexF: Double,
    val humidity: Int,
    @Json(name = "is_day") val isDay: Int,
    @Json(name = "precip_in") val precipitationIn: Double,
    @Json(name = "precip_mm") val precipitationMm: Double,
    @Json(name = "pressure_in") val pressureIn: Double,
    @Json(name = "pressure_mb") val pressureMb: Double,
    @Json(name = "snow_cm") val snowCm: Double,
    @Json(name = "temp_c") val temperatureC: Double,
    @Json(name = "temp_f") val temperatureF: Double,
    val time: String,
    @Json(name = "time_epoch") val timeEpoch: Int,
    @Json(name = "uv") val uvIndex: Double,
    @Json(name = "vis_km") val visibilityKm: Double,
    @Json(name = "vis_miles") val visibilityMiles: Double,
    @Json(name = "will_it_rain") val willItRain: Int,
    @Json(name = "will_it_snow") val willItSnow: Int,
    @Json(name = "wind_degree") val windDegree: Int,
    @Json(name = "wind_dir") val windDir: String,
    @Json(name = "wind_kph") val windKph: Double,
    @Json(name = "wind_mph") val windMph: Double,
    @Json(name = "windchill_c") val windchillC: Double,
    @Json(name = "windchill_f") val windchillF: Double,
)
