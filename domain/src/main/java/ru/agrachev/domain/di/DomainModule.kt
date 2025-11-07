package ru.agrachev.domain.di

import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.agrachev.domain.usecase.GetWeatherForecastUseCase

val domainModule = module {
    single {
        Dispatchers.IO
    }
    singleOf(::GetWeatherForecastUseCase)
}
