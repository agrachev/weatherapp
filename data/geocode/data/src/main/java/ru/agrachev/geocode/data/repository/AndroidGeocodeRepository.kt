package ru.agrachev.geocode.data.repository

import android.content.Context
import android.location.Geocoder
import android.os.Build
import ru.agrachev.core.domain.model.GeoLocation
import ru.agrachev.geocode.domain.model.Address
import ru.agrachev.geocode.domain.repository.GeocodeRepository
import java.util.Locale
import kotlin.coroutines.suspendCoroutine
import android.location.Address as AndroidLocationAddress

internal class AndroidGeocodeRepository(
    context: Context,
    locale: Locale = Locale.getDefault(),
) : GeocodeRepository {

    private val geocoder: Geocoder = Geocoder(context, locale)

    override suspend fun getAddressesFromLocationName(locationName: String) =
        when {
            locationName.isEmpty() -> emptyList()
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ->
                suspendCoroutine {
                    geocoder.getFromLocationName(
                        locationName, MAX_RESULTS,
                    ) { addresses ->
                        it.resumeWith(Result.success(addresses))
                    }
                }

            else -> {
                @Suppress("deprecation")
                geocoder.getFromLocationName(locationName, MAX_RESULTS)
            }
        }
            .toDomainModels {
                GeoLocation(
                    latitude = it.latitude,
                    longitude = it.longitude,
                )
            }

    override suspend fun getAddressesFromLocation(geoLocation: GeoLocation) =
        with(geoLocation) {
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
            .toDomainModels { geoLocation }

    private inline fun List<AndroidLocationAddress>?.toDomainModels(
        geoLocationProvider: (AndroidLocationAddress) -> GeoLocation,
    ) = this?.map {
        Address(
            featureName = it.featureName,
            thoroughfare = it.thoroughfare,
            subThoroughfare = it.subThoroughfare,
            locality = it.locality,
            subLocality = it.subLocality,
            adminArea = it.adminArea,
            subAdminArea = it.subAdminArea,
            countryName = it.countryName,
            countryCode = it.countryCode,
            postalCode = it.postalCode,
            addressLines = List(it.maxAddressLineIndex + 1) { index ->
                it.getAddressLine(index)
            },
            geoLocation = geoLocationProvider(it),
        )
    } ?: emptyList()
}

private const val MAX_RESULTS = 3
