package ru.agrachev.presentation.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.agrachev.presentation.WeatherViewModel

val presentationModule = module {
    viewModelOf(::WeatherViewModel)
}
