package ru.agrachev.data.network.mappers

import ru.agrachev.data.network.pojo.AstroPojo
import ru.agrachev.data.network.pojo.ConditionPojo
import ru.agrachev.data.network.pojo.CurrentPojo
import ru.agrachev.data.network.pojo.DayPojo
import ru.agrachev.data.network.pojo.ForecastPojo
import ru.agrachev.data.network.pojo.ForecastdayPojo
import ru.agrachev.data.network.pojo.HourPojo
import ru.agrachev.data.network.pojo.LocationPojo
import ru.agrachev.data.network.pojo.WeatherForecastPojo
import ru.agrachev.domain.model.Astro
import ru.agrachev.domain.model.Condition
import ru.agrachev.domain.model.Current
import ru.agrachev.domain.model.Day
import ru.agrachev.domain.model.Forecast
import ru.agrachev.domain.model.Forecastday
import ru.agrachev.domain.model.Hour
import ru.agrachev.domain.model.Location
import ru.agrachev.domain.model.WeatherForecast

internal fun WeatherForecastPojo.toDomainModel() = WeatherForecast(
    current = this.current.toDomainModel(),
    forecast = this.forecast.toDomainModel(),
    location = this.location.toDomainModel(),
)

internal fun CurrentPojo.toDomainModel() = Current(
    cloudCoveragePercentage = this.cloud,
    condition = this.condition.toDomainModel(),
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
    temperatureCelsius = this.temp_c,
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

internal fun ForecastPojo.toDomainModel() = Forecast(
    forecastday = this.forecastday.map { it.toDomainModel() }
)

internal fun LocationPojo.toDomainModel() = Location(
    country = this.country,
    latitude = this.lat,
    localtime = this.localtime,
    localtime_epoch = this.localtime_epoch,
    longitude = this.lon,
    name = this.name,
    region = this.region,
    tz_id = this.tz_id,
)

internal fun ConditionPojo.toDomainModel() = Condition(
    code = this.code,
    icon = this.icon,
    text = this.text,
)

internal fun ForecastdayPojo.toDomainModel() = Forecastday(
    astro = this.astro.toDomainModel(),
    date = this.date,
    date_epoch = this.date_epoch,
    day = this.day.toDomainModel(),
    hour = this.hour.map { it.toDomainModel() }
)

internal fun AstroPojo.toDomainModel() = Astro(
    isMoonUp = this.is_moon_up > 0,
    isSunUp = this.is_sun_up > 0,
    moon_illumination = this.is_sun_up,
    moon_phase = this.moon_phase,
    moonrise = this.moonrise,
    moonset = this.moonset,
    sunrise = this.sunrise,
    sunset = this.sunset,
)

internal fun DayPojo.toDomainModel() = Day(
    avghumidity = this.avghumidity,
    averageTemperatureCelsius = this.avgtemp_c,
    averageTemperatureFahrenheit = this.avgtemp_f,
    avgvis_km = this.avgvis_km,
    avgvis_miles = this.avgvis_miles,
    condition = this.condition.toDomainModel(),
    daily_chance_of_rain = this.daily_chance_of_rain,
    daily_chance_of_snow = this.daily_chance_of_snow,
    daily_will_it_rain = this.daily_will_it_rain,
    daily_will_it_snow = this.daily_will_it_snow,
    maxtemperatureCelsius = this.maxtemp_c,
    maxtemp_f = this.maxtemp_f,
    maxwind_kph = this.maxwind_kph,
    maxwind_mph = this.maxwind_mph,
    mintemperatureCelsius = this.mintemp_c,
    mintemp_f = this.mintemp_f,
    totalprecip_in = this.totalprecip_in,
    totalprecip_mm = this.totalprecip_mm,
    totalsnow_cm = this.totalsnow_cm,
    uv = this.uv,
)

internal fun HourPojo.toDomainModel() = Hour(
    chance_of_rain = this.chance_of_rain,
    chance_of_snow = this.chance_of_snow,
    cloudCoveragePercentage = this.cloud,
    condition = this.condition.toDomainModel(),
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
    temperatureCelsius = this.temp_c,
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
