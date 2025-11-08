package ru.agrachev.presentation.mappers

import androidx.core.net.toUri
import ru.agrachev.domain.model.Astro
import ru.agrachev.domain.model.Condition
import ru.agrachev.domain.model.Current
import ru.agrachev.domain.model.Day
import ru.agrachev.domain.model.Forecast
import ru.agrachev.domain.model.Forecastday
import ru.agrachev.domain.model.Hour
import ru.agrachev.domain.model.Location
import ru.agrachev.domain.model.WeatherForecast
import ru.agrachev.presentation.model.AstroUiModel
import ru.agrachev.presentation.model.ConditionUiModel
import ru.agrachev.presentation.model.CurrentUiModel
import ru.agrachev.presentation.model.DayUiModel
import ru.agrachev.presentation.model.ForecastUiModel
import ru.agrachev.presentation.model.ForecastdayUiModel
import ru.agrachev.presentation.model.HourUiModel
import ru.agrachev.presentation.model.LocationUiModel
import ru.agrachev.presentation.model.WeatherForecastUiModel

internal fun WeatherForecast.toUiModel() = WeatherForecastUiModel(
    current = this.current.toUiModel(),
    forecast = this.forecast.toUiModel(),
    location = this.location.toUiModel(),
)

internal fun Current.toUiModel() = CurrentUiModel(
    cloudCoveragePercentage = this.cloudCoveragePercentage,
    condition = this.condition.toUiModel(),
    dewpoint_c = this.dewpoint_c,
    dewpoint_f = this.dewpoint_c,
    feelslike_c = this.feelslike_c,
    feelslike_f = this.feelslike_f,
    gust_kph = this.gust_kph,
    gust_mph = this.gust_mph,
    heatindex_c = this.heatindex_c,
    heatindex_f = this.heatindex_f,
    humidity = this.humidity,
    is_day = this.is_day,
    last_updated = this.last_updated,
    last_updated_epoch = this.last_updated_epoch,
    precip_in = this.precip_in,
    precip_mm = this.precip_mm,
    pressure_in = this.pressure_in,
    pressure_mb = this.pressure_mb,
    temperatureCelsius = this.temperatureCelsius,
    temp_f = this.temp_f,
    uv = this.uv,
    vis_km = this.vis_km,
    vis_miles = this.vis_miles,
    wind_degree = this.wind_degree,
    wind_dir = this.wind_dir,
    wind_kph = this.wind_kph,
    wind_mph = this.wind_mph,
    windchill_c = this.windchill_c,
    windchill_f = this.windchill_f,
)

internal fun Forecast.toUiModel() = ForecastUiModel(
    forecastday = this.forecastday.map { it.toUiModel() }
)

internal fun Location.toUiModel() = LocationUiModel(
    country = this.country,
    latitude = this.latitude,
    localtime = this.localtime,
    localtime_epoch = this.localtime_epoch,
    longitude = this.longitude,
    name = this.name,
    region = this.region,
    tz_id = this.tz_id,
)

internal fun Condition.toUiModel() = ConditionUiModel(
    code = this.code,
    icon = this.icon.validateUrl(),
    text = this.text,
)

internal fun Forecastday.toUiModel() = ForecastdayUiModel(
    astro = this.astro.toUiModel(),
    date = this.date,
    date_epoch = this.date_epoch,
    day = this.day.toUiModel(),
    hour = this.hour.map { it.toUiModel() }
)

internal fun Astro.toUiModel() = AstroUiModel(
    isMoonUp = this.isMoonUp,
    isSunUp = this.isSunUp,
    moon_illumination = this.moon_illumination,
    moon_phase = this.moon_phase,
    moonrise = this.moonrise,
    moonset = this.moonset,
    sunrise = this.sunrise,
    sunset = this.sunset,
)

internal fun Day.toUiModel() = DayUiModel(
    avghumidity = this.avghumidity,
    averageTemperatureCelsius = this.averageTemperatureCelsius,
    averageTemperatureFahrenheit = this.averageTemperatureFahrenheit,
    avgvis_km = this.avgvis_km,
    avgvis_miles = this.avgvis_miles,
    condition = this.condition.toUiModel(),
    daily_chance_of_rain = this.daily_chance_of_rain,
    daily_chance_of_snow = this.daily_chance_of_snow,
    daily_will_it_rain = this.daily_will_it_rain,
    daily_will_it_snow = this.daily_will_it_snow,
    maxtemperatureCelsius = this.maxtemperatureCelsius,
    maxtemp_f = this.maxtemp_f,
    maxwind_kph = this.maxwind_kph,
    maxwind_mph = this.maxwind_mph,
    mintemperatureCelsius = this.mintemperatureCelsius,
    mintemp_f = this.mintemp_f,
    totalprecip_in = this.totalprecip_in,
    totalprecip_mm = this.totalprecip_mm,
    totalsnow_cm = this.totalsnow_cm,
    uv = this.uv,
)

internal fun Hour.toUiModel() = HourUiModel(
    chance_of_rain = this.chance_of_rain,
    chance_of_snow = this.chance_of_snow,
    cloudCoveragePercentage = this.cloudCoveragePercentage,
    condition = this.condition.toUiModel(),
    dewpoint_c = this.dewpoint_c,
    dewpoint_f = this.dewpoint_f,
    feelslike_c = this.feelslike_c,
    feelslike_f = this.feelslike_f,
    gust_kph = this.gust_kph,
    gust_mph = this.gust_mph,
    heatindex_c = this.heatindex_c,
    heatindex_f = this.heatindex_f,
    humidity = this.humidity,
    is_day = this.is_day,
    precip_in = this.precip_in,
    precip_mm = this.precip_mm,
    pressure_in = this.pressure_in,
    pressure_mb = this.pressure_mb,
    snow_cm = this.snow_cm,
    temperatureCelsius = this.temperatureCelsius,
    temp_f = this.temp_f,
    time = this.time,
    time_epoch = this.time_epoch,
    uv = this.uv,
    vis_km = this.vis_km,
    vis_miles = this.vis_miles,
    will_it_rain = this.will_it_rain,
    will_it_snow = this.will_it_snow,
    wind_degree = this.wind_degree,
    wind_dir = this.wind_dir,
    wind_kph = this.wind_kph,
    wind_mph = this.wind_mph,
    windchill_c = this.windchill_c,
    windchill_f = this.windchill_f,
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
