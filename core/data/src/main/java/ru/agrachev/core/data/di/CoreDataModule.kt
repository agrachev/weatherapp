package ru.agrachev.core.data.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.agrachev.core.data.BuildConfig
import ru.agrachev.core.domain.di.RepositoryDeclarator
import ru.agrachev.core.domain.di.sharedScope

val coreDataModule = module {
    // IO Dispatcher instance
    single {
        Dispatchers.IO
    }
    // JSON converter factory
    single<Converter.Factory> {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        MoshiConverterFactory.create(moshi)
    }
    // Request logging
    single {
        HttpLoggingInterceptor()
            .setLevel(
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            )
    }
    // Request factory
    factory {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }
    // Retrofit instance
    factory {
        Retrofit.Builder()
            .baseUrl(it.get<String>(0))
            .addConverterFactory(get())
            .callFactory { request ->
                get<OkHttpClient>()
                    .newCall(request)
            }
            .build()
    }
    sharedScope.declare(
        instance = CoreDataModuleRepositoryDeclarator(this@module),
        secondaryTypes = listOf(RepositoryDeclarator::class),
    )
}
