package ru.agrachev.data.location.provider

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
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.sync.Semaphore
import ru.agrachev.domain.model.GeoLocation
import ru.agrachev.domain.repository.LocationProvider
import kotlin.math.pow
import kotlin.math.round

internal class CurrentLocationProvider(
    context: Context,
) : LocationProvider {

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

    @Volatile
    override var currentLocation = MoscowLocation
        private set

    override val locations = callbackFlow {
        semaphore.acquire()
        tokenSource = CancellationTokenSource()
        locationService.getCurrentLocation(
            Priority.PRIORITY_LOW_POWER,
            object : CancellationToken() {
                override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                    tokenSource.token

                override fun isCancellationRequested() = false
            }
        )
            .addOnSuccessListener { location: Location? ->
                location?.let { loc ->
                    GeoLocation(
                        latitude = loc.latitude.roundToDecimalPlaces(ACCURACY),
                        longitude = loc.longitude.roundToDecimalPlaces(ACCURACY),
                    ).let {
                        if (it != currentLocation) {
                            trySend(it)
                        }
                    }
                }
            }
        awaitClose {
            tokenSource.cancel()
        }
    }
        .distinctUntilChanged()
        .onEach {
            currentLocation = it
        }

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

private fun Double.roundToDecimalPlaces(precision: Int): Float {
    val multiplier = 10.0.pow(precision)
    return (round(this * multiplier) / multiplier).toFloat()
}

private val MoscowLocation = GeoLocation(
    latitude = 55.7569f,
    longitude = 37.6151f,
)

private const val ACCURACY = 4
