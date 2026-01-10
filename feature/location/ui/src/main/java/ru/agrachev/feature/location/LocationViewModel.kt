package ru.agrachev.feature.location

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
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

internal class LocationViewModel(
    getCurrentAddressUseCase: GetCurrentAddressUseCase,
    private val getAddressSuggestionsUseCase: GetAddressSuggestionsUseCase,
    getLocationUpdatesUseCase: GetLocationUpdatesStatusUseCase,
    private val toggleLocationUpdatesUseCase: ToggleLocationUpdatesUseCase,
    private val updateSelectedLocationUseCase: UpdateSelectedLocationUseCase,
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

    private val addressSuggestionsFlow
        get() = locationName.map {
            getAddressSuggestionsUseCase(it)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val uiStates = flowOf(
        intentHandlerFlow,

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
        .flattenMerge()
        .catch {
            Log.e(TAG, "caught an exception while handling location data", it)
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

private const val TAG = "LocationViewModel"
