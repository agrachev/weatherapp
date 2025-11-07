package ru.agrachev.presentation.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
internal data class WeatherForecastUiModel(
    val current: CurrentUiModel,
    val forecast: ForecastUiModel,
    val location: LocationUiModel
)

@Immutable
internal data class CurrentUiModel(
    val cloudCoveragePercentage: Int,
    val condition: ConditionUiModel,
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
    val temperatureCelsius: Double,
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

@Stable
internal data class ForecastUiModel(
    val forecastday: List<ForecastdayUiModel>
)

@Immutable
internal data class LocationUiModel(
    val country: String,
    val latitude: Double,
    val longitude: Double,
    val localtime: String,
    val localtime_epoch: Int,
    val name: String,
    val region: String,
    val tz_id: String
)

@Immutable
internal data class ConditionUiModel(
    val code: Int,
    val icon: String,
    val text: String
)

@Stable
internal data class ForecastdayUiModel(
    val astro: AstroUiModel,
    val date: String,
    val date_epoch: Int,
    val day: DayUiModel,
    val hour: List<HourUiModel>
)

@Immutable
internal data class AstroUiModel(
    val isMoonUp: Boolean,
    val isSunUp: Boolean,
    val moon_illumination: Int,
    val moon_phase: String,
    val moonrise: String,
    val moonset: String,
    val sunrise: String,
    val sunset: String
)

@Immutable
internal data class DayUiModel(
    val avghumidity: Int,
    val averageTemperatureCelsius: Double,
    val averageTemperatureFahrenheit: Double,
    val avgvis_km: Double,
    val avgvis_miles: Double,
    val condition: ConditionUiModel,
    val daily_chance_of_rain: Int,
    val daily_chance_of_snow: Int,
    val daily_will_it_rain: Int,
    val daily_will_it_snow: Int,
    val maxtemperatureCelsius: Double,
    val maxtemp_f: Double,
    val maxwind_kph: Double,
    val maxwind_mph: Double,
    val mintemperatureCelsius: Double,
    val mintemp_f: Double,
    val totalprecip_in: Double,
    val totalprecip_mm: Double,
    val totalsnow_cm: Int,
    val uv: Double
)

@Immutable
internal data class HourUiModel(
    val chance_of_rain: Int,
    val chance_of_snow: Int,
    val cloudCoveragePercentage: Int,
    val condition: ConditionUiModel,
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
    val temperatureCelsius: Double,
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
