package ru.agrachev.weather.forecast.data.di

import Weather_App.weather_forecast_data.BuildConfig
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.agrachev.core.domain.di.declareLocalRepository
import ru.agrachev.core.domain.di.declareRemoteRepository
import ru.agrachev.weather.forecast.data.local.processor.WeatherForecastDataTransformer
import ru.agrachev.weather.forecast.data.local.processor.WeatherForecastSerializationApi
import ru.agrachev.weather.forecast.data.remote.api.WeatherForecastApi
import ru.agrachev.weather.forecast.data.remote.impl.WeatherForecastRemoteRepository
import ru.agrachev.weather.forecast.data.remote.util.WeatherApiConfig
import ru.agrachev.weather.forecast.data.repository.OfflineFirstWeatherForecastRepository
import ru.agrachev.weather.forecast.domain.repository.WeatherForecastRepository

val weatherForecastDataModule = module {
    declareLocalRepository(
        storageFileName = "weather_forecast_storage",
        serializationApi = WeatherForecastSerializationApi,
        dataTransformer = WeatherForecastDataTransformer,
    )
    declareRemoteRepository(
        baseUrl = BASE_URL,
        apiClass = WeatherForecastApi::class,
    ) {
        singleOf(::WeatherForecastRemoteRepository)
    }
    single {
        WeatherApiConfig(
            apiKey = API_KEY,
            days = DAYS,
        )
    }
    singleOf(::OfflineFirstWeatherForecastRepository) bind WeatherForecastRepository::class
}

private const val BASE_URL = "https://api.weatherapi.com/"
private const val API_KEY = BuildConfig.WEATHER_API_KEY
private const val DAYS = 3
