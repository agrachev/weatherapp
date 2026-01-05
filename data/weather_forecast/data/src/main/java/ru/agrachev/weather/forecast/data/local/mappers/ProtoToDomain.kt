package ru.agrachev.weather.forecast.data.local.mappers

import ru.agrachev.weather.forecast.domain.model.Astro
import ru.agrachev.weather.forecast.domain.model.Condition
import ru.agrachev.weather.forecast.domain.model.Current
import ru.agrachev.weather.forecast.domain.model.Day
import ru.agrachev.weather.forecast.domain.model.Forecast
import ru.agrachev.weather.forecast.domain.model.ForecastDay
import ru.agrachev.weather.forecast.domain.model.Hour
import ru.agrachev.weather.forecast.domain.model.Location
import ru.agrachev.weather.forecast.domain.model.WeatherForecast
import ru.agrachev.feature.weather.forecast.persistence.proto.Astro as AstroProto
import ru.agrachev.feature.weather.forecast.persistence.proto.Condition as ConditionProto
import ru.agrachev.feature.weather.forecast.persistence.proto.Current as CurrentProto
import ru.agrachev.feature.weather.forecast.persistence.proto.Day as DayProto
import ru.agrachev.feature.weather.forecast.persistence.proto.Forecast as ForecastProto
import ru.agrachev.feature.weather.forecast.persistence.proto.ForecastDay as ForecastDayProto
import ru.agrachev.feature.weather.forecast.persistence.proto.Hour as HourProto
import ru.agrachev.feature.weather.forecast.persistence.proto.Location as LocationProto
import ru.agrachev.feature.weather.forecast.persistence.proto.WeatherForecast as WeatherForecastProto

internal fun WeatherForecastProto.toDomainModel() = WeatherForecast(
    current = this.current.toDomainModel(),
    forecast = this.forecast.toDomainModel(),
    location = this.location.toDomainModel(),
)

internal fun CurrentProto.toDomainModel() = Current(
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

internal fun ConditionProto.toDomainModel() = Condition(
    code = this.code,
    icon = this.icon,
    text = this.text,
)

internal fun ForecastProto.toDomainModel() = Forecast(
    forecastDay = this.forecastDayList.map { it.toDomainModel() }
)

internal fun ForecastDayProto.toDomainModel() = ForecastDay(
    astro = this.astro.toDomainModel(),
    date = this.date,
    dateEpoch = this.dateEpoch,
    day = this.day.toDomainModel(),
    hour = this.hourList.map { it.toDomainModel() }
)

internal fun AstroProto.toDomainModel() = Astro(
    isMoonUp = this.isMoonUp,
    isSunUp = this.isSunUp,
    moonIllumination = this.moonIllumination,
    moonPhase = this.moonPhase,
    moonrise = this.moonrise,
    moonset = this.moonset,
    sunrise = this.sunrise,
    sunset = this.sunset,
)

internal fun DayProto.toDomainModel() = Day(
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

internal fun HourProto.toDomainModel() = Hour(
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

internal fun LocationProto.toDomainModel() = Location(
    country = this.country,
    latitude = this.latitude,
    localTime = this.localTime,
    localTimeEpoch = this.localTimeEpoch,
    longitude = this.longitude,
    name = this.name,
    region = this.region,
    timeZoneId = this.timeZoneId,
)
