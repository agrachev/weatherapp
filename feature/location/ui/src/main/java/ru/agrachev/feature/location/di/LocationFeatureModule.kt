package ru.agrachev.feature.location.di

import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Qualifier
import org.koin.core.annotation.Single
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.parameter.ParametersHolder
import org.koin.core.scope.Scope
import org.koin.dsl.module
import ru.agrachev.core.presentation.feature.Feature
import ru.agrachev.core.presentation.feature.FeatureModule
import ru.agrachev.feature.location.LocationFeature
import ru.agrachev.feature.location.LocationViewModel
import ru.agrachev.geocode.data.di.geocodeDataModule
import ru.agrachev.location.component.usecase.GetCurrentAddressUseCase
import ru.agrachev.location.component.usecase.ToggleLocationUpdatesUseCase
import ru.agrachev.location.data.di.locationDataModule
import org.koin.core.annotation.Module as KoinModule

internal val locationFeatureModule = module {
    includes(
        geocodeDataModule,
        locationDataModule,
    )
    factoryOf(::ToggleLocationUpdatesUseCase)
    factory {
        GetCurrentAddressUseCase(
            get(it), get(), get(),
        )
    }
    viewModel {
        LocationViewModel(
            get(it), get(),
        )
    }
}

private inline fun <reified T : Any> Scope.get(parametersHolder: ParametersHolder) =
    this.get<T>(parameters = { parametersHolder })

@KoinModule
@Configuration
class LocationFeatureDefaultModule : FeatureModule<Module> {

    @Single
    @Qualifier(LocationFeatureDefaultModule::class)
    override fun provideFeature(): Feature<Module> = LocationFeature()
}
