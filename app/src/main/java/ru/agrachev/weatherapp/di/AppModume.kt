package ru.agrachev.weatherapp.di

import org.koin.dsl.module
import ru.agrachev.data.location.di.locationModule
import ru.agrachev.data.network.di.networkModule
import ru.agrachev.data.persistence.di.persistenceModule
import ru.agrachev.domain.di.domainModule
import ru.agrachev.presentation.di.presentationModule

val appModule = module {
    includes(
        locationModule,
        persistenceModule,
        networkModule,
        domainModule,
        presentationModule,
    )
}
