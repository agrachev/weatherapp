package ru.agrachev.feature.location

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
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
import ru.agrachev.feature.location.model.UiState
import ru.agrachev.location.component.usecase.GetCurrentAddressUseCase
import ru.agrachev.location.component.usecase.ToggleLocationUpdatesUseCase

internal class LocationViewModel(
    getCurrentAddressUseCase: GetCurrentAddressUseCase,
    private val toggleLocationUpdatesUseCase: ToggleLocationUpdatesUseCase,
) : ViewModel(), LocationViewModelDefinition {

    private val intents = MutableSharedFlow<LocationIntent>()
//    private val locationName = MutableStateFlow("")

    private val intentHandlerFlow
        get() = flow<Nothing> {
            intents.collect { intent ->
                when (intent) {
                    is LocationIntent.RequestLocationUpdates ->
                        toggleLocationUpdatesUseCase(intent.toggleOn)

                    is LocationIntent.RequestAddressList -> {

                    }
                }
            }
        }

    /*private val addressListFlow
        get() = locationName
            .filterNot { it.isEmpty() }
    .map {
        geocodeRepository.getAddressList(it)
    }*/

    @OptIn(ExperimentalCoroutinesApi::class)
    override val uiStates = flowOf(
        intentHandlerFlow,

        getCurrentAddressUseCase()/*.combine(addressListFlow) { weatherForecast, addressList ->
            UiState.DataAvailable(
                Content(
                    forecast = weatherForecast.toUiModel(),
                    //addressList = addressList,
                ),
            )
        }*/
            .map {
                currentUiState.copy(
                    text = it?.description.orEmpty(),
                )
            }
    )
        .flattenMerge()
        .catch {
            Log.e(TAG, "caught an exception while fetching a forecast", it)
            //UiState.Error(currentUiState.content, it)
        }
        .flowOn(Dispatchers.Default)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribedWithDelay(),
            initialValue = UiState(),
        )

    override val currentUiState: UiState by uiStates::value

    override fun accept(intent: LocationIntent) {
        viewModelScope.launch {
            intents.emit(intent)
        }
    }
}

private const val TAG = "LocationViewModel"
