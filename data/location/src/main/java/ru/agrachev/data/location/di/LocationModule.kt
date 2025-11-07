package ru.agrachev.data.location.di

import android.content.Context
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.agrachev.data.location.provider.CurrentLocationProvider
import ru.agrachev.domain.repository.LocationProvider

val locationModule = module {
    singleOf<LocationProvider, Context>(::CurrentLocationProvider)
}
