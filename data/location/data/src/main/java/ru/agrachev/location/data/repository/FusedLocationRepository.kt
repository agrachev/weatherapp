package ru.agrachev.location.data.repository

import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.sync.Semaphore
import ru.agrachev.core.domain.entity.GeoLocation
import ru.agrachev.location.domain.ListenableLocationRepository

internal class FusedLocationRepository(
    context: Context,
) : ListenableLocationRepository {

    private val semaphore by lazy {
        Semaphore(
            permits = 1,
            acquiredPermits = 1,
        )
    }
    private val locationService by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }
    private lateinit var tokenSource: CancellationTokenSource

    override val locations = callbackFlow {
        semaphore.acquire()
        tokenSource = CancellationTokenSource()
        locationService.getCurrentLocation(
            Priority.PRIORITY_BALANCED_POWER_ACCURACY,
            object : CancellationToken() {
                override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                    tokenSource.token

                override fun isCancellationRequested() = false
            }
        )
            .addOnSuccessListener { location: Location? ->
                location?.let { loc ->
                    trySend(
                        GeoLocation(
                            latitude = loc.latitude,
                            longitude = loc.longitude,
                        )
                    )
                }
            }
        awaitClose {
            tokenSource.cancel()
        }
    }
        .distinctUntilChanged()

    override fun startListenToLocationUpdates() {
        if (semaphore.availablePermits == 0) {
            semaphore.release()
        }
    }

    override suspend fun stopListenToLocationUpdates() {
        if (::tokenSource.isInitialized) {
            tokenSource.cancel()
        }
        semaphore.acquire()
    }
}
