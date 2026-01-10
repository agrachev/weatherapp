package ru.agrachev.feature.weather.forecast

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
import ru.agrachev.feature.weather.forecast.core.MainIntent
import ru.agrachev.feature.weather.forecast.core.WeatherViewModelDefinition
import ru.agrachev.feature.weather.forecast.mappers.toUiModel
import ru.agrachev.feature.weather.forecast.model.WeatherForecastUiState
import ru.agrachev.weather.forecast.component.usecase.GetWeatherForecastUseCase

internal class WeatherViewModel(
    getWeatherForecastUseCase: GetWeatherForecastUseCase,
) : ViewModel(), WeatherViewModelDefinition {

    private val intents = MutableSharedFlow<MainIntent>()

    private val intentHandlerFlow
        get() = flow<Nothing> {
            intents.collect { intent ->
                when (intent) {
                    is MainIntent.RequestForecast -> {

                    }

                    is MainIntent.DismissAlert -> {

                    }
                }
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val uiStates = flowOf(
        intentHandlerFlow,

        getWeatherForecastUseCase()
            .map {
                WeatherForecastUiState.DataAvailable(
                    content = it.toUiModel(),
                )
            }
    )
        .flattenMerge()
        .catch {
            Log.e(TAG, "caught an exception while fetching a forecast", it)
            WeatherForecastUiState.Error(currentUiState.content, it)
        }
        .flowOn(Dispatchers.Default)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribedWithDelay(),
            initialValue = WeatherForecastUiState.Loading,
        )

    override val currentUiState: WeatherForecastUiState by uiStates::value

    override fun accept(intent: MainIntent) {
        viewModelScope.launch {
            intents.emit(intent)
        }
    }
}

private const val TAG = "WeatherViewModel"
