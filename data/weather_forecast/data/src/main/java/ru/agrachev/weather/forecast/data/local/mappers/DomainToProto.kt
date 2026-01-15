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

internal fun WeatherForecast.toProtoModel() = WeatherForecastProto.newBuilder().run {
    this@toProtoModel.let {
        current = it.current.toProtoModel()
        forecast = it.forecast.toProtoModel()
        location = it.location.toProtoModel()
        timeStamp = it.timeStamp
        build()
    }
}

internal fun Current.toProtoModel() = CurrentProto.newBuilder().run {
    this@toProtoModel.let {
        cloudCoverPercentage = it.cloudCoverPercentage
        condition = it.condition.toProtoModel()
        dewPointC = it.dewPointC
        dewPointF = it.dewPointF
        feelsLikeC = it.feelsLikeC
        feelsLikeF = it.feelsLikeF
        gustKph = it.gustKph
        gustMph = it.gustMph
        heatIndexC = it.heatIndexC
        heatIndexF = it.heatIndexF
        humidity = it.humidity
        isDay = it.isDay
        lastUpdated = it.lastUpdated
        lastUpdatedEpoch = it.lastUpdatedEpoch
        precipitationIn = it.precipitationIn
        precipitationMm = it.precipitationMm
        pressureIn = it.pressureIn
        pressureMb = it.pressureMb
        temperatureC = it.temperatureC
        temperatureF = it.temperatureF
        uvIndex = it.uvIndex
        visibilityKm = it.visibilityKm
        visibilityMiles = it.visibilityMiles
        windDegree = it.windDegree
        windDir = it.windDir
        windKph = it.windKph
        windMph = it.windMph
        windchillC = it.windchillC
        windchillF = it.windchillF
        build()
    }
}

internal fun Condition.toProtoModel() = ConditionProto.newBuilder().run {
    this@toProtoModel.let {
        code = it.code
        icon = it.icon
        text = it.text
        build()
    }
}

internal fun Forecast.toProtoModel() = ForecastProto.newBuilder().run {
    this@toProtoModel.let {
        addAllForecastDay(it.forecastDay.map { item -> item.toProtoModel() })
        build()
    }
}

internal fun ForecastDay.toProtoModel() = ForecastDayProto.newBuilder().run {
    this@toProtoModel.let {
        astro = it.astro.toProtoModel()
        date = it.date
        dateEpoch = it.dateEpoch
        day = it.day.toProtoModel()
        addAllHour(it.hour.map { item -> item.toProtoModel() })
        build()
    }
}

internal fun Astro.toProtoModel() = AstroProto.newBuilder().run {
    this@toProtoModel.let {
        isMoonUp = it.isMoonUp
        isSunUp = it.isSunUp
        moonIllumination = it.moonIllumination
        moonPhase = it.moonPhase
        moonrise = it.moonrise
        moonset = it.moonset
        sunrise = it.sunrise
        sunset = it.sunset
        build()
    }
}

internal fun Day.toProtoModel() = DayProto.newBuilder().run {
    this@toProtoModel.let {
        averageHumidity = it.averageHumidity
        averageTemperatureC = it.averageTemperatureC
        averageTemperatureF = it.averageTemperatureF
        averageVisibilityKm = it.averageVisibilityKm
        averageVisibilityMiles = it.averageVisibilityMiles
        condition = it.condition.toProtoModel()
        dailyChanceOfRain = it.dailyChanceOfRain
        dailyChanceOfSnow = it.dailyChanceOfSnow
        dailyWillItRain = it.dailyWillItRain
        dailyWillItSnow = it.dailyWillItSnow
        maxTemperatureC = it.maxTemperatureC
        maxTemperatureF = it.maxTemperatureF
        maxWindKph = it.maxWindKph
        maxWindMph = it.maxWindMph
        minTemperatureC = it.minTemperatureC
        minTemperatureF = it.minTemperatureF
        totalPrecipitationIn = it.totalPrecipitationIn
        totalPrecipitationMm = it.totalPrecipitationMm
        totalSnowCm = it.totalSnowCm
        uvIndex = it.uvIndex
        build()
    }
}

internal fun Hour.toProtoModel() = HourProto.newBuilder().run {
    this@toProtoModel.let {
        chanceOfRain = it.chanceOfRain
        chanceOfSnow = it.chanceOfSnow
        cloudCoverPercentage = it.cloudCoverPercentage
        condition = it.condition.toProtoModel()
        dewPointC = it.dewPointC
        dewPointF = it.dewPointF
        feelsLikeC = it.feelsLikeC
        feelsLikeF = it.feelsLikeF
        gustKph = it.gustKph
        gustMph = it.gustMph
        heatIndexC = it.heatIndexC
        heatIndexF = it.heatIndexF
        humidity = it.humidity
        isDay = it.isDay
        precipitationIn = it.precipitationIn
        precipitationMm = it.precipitationMm
        pressureIn = it.pressureIn
        pressureMb = it.pressureMb
        snowCm = it.snowCm
        temperatureC = it.temperatureC
        temperatureF = it.temperatureF
        time = it.time
        timeEpoch = it.timeEpoch
        uvIndex = it.uvIndex
        visibilityKm = it.visibilityKm
        visibilityMiles = it.visibilityMiles
        willItRain = it.willItRain
        willItSnow = it.willItSnow
        windDegree = it.windDegree
        windDir = it.windDir
        windKph = it.windKph
        windMph = it.windMph
        windchillC = it.windchillC
        windchillF = it.windchillF
        build()
    }
}

internal fun Location.toProtoModel() = LocationProto.newBuilder().run {
    this@toProtoModel.let {
        country = it.country
        latitude = it.latitude
        localTime = it.localTime
        localTimeEpoch = it.localTimeEpoch
        longitude = it.longitude
        name = it.name
        region = it.region
        timeZoneId = it.timeZoneId
        build()
    }
}
