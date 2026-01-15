package ru.agrachev.feature.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.agrachev.core.domain.FailureBridge
import ru.agrachev.core.presentation.WhileSubscribedWithDelay
import ru.agrachev.feature.location.core.LocationIntent
import ru.agrachev.feature.location.core.LocationViewModelDefinition
import ru.agrachev.feature.location.mappers.toUiModel
import ru.agrachev.feature.location.model.LocationUiState
import ru.agrachev.location.component.usecase.GetAddressSuggestionsUseCase
import ru.agrachev.location.component.usecase.GetCurrentAddressUseCase
import ru.agrachev.location.component.usecase.GetLocationUpdatesStatusUseCase
import ru.agrachev.location.component.usecase.ToggleLocationUpdatesUseCase
import ru.agrachev.location.component.usecase.UpdateSelectedLocationUseCase
import timber.log.Timber

internal class LocationViewModel(
    getCurrentAddressUseCase: GetCurrentAddressUseCase,
    private val getAddressSuggestionsUseCase: GetAddressSuggestionsUseCase,
    getLocationUpdatesUseCase: GetLocationUpdatesStatusUseCase,
    private val toggleLocationUpdatesUseCase: ToggleLocationUpdatesUseCase,
    private val updateSelectedLocationUseCase: UpdateSelectedLocationUseCase,
    private val failureBridge: FailureBridge,
) : ViewModel(), LocationViewModelDefinition {

    private val intents = MutableSharedFlow<LocationIntent>()
    private val locationName = MutableStateFlow("")

    private val intentHandlerFlow
        get() = flow<Nothing> {
            intents.collect { intent ->
                when (intent) {
                    is LocationIntent.RequestAddressSuggestions ->
                        locationName.value = intent.locationName

                    is LocationIntent.RequestLocationUpdates ->
                        toggleLocationUpdatesUseCase(intent.toggleOn)

                    is LocationIntent.UpdateSelectedLocation ->
                        updateSelectedLocationUseCase(intent.geoLocation)
                }
            }
        }

    // TODO map to failure UI state event and implement some visual feedback
    private val failureHandlerFlow
        get() = flow<Nothing> {
            failureBridge()
                .collect {
                    Timber.e(it, "Caught an exception during geocode request")
                }
        }

    private val addressSuggestionsFlow
        get() = locationName.map {
            getAddressSuggestionsUseCase(it)
        }

    override val uiStates = merge(
        intentHandlerFlow,
        failureHandlerFlow,

        combine(
            getCurrentAddressUseCase(),
            addressSuggestionsFlow,
            getLocationUpdatesUseCase(),
        ) { currentAddress, addressSuggestions, locationUpdatesStatus ->
            LocationUiState(
                currentAddress = currentAddress?.toUiModel()
                    ?.formattedDescription.orEmpty(),
                addressSuggestions = addressSuggestions.map {
                    it.toUiModel()
                },
                isListeningToLocationUpdates = locationUpdatesStatus,
            )
        }
    )
        .catch {
            Timber.e(it, "caught an exception while handling location data")
        }
        .flowOn(Dispatchers.Default)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribedWithDelay(),
            initialValue = LocationUiState(),
        )

    override val currentUiState: LocationUiState by uiStates::value

    override fun accept(intent: LocationIntent) {
        viewModelScope.launch {
            intents.emit(intent)
        }
    }
}
