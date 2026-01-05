package ru.agrachev.weather.forecast.data.remote.mappers

import ru.agrachev.core.domain.entity.GeoLocation
import ru.agrachev.weather.forecast.data.remote.dto.AstroDto
import ru.agrachev.weather.forecast.data.remote.dto.ConditionDto
import ru.agrachev.weather.forecast.data.remote.dto.CurrentDto
import ru.agrachev.weather.forecast.data.remote.dto.DayDto
import ru.agrachev.weather.forecast.data.remote.dto.ForecastDayDto
import ru.agrachev.weather.forecast.data.remote.dto.ForecastDto
import ru.agrachev.weather.forecast.data.remote.dto.HourDto
import ru.agrachev.weather.forecast.data.remote.dto.LocationDto
import ru.agrachev.weather.forecast.data.remote.dto.WeatherForecastDto
import ru.agrachev.weather.forecast.domain.model.Astro
import ru.agrachev.weather.forecast.domain.model.Condition
import ru.agrachev.weather.forecast.domain.model.Current
import ru.agrachev.weather.forecast.domain.model.Day
import ru.agrachev.weather.forecast.domain.model.Forecast
import ru.agrachev.weather.forecast.domain.model.ForecastDay
import ru.agrachev.weather.forecast.domain.model.Hour
import ru.agrachev.weather.forecast.domain.model.Location
import ru.agrachev.weather.forecast.domain.model.WeatherForecast

internal fun WeatherForecastDto.toDomainModel(
    sourceGeoLocation: GeoLocation? = null,
) = WeatherForecast(
    current = this.current.toDomainModel(),
    forecast = this.forecast.toDomainModel(),
    location = this.location.toDomainModel(sourceGeoLocation),
)

internal fun CurrentDto.toDomainModel() = Current(
    cloudCoverPercentage = this.cloudCoverPercentage,
    condition = this.condition.toDomainModel(),
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
)

internal fun ForecastDto.toDomainModel() = Forecast(
    forecastDay = this.forecastDay.map { it.toDomainModel() }
)

internal fun LocationDto.toDomainModel(
    sourceGeoLocation: GeoLocation? = null
) = Location(
    country = this.country,
    latitude = sourceGeoLocation?.latitude ?: this.latitude,
    localTime = this.localTime,
    localTimeEpoch = this.localTimeEpoch,
    longitude = sourceGeoLocation?.longitude ?: this.longitude,
    name = this.name,
    region = this.region,
    timeZoneId = this.timeZoneId,
)

internal fun ConditionDto.toDomainModel() = Condition(
    code = this.code,
    icon = this.icon,
    text = this.text,
)

internal fun ForecastDayDto.toDomainModel() = ForecastDay(
    astro = this.astro.toDomainModel(),
    date = this.date,
    dateEpoch = this.dateEpoch,
    day = this.day.toDomainModel(),
    hour = this.hour.map { it.toDomainModel() }
)

internal fun AstroDto.toDomainModel() = Astro(
    isMoonUp = this.isMoonUp > 0,
    isSunUp = this.isSunUp > 0,
    moonIllumination = this.moonIllumination,
    moonPhase = this.moonPhase,
    moonrise = this.moonrise,
    moonset = this.moonset,
    sunrise = this.sunrise,
    sunset = this.sunset,
)

internal fun DayDto.toDomainModel() = Day(
    averageHumidity = this.averageHumidity,
    averageTemperatureC = this.averageTemperatureC,
    averageTemperatureF = this.averageTemperatureF,
    averageVisibilityKm = this.averageVisibilityKm,
    averageVisibilityMiles = this.averageVisibilityMiles,
    condition = this.condition.toDomainModel(),
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

internal fun HourDto.toDomainModel() = Hour(
    chanceOfRain = this.chanceOfRain,
    chanceOfSnow = this.chanceOfSnow,
    cloudCoverPercentage = this.cloudCoverPercentage,
    condition = this.condition.toDomainModel(),
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
