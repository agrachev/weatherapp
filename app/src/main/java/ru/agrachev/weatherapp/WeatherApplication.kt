package ru.agrachev.weatherapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.ksp.generated.startKoin
import ru.agrachev.core.presentation.feature.Feature
import ru.agrachev.weatherapp.di.appModule
import ru.agrachev.weatherapp.util.KoinApp

class WeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        KoinApp.startKoin {
            androidContext(this@WeatherApplication)
        }.koin.apply {
            loadModules(
                appModule + getAll<Feature<Module>>().map {
                    it.diModule
                }
            )
        }
    }
}
