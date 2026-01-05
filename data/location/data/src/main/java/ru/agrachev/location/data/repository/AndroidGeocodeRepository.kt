package ru.agrachev.location.data.repository

/*internal class AndroidGeocodeRepository(
    context: Context,
    locale: Locale = Locale.getDefault(),
) : GeocodeRepository {

    private val geocoder: Geocoder = Geocoder(context, locale)

    override suspend fun getAddressList(locationName: String): List<Address> {
        val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            suspendCoroutine {
                geocoder.getFromLocationName(
                    locationName, MAX_RESULTS,
                ) { addresses ->
                    it.resumeWith(Result.success(addresses))
                }
            }
        } else {
            val r = geocoder.getFromLocation(56.210115800000004, 44.099501499999995, 3)
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
                        .joinToString("\n"),
                    geoLocation = GeoLocation(
                        longitude = address.longitude,
                        latitude = address.latitude,
                    ),
                )
            } ?: emptyList()
    }
}

private const val MAX_RESULTS = 3*/
