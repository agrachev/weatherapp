package ru.agrachev.geocode.data.di

import org.koin.dsl.bind
import org.koin.dsl.module
import ru.agrachev.geocode.data.repository.AndroidGeocodeRepository
import ru.agrachev.geocode.domain.repository.GeocodeRepository

val geocodeDataModule = module {
    factory {
        AndroidGeocodeRepository(get(), it[0])
    } bind GeocodeRepository::class
}
