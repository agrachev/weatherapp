package ru.agrachev.feature.weather.forecast.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.agrachev.feature.weather.forecast.WeatherViewModel
import ru.agrachev.location.data.di.locationDataModule
import ru.agrachev.weather.forecast.component.usecase.GetWeatherForecastUseCase
import ru.agrachev.weather.forecast.data.di.weatherForecastDataModule

val weatherForecastFeatureModule = module {
    includes(
        weatherForecastDataModule,
        locationDataModule,
    )
    viewModelOf(::WeatherViewModel)
    factoryOf(::GetWeatherForecastUseCase)
}
