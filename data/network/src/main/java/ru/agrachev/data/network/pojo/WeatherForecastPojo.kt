package ru.agrachev.data.network.pojo

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class WeatherForecastPojo(
    val current: CurrentPojo,
    val forecast: ForecastPojo,
    val location: LocationPojo
)

@JsonClass(generateAdapter = true)
internal data class CurrentPojo(
    val cloud: Int,
    val condition: ConditionPojo,
    val dewpoint_c: Double,
    val dewpoint_f: Double,
    val feelslike_c: Double,
    val feelslike_f: Double,
    val gust_kph: Double,
    val gust_mph: Double,
    val heatindex_c: Double,
    val heatindex_f: Double,
    val humidity: Int,
    val is_day: Int,
    val last_updated: String,
    val last_updated_epoch: Int,
    val precip_in: Double,
    val precip_mm: Double,
    val pressure_in: Double,
    val pressure_mb: Int,
    val temp_c: Double,
    val temp_f: Double,
    val uv: Double,
    val vis_km: Double,
    val vis_miles: Double,
    val wind_degree: Int,
    val wind_dir: String,
    val wind_kph: Double,
    val wind_mph: Double,
    val windchill_c: Double,
    val windchill_f: Double
)

@JsonClass(generateAdapter = true)
internal data class ForecastPojo(
    val forecastday: List<ForecastdayPojo>
)

@JsonClass(generateAdapter = true)
internal data class LocationPojo(
    val country: String,
    val lat: Double,
    val localtime: String,
    val localtime_epoch: Int,
    val lon: Double,
    val name: String,
    val region: String,
    val tz_id: String
)

@JsonClass(generateAdapter = true)
internal data class ConditionPojo(
    val code: Int,
    val icon: String,
    val text: String
)

@JsonClass(generateAdapter = true)
internal data class ForecastdayPojo(
    val astro: AstroPojo,
    val date: String,
    val date_epoch: Int,
    val day: DayPojo,
    val hour: List<HourPojo>
)

@JsonClass(generateAdapter = true)
internal data class AstroPojo(
    val is_moon_up: Int,
    val is_sun_up: Int,
    val moon_illumination: Int,
    val moon_phase: String,
    val moonrise: String,
    val moonset: String,
    val sunrise: String,
    val sunset: String
)

@JsonClass(generateAdapter = true)
internal data class DayPojo(
    val avghumidity: Int,
    val avgtemp_c: Double,
    val avgtemp_f: Double,
    val avgvis_km: Double,
    val avgvis_miles: Double,
    val condition: ConditionPojo,
    val daily_chance_of_rain: Int,
    val daily_chance_of_snow: Int,
    val daily_will_it_rain: Int,
    val daily_will_it_snow: Int,
    val maxtemp_c: Double,
    val maxtemp_f: Double,
    val maxwind_kph: Double,
    val maxwind_mph: Double,
    val mintemp_c: Double,
    val mintemp_f: Double,
    val totalprecip_in: Double,
    val totalprecip_mm: Double,
    val totalsnow_cm: Int,
    val uv: Double
)

@JsonClass(generateAdapter = true)
internal data class HourPojo(
    val chance_of_rain: Int,
    val chance_of_snow: Int,
    val cloud: Int,
    val condition: ConditionPojo,
    val dewpoint_c: Double,
    val dewpoint_f: Double,
    val feelslike_c: Double,
    val feelslike_f: Double,
    val gust_kph: Double,
    val gust_mph: Double,
    val heatindex_c: Double,
    val heatindex_f: Double,
    val humidity: Int,
    val is_day: Int,
    val precip_in: Double,
    val precip_mm: Double,
    val pressure_in: Double,
    val pressure_mb: Int,
    val snow_cm: Int,
    val temp_c: Double,
    val temp_f: Double,
    val time: String,
    val time_epoch: Int,
    val uv: Double,
    val vis_km: Double,
    val vis_miles: Double,
    val will_it_rain: Int,
    val will_it_snow: Int,
    val wind_degree: Int,
    val wind_dir: String,
    val wind_kph: Double,
    val wind_mph: Double,
    val windchill_c: Double,
    val windchill_f: Double
)
