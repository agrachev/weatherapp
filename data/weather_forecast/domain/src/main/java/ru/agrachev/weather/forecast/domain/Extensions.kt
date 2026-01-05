package ru.agrachev.weather.forecast.domain

import ru.agrachev.core.domain.repository.LocalRepository
import ru.agrachev.weather.forecast.domain.model.WeatherForecast

typealias WeatherForecastLocalRepository = LocalRepository<WeatherForecast>
