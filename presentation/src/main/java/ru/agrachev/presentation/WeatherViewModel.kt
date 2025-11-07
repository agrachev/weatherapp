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
import ru.agrachev.presentation.mappers.toUiModel
import ru.agrachev.presentation.model.UiState

internal class WeatherViewModel(
    private val getWeatherForecastUseCase: GetWeatherForecastUseCase,
    private val locationProvider: LocationProvider,
) : ViewModel() {

    var currentUiState = UiState()
        private set

    private val intents = MutableSharedFlow<MainIntent>(1)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiStates = flowOf(flow {
        requestWeatherForecast()
        intents.collect { intent ->
            when (intent) {
                is MainIntent.RequestForecast -> {
                    requestWeatherForecast()
                }

                is MainIntent.DismissAlert -> {
                    emit(
                        currentUiState.copy(
                            isError = false,
                        )
                    )
                }

                is MainIntent.StartListenToLocationUpdates -> {
                    locationProvider.startListenToLocationUpdates()
                }
            }
        }
    }, flow {
        locationProvider.locations
            .collect {
                requestWeatherForecast()
            }
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

    fun accept(intent: MainIntent) {
        viewModelScope.launch {
            intents.emit(intent)
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
                Log.d(
                    "${this@WeatherViewModel::class.java.simpleName}",
                    "caught exception when fetching forecast",
                    e,
                )
                currentUiState.copy(
                    isLoading = false,
                    isError = true,
                )
            }
        )
    }
}
