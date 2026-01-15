package ru.agrachev.location.component.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.agrachev.core.domain.util.FailureHandler
import ru.agrachev.geocode.domain.repository.GeocodeRepository

class GetAddressSuggestionsUseCase(
    private val geocodeRepository: GeocodeRepository,
    private val ioDispatcher: CoroutineDispatcher,
    private val failureHandler: FailureHandler,
) {

    suspend operator fun invoke(locationName: String) =
        withContext(ioDispatcher) {
            try {
                geocodeRepository.getAddressesFromLocationName(locationName)
            } catch (e: Exception) {
                failureHandler(e)
                emptyList()
            }
        }
}
