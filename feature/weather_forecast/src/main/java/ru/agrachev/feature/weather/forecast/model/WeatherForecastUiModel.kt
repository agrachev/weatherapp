package ru.agrachev.feature.weather.forecast.model

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
    val cloudCoverPercentage: Int,
    val condition: ConditionUiModel,
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
    val windchillF: Double,
    val minTemperatureC: Double,
    val minTemperatureF: Double,
    val maxTemperatureC: Double,
    val maxTemperatureF: Double,
)

@Stable
internal data class ForecastUiModel(
    val forecastDay: List<ForecastDayUiModel>
)

@Immutable
internal data class LocationUiModel(
    val country: String,
    val latitude: Double,
    val longitude: Double,
    val localTime: String,
    val localTimeEpoch: Int,
    val name: String,
    val region: String,
    val timeZoneId: String
)

@Immutable
internal data class ConditionUiModel(
    val code: Int,
    val icon: String,
    val text: String
)

@Stable
internal data class ForecastDayUiModel(
    val astro: AstroUiModel,
    val date: String,
    val dateEpoch: Int,
    val day: DayUiModel,
    val hour: List<HourUiModel>
)

@Immutable
internal data class AstroUiModel(
    val isMoonUp: Boolean,
    val isSunUp: Boolean,
    val moonIllumination: Double,
    val moonPhase: String,
    val moonrise: String,
    val moonset: String,
    val sunrise: String,
    val sunset: String
)

@Immutable
internal data class DayUiModel(
    val averageHumidity: Int,
    val averageTemperatureC: Double,
    val averageTemperatureF: Double,
    val averageVisibilityKm: Double,
    val averageVisibilityMiles: Double,
    val condition: ConditionUiModel,
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

@Immutable
internal data class HourUiModel(
    val chanceOfRain: Int,
    val chanceOfSnow: Int,
    val cloudCoverPercentage: Int,
    val condition: ConditionUiModel,
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
