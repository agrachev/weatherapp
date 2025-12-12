package ru.agrachev.data.network.di

import Weather_App.network.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import ru.agrachev.data.network.api.WeatherApi
import ru.agrachev.data.network.repository.WeatherForecastRemoteRepository
import ru.agrachev.domain.model.WeatherApiConfig
import ru.agrachev.domain.repository.WeatherForecastRepository

val networkModule = module {
    single {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .callFactory { request ->
                get<OkHttpClient>()
                    .newCall(request)
            }
            .build()
    }
    single {
        get<Retrofit>().create<WeatherApi>()
    }
    single {
        WeatherApiConfig(
            apiKey = API_KEY,
            days = DAYS,
        )
    }
    singleOf<WeatherForecastRepository, WeatherApi, WeatherApiConfig>(
        ::WeatherForecastRemoteRepository
    )
    single {
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.NONE)
    }
    factory {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }
}

private const val BASE_URL = "https://api.weatherapi.com/"
private const val API_KEY = BuildConfig.WEATHER_API_KEY
private const val DAYS = 3
