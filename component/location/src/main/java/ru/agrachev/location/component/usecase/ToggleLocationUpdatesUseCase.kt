package ru.agrachev.location.component.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.agrachev.location.domain.LocationRepository

class ToggleLocationUpdatesUseCase(
    private val locationRepository: LocationRepository,
    private val ioDispatcher: CoroutineDispatcher,
) {

    suspend operator fun invoke(toggleOn: Boolean) = withContext(ioDispatcher) {
        with(locationRepository) {
            if (toggleOn) {
                startListenToLocationUpdates()
            } else {
                stopListenToLocationUpdates()
            }
        }
    }
}
