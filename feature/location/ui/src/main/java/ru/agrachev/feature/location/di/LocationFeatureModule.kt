package ru.agrachev.feature.location.di

import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Single
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.parameter.ParametersHolder
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module
import ru.agrachev.core.presentation.feature.Feature
import ru.agrachev.core.presentation.feature.FeatureModule
import ru.agrachev.feature.location.LocationFeature
import ru.agrachev.feature.location.LocationViewModel
import ru.agrachev.geocode.data.di.geocodeDataModule
import ru.agrachev.geocode.domain.repository.GeocodeRepository
import ru.agrachev.location.component.usecase.GetAddressSuggestionsUseCase
import ru.agrachev.location.component.usecase.GetCurrentAddressUseCase
import ru.agrachev.location.component.usecase.GetLocationUpdatesStatusUseCase
import ru.agrachev.location.component.usecase.ToggleLocationUpdatesUseCase
import ru.agrachev.location.component.usecase.UpdateSelectedLocationUseCase
import ru.agrachev.location.data.di.locationDataModule
import org.koin.core.annotation.Module as KoinModule
import org.koin.core.annotation.Qualifier as KoinQualifier

internal val locationViewModelScopeQualifier by lazy {
    named(name = "locationViewModelScopeId")
}

internal val locationFeatureModule = module {
    includes(
        geocodeDataModule,
        locationDataModule,
    )
    factoryOf(::GetLocationUpdatesStatusUseCase)
    factoryOf(::ToggleLocationUpdatesUseCase)
    factoryOf(::UpdateSelectedLocationUseCase)

    scope(locationViewModelScopeQualifier) {
        scoped(locationViewModelScopeQualifier) {
            get<GeocodeRepository>(it)
        }
        factory {
            GetAddressSuggestionsUseCase(
                get(it, locationViewModelScopeQualifier),
                get(),
            )
        }
        factory {
            GetCurrentAddressUseCase(
                get(it, locationViewModelScopeQualifier),
                get(),
                get(),
            )
        }
        viewModel {
            LocationViewModel(
                get(it),
                get(it),
                get(),
                get(),
                get(),
            )
        }
    }
}

private inline fun <reified T : Any> Scope.get(
    parametersHolder: ParametersHolder,
    qualifier: Qualifier? = null,
) = this.get<T>(qualifier) { parametersHolder }

@KoinModule
@Configuration
class LocationFeatureDefaultModule : FeatureModule<Module> {

    @Single
    @KoinQualifier(LocationFeatureDefaultModule::class)
    override fun provideFeature(): Feature<Module> = LocationFeature()
}
