package ru.agrachev.feature.weather.forecast

import org.koin.core.module.Module
import ru.agrachev.core.presentation.feature.Feature
import ru.agrachev.core.presentation.feature.UiContent
import ru.agrachev.feature.weather.forecast.composables.screen.WeatherScreen
import ru.agrachev.feature.weather.forecast.definition.WeatherForecastFeatureDefinition
import ru.agrachev.feature.weather.forecast.di.weatherForecastFeatureModule

internal class WeatherForecastFeature : Feature<Module> {

    override val definition = WeatherForecastFeatureDefinition

    override val diModule = weatherForecastFeatureModule

    override val uiContent: UiContent = ::WeatherScreen
}
