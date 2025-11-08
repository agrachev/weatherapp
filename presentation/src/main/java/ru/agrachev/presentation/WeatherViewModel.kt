package ru.agrachev.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.agrachev.domain.repository.LocationProvider
import ru.agrachev.domain.usecase.GetWeatherForecastUseCase
import ru.agrachev.presentation.core.MainIntent
import ru.agrachev.presentation.core.WeatherViewModelDefinition
import ru.agrachev.presentation.mappers.toUiModel
import ru.agrachev.presentation.model.UiState

internal class WeatherViewModel(
    private val getWeatherForecastUseCase: GetWeatherForecastUseCase,
    private val locationProvider: LocationProvider,
) : ViewModel(), WeatherViewModelDefinition {

    private val intents = MutableSharedFlow<MainIntent>()

    override var currentUiState = UiState()
        private set

    @OptIn(ExperimentalCoroutinesApi::class)
    override val uiStates = flowOf(
        flow {
            requestInitialDataAndListenToIntents()
        },
        flow {
            requestForecastOnLocationChange()
        })
        .flattenMerge()
        .onEach {
            currentUiState = it
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = currentUiState,
        )

    override fun accept(intent: MainIntent) {
        viewModelScope.launch {
            intents.emit(intent)
        }
    }

    private suspend fun FlowCollector<UiState>.requestInitialDataAndListenToIntents() {
        requestWeatherForecast()
        intents.collect { intent ->
            when (intent) {
                is MainIntent.RequestForecast -> {
                    requestWeatherForecast()
                }

                is MainIntent.DismissAlert -> {
                    emit(
                        currentUiState.copy(
                            isLoading = false,
                            isError = false,
                        )
                    )
                }

                is MainIntent.StartListenToLocationUpdates -> {
                    locationProvider.startListenToLocationUpdates()
                }
            }
        }
    }

    private suspend fun FlowCollector<UiState>.requestForecastOnLocationChange() {
        locationProvider.locations
            .collect {
                requestWeatherForecast()
            }
    }

    private suspend fun FlowCollector<UiState>.requestWeatherForecast() {
        emit(
            currentUiState.copy(
                isLoading = true,
                isError = false,
            )
        )
        emit(
            try {
                currentUiState.copy(
                    isLoading = false,
                    isError = false,
                    forecast = getWeatherForecastUseCase()
                        .toUiModel(),
                )
            } catch (e: Exception) {
                currentCoroutineContext().ensureActive()
                Log.d(TAG, "caught an exception while fetching a forecast", e)
                currentUiState.copy(
                    isLoading = false,
                    isError = true,
                )
            }
        )
    }
}

private val TAG = WeatherViewModel::class.java.simpleName
