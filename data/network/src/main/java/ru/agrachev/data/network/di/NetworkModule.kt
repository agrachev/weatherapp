package ru.agrachev.data.network.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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
        WeatherApiConfig(
            apiKey = API_KEY,
            days = DAYS,
        )
    }
    singleOf<WeatherForecastRepository, Retrofit, WeatherApiConfig>(
        ::WeatherForecastRemoteRepository
    )
    factory {
        OkHttpClient()
    }
}

const val BASE_URL = "https://api.weatherapi.com/"
private const val API_KEY = "fa8b3df74d4042b9aa7135114252304"
private const val DAYS = 3
