package ru.agrachev.feature.location.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.agrachev.geocode.data.di.geocodeDataModule
import ru.agrachev.location.component.usecase.GetCurrentAddressUseCase
import ru.agrachev.location.component.usecase.ToggleLocationUpdatesUseCase
import ru.agrachev.location.data.di.locationDataModule

val locationFeatureModule = module {
    includes(
        geocodeDataModule,
        locationDataModule,
    )
    //viewModelOf(::WeatherViewModel)
    factoryOf(::GetCurrentAddressUseCase)
    factoryOf(::ToggleLocationUpdatesUseCase)
}
