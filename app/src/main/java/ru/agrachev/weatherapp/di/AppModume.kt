package ru.agrachev.weatherapp.di

import org.koin.dsl.module
import ru.agrachev.core.data.di.coreDataModule

val appModule = module {
    includes(
        coreDataModule,
    )
}
