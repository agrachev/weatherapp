package ru.agrachev.location.component.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.agrachev.geocode.domain.repository.GeocodeRepository

class GetAddressSuggestionsUseCase(
    private val geocodeRepository: GeocodeRepository,
    private val ioDispatcher: CoroutineDispatcher,
) {

    suspend operator fun invoke(locationName: String) =
        withContext(ioDispatcher) {
            geocodeRepository.getAddressesFromLocationName(locationName)
        }
}
