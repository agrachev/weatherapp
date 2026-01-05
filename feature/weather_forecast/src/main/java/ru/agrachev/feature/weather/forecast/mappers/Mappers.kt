package ru.agrachev.feature.weather.forecast.mappers

import androidx.core.net.toUri
import ru.agrachev.feature.weather.forecast.model.AstroUiModel
import ru.agrachev.feature.weather.forecast.model.ConditionUiModel
import ru.agrachev.feature.weather.forecast.model.CurrentUiModel
import ru.agrachev.feature.weather.forecast.model.DayUiModel
import ru.agrachev.feature.weather.forecast.model.ForecastDayUiModel
import ru.agrachev.feature.weather.forecast.model.ForecastUiModel
import ru.agrachev.feature.weather.forecast.model.HourUiModel
import ru.agrachev.feature.weather.forecast.model.LocationUiModel
import ru.agrachev.feature.weather.forecast.model.WeatherForecastUiModel
import ru.agrachev.weather.forecast.domain.model.Astro
import ru.agrachev.weather.forecast.domain.model.Condition
import ru.agrachev.weather.forecast.domain.model.Current
import ru.agrachev.weather.forecast.domain.model.Day
import ru.agrachev.weather.forecast.domain.model.Forecast
import ru.agrachev.weather.forecast.domain.model.ForecastDay
import ru.agrachev.weather.forecast.domain.model.Hour
import ru.agrachev.weather.forecast.domain.model.Location
import ru.agrachev.weather.forecast.domain.model.WeatherForecast

internal fun WeatherForecast.toUiModel() = WeatherForecastUiModel(
    current = this.current.toUiModel(
        today = this.forecast.forecastDay.first().day,
    ),
    forecast = this.forecast.toUiModel(),
    location = this.location.toUiModel(),
)

internal fun Current.toUiModel(today: Day) = CurrentUiModel(
    cloudCoverPercentage = this.cloudCoverPercentage,
    condition = this.condition.toUiModel(),
    dewPointC = this.dewPointC,
    dewPointF = this.dewPointF,
    feelsLikeC = this.feelsLikeC,
    feelsLikeF = this.feelsLikeF,
    gustKph = this.gustKph,
    gustMph = this.gustMph,
    heatIndexC = this.heatIndexC,
    heatIndexF = this.heatIndexF,
    humidity = this.humidity,
    isDay = this.isDay,
    lastUpdated = this.lastUpdated,
    lastUpdatedEpoch = this.lastUpdatedEpoch,
    precipitationIn = this.precipitationIn,
    precipitationMm = this.precipitationMm,
    pressureIn = this.pressureIn,
    pressureMb = this.pressureMb,
    temperatureC = this.temperatureC,
    temperatureF = this.temperatureF,
    uvIndex = this.uvIndex,
    visibilityKm = this.visibilityKm,
    visibilityMiles = this.visibilityMiles,
    windDegree = this.windDegree,
    windDir = this.windDir,
    windKph = this.windKph,
    windMph = this.windMph,
    windchillC = this.windchillC,
    windchillF = this.windchillF,
    minTemperatureC = today.minTemperatureC,
    minTemperatureF = today.minTemperatureF,
    maxTemperatureC = today.maxTemperatureC,
    maxTemperatureF = today.maxTemperatureF,
)

internal fun Forecast.toUiModel() = ForecastUiModel(
    forecastDay = this.forecastDay.map { it.toUiModel() }
)

internal fun Location.toUiModel() = LocationUiModel(
    country = this.country,
    latitude = this.latitude,
    localTime = this.localTime,
    localTimeEpoch = this.localTimeEpoch,
    longitude = this.longitude,
    name = this.name,
    region = this.region,
    timeZoneId = this.timeZoneId,
)

internal fun Condition.toUiModel() = ConditionUiModel(
    code = this.code,
    icon = this.icon.validateUrl(),
    text = this.text,
)

internal fun ForecastDay.toUiModel() = ForecastDayUiModel(
    astro = this.astro.toUiModel(),
    date = this.date,
    dateEpoch = this.dateEpoch,
    day = this.day.toUiModel(),
    hour = this.hour.map { it.toUiModel() }
)

internal fun Astro.toUiModel() = AstroUiModel(
    isMoonUp = this.isMoonUp,
    isSunUp = this.isSunUp,
    moonIllumination = this.moonIllumination,
    moonPhase = this.moonPhase,
    moonrise = this.moonrise,
    moonset = this.moonset,
    sunrise = this.sunrise,
    sunset = this.sunset,
)

internal fun Day.toUiModel() = DayUiModel(
    averageHumidity = this.averageHumidity,
    averageTemperatureC = this.averageTemperatureC,
    averageTemperatureF = this.averageTemperatureF,
    averageVisibilityKm = this.averageVisibilityKm,
    averageVisibilityMiles = this.averageVisibilityMiles,
    condition = this.condition.toUiModel(),
    dailyChanceOfRain = this.dailyChanceOfRain,
    dailyChanceOfSnow = this.dailyChanceOfSnow,
    dailyWillItRain = this.dailyWillItRain,
    dailyWillItSnow = this.dailyWillItSnow,
    maxTemperatureC = this.maxTemperatureC,
    maxTemperatureF = this.maxTemperatureF,
    maxWindKph = this.maxWindKph,
    maxWindMph = this.maxWindMph,
    minTemperatureC = this.minTemperatureC,
    minTemperatureF = this.minTemperatureF,
    totalPrecipitationIn = this.totalPrecipitationIn,
    totalPrecipitationMm = this.totalPrecipitationMm,
    totalSnowCm = this.totalSnowCm,
    uvIndex = this.uvIndex,
)

internal fun Hour.toUiModel() = HourUiModel(
    chanceOfRain = this.chanceOfRain,
    chanceOfSnow = this.chanceOfSnow,
    cloudCoverPercentage = this.cloudCoverPercentage,
    condition = this.condition.toUiModel(),
    dewPointC = this.dewPointC,
    dewPointF = this.dewPointF,
    feelsLikeC = this.feelsLikeC,
    feelsLikeF = this.feelsLikeF,
    gustKph = this.gustKph,
    gustMph = this.gustMph,
    heatIndexC = this.heatIndexC,
    heatIndexF = this.heatIndexF,
    humidity = this.humidity,
    isDay = this.isDay,
    precipitationIn = this.precipitationIn,
    precipitationMm = this.precipitationMm,
    pressureIn = this.pressureIn,
    pressureMb = this.pressureMb,
    snowCm = this.snowCm,
    temperatureC = this.temperatureC,
    temperatureF = this.temperatureF,
    time = this.time,
    timeEpoch = this.timeEpoch,
    uvIndex = this.uvIndex,
    visibilityKm = this.visibilityKm,
    visibilityMiles = this.visibilityMiles,
    willItRain = this.willItRain,
    willItSnow = this.willItSnow,
    windDegree = this.windDegree,
    windDir = this.windDir,
    windKph = this.windKph,
    windMph = this.windMph,
    windchillC = this.windchillC,
    windchillF = this.windchillF,
)

private fun String.validateUrl() =
    try {
        with(this.toUri()) {
            if (scheme != HTTPS_SCHEME) {
                this.buildUpon()
                    .scheme(HTTPS_SCHEME)
                    .build()
                    .toString()
            } else {
                this@validateUrl
            }
        }
    } catch (_: Exception) {
        this
    }

private const val HTTPS_SCHEME = "https"
