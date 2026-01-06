package ru.agrachev.geocode.data.repository

import android.content.Context
import android.location.Geocoder
import android.os.Build
import ru.agrachev.core.domain.model.GeoLocation
import ru.agrachev.geocode.domain.model.Address
import ru.agrachev.geocode.domain.repository.GeocodeRepository
import java.util.Locale
import kotlin.coroutines.suspendCoroutine

internal class AndroidGeocodeRepository(
    context: Context,
    locale: Locale = Locale.getDefault(),
) : GeocodeRepository {

    private val geocoder: Geocoder = Geocoder(context, locale)

    override suspend fun getAddressesFromLocationName(locationName: String): List<Address> {
        val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            suspendCoroutine {
                geocoder.getFromLocationName(
                    locationName, MAX_RESULTS,
                ) { addresses ->
                    it.resumeWith(Result.success(addresses))
                }
            }
        } else {
            @Suppress("deprecation")
            geocoder.getFromLocationName(locationName, MAX_RESULTS)
        }
        return result
            ?.map { address ->
                Address(
                    description = List(address.maxAddressLineIndex + 1) { index ->
                        address.getAddressLine(index)
                    }
                        .filterNotNull()
                        .joinToString(separator = "\n"),
                    geoLocation = GeoLocation(
                        longitude = address.longitude,
                        latitude = address.latitude,
                    ),
                )
            } ?: emptyList()
    }

    override suspend fun getAddressesFromLocation(geoLocation: GeoLocation): List<Address> {
        val result = with(geoLocation) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                suspendCoroutine {
                    geocoder.getFromLocation(
                        latitude, longitude, MAX_RESULTS,
                    ) { addresses ->
                        it.resumeWith(Result.success(addresses))
                    }
                }
            } else {
                @Suppress("deprecation")
                geocoder.getFromLocation(latitude, longitude, MAX_RESULTS)
            }
        }
        return result
            ?.map { address ->
                Address(
                    description = List(address.maxAddressLineIndex + 1) { index ->
                        address.getAddressLine(index)
                    }
                        .filterNotNull()
                        .joinToString(separator = "\n"),
                    geoLocation = geoLocation,
                )
            } ?: emptyList()
    }
}

private const val MAX_RESULTS = 3
