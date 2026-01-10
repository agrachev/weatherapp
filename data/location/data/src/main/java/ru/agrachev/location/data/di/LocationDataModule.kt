package ru.agrachev.location.data.di

import android.content.Context
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import org.koin.core.definition.Callbacks
import org.koin.core.module.dsl.onOptions
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.agrachev.core.domain.di.declareRemoteRepository
import ru.agrachev.location.data.remote.api.WhoIsApi
import ru.agrachev.location.data.remote.impl.WhoIsRepository
import ru.agrachev.location.data.repository.CurrentLocationRepository
import ru.agrachev.location.data.repository.FusedLocationRepository
import ru.agrachev.location.data.repository.ManualLocationRepository
import ru.agrachev.location.data.repository.StoredLocationRepository
import ru.agrachev.location.domain.ListenableLocationRepository
import ru.agrachev.location.domain.LocationRepository
import ru.agrachev.location.domain.ReadOnlyLocationRepository
import ru.agrachev.location.domain.WriteableLocationRepository
import kotlin.coroutines.EmptyCoroutineContext

val locationDataModule = module {
    declareRemoteRepository(
        baseUrl = WHO_IS_API_BASE_URL,
        apiClass = WhoIsApi::class,
    ) {
        singleOf(::WhoIsRepository)
    }
    single {
        CoroutineScope(EmptyCoroutineContext)
    }.onOptions {
        callbacks = Callbacks(
            onClose = {
                it?.cancel()
            }
        )
    }
    single {
        LocationServices.getFusedLocationProviderClient(get<Context>())
    }
    singleOf(::StoredLocationRepository) bind ReadOnlyLocationRepository::class
    singleOf(::FusedLocationRepository) bind ListenableLocationRepository::class
    singleOf(::ManualLocationRepository) bind WriteableLocationRepository::class
    singleOf(::CurrentLocationRepository) bind LocationRepository::class
}

private const val WHO_IS_API_BASE_URL = "https://ipwho.is/"
