package ru.agrachev.location.data.repository

import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Semaphore
import ru.agrachev.core.domain.model.GeoLocation
import ru.agrachev.location.domain.repository.ListenableLocationRepository
import ru.agrachev.location.domain.repository.RestartableLocationRepository
import timber.log.Timber

internal class FusedLocationRepository(
    locationService: FusedLocationProviderClient,
) : RestartableLocationRepository(), ListenableLocationRepository {

    private val locationUpdatesListeningStatus = MutableStateFlow(false)

    private val semaphore by lazy {
        Semaphore(
            permits = 1,
            acquiredPermits = 1,
        )
    }

    @Volatile
    private lateinit var tokenSource: CancellationTokenSource

    override val locations = callbackFlow {
        Timber.d("(Re)started listenable locations flow")
        launch {
            while (true) {
                semaphore.acquire()
                tokenSource = CancellationTokenSource()
                try {
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
                } catch (e: SecurityException) {
                    throw IllegalStateException(e)
                }
            }
        }
        awaitClose {
            stopListenToLocationUpdates()
        }
    }.asRestartable()

    override val isListeningToLocationUpdates =
        locationUpdatesListeningStatus.asSharedFlow()

    override fun startListenToLocationUpdates() {
        if (semaphore.availablePermits == 0) {
            locationUpdatesListeningStatus.value = true
            try {
                semaphore.release()
            } catch (e: IllegalStateException) {
                Timber.e(e, "Unable to release a permit on a semaphore")
            }
        }
    }

    override fun stopListenToLocationUpdates() {
        if (::tokenSource.isInitialized) {
            tokenSource.cancel()
        }
        locationUpdatesListeningStatus.value = false
        semaphore.tryAcquire()
    }
}
