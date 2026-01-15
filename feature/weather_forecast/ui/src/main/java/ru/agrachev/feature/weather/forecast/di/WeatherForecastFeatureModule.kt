package ru.agrachev.feature.weather.forecast.di

import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Qualifier
import org.koin.core.annotation.Single
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.agrachev.core.domain.di.declareFailureEntities
import ru.agrachev.core.presentation.feature.Feature
import ru.agrachev.core.presentation.feature.FeatureModule
import ru.agrachev.feature.weather.forecast.WeatherForecastFeature
import ru.agrachev.feature.weather.forecast.WeatherViewModel
import ru.agrachev.location.data.di.locationDataModule
import ru.agrachev.weather.forecast.component.usecase.GetRefreshUpdateStatusUseCase
import ru.agrachev.weather.forecast.component.usecase.GetWeatherForecastUseCase
import ru.agrachev.weather.forecast.component.usecase.RefreshWeatherForecastUseCase
import ru.agrachev.weather.forecast.data.di.weatherForecastDataModule
import org.koin.core.annotation.Module as KoinModule

internal val weatherForecastViewModelScopeQualifier by lazy {
    named(name = "weatherForecastViewModelScopeQualifier")
}

internal val weatherForecastFeatureModule = module {
    includes(
        weatherForecastDataModule,
        locationDataModule,
    )
    singleOf(::GetRefreshUpdateStatusUseCase)
    scope(weatherForecastViewModelScopeQualifier) {
        declareFailureEntities()
        viewModelOf(::WeatherViewModel)
        factoryOf(::GetWeatherForecastUseCase)
        factoryOf(::RefreshWeatherForecastUseCase)
    }
}

@KoinModule
@Configuration
class WeatherForecastFeatureDefaultModule : FeatureModule<Module> {

    @Single
    @Qualifier(WeatherForecastFeatureDefaultModule::class)
    override fun provideFeature(): Feature<Module> = WeatherForecastFeature()
}
