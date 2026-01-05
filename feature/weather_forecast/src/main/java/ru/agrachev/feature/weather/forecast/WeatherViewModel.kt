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
import ru.agrachev.feature.weather.forecast.core.MainIntent
import ru.agrachev.feature.weather.forecast.core.WeatherViewModelDefinition
import ru.agrachev.feature.weather.forecast.mappers.toUiModel
import ru.agrachev.feature.weather.forecast.model.Content
import ru.agrachev.feature.weather.forecast.model.UiState
import ru.agrachev.weather.forecast.component.usecase.GetWeatherForecastUseCase

internal class WeatherViewModel(
    getWeatherForecastUseCase: GetWeatherForecastUseCase,
    //private val locationProvider: LocationRepository,
    //private val geocodeRepository: GeocodeRepository,
) : ViewModel(), WeatherViewModelDefinition {

    private val intents = MutableSharedFlow<MainIntent>()
//    private val locationName = MutableStateFlow("")

    private val intentHandlerFlow
        get() = flow<Nothing> {
            intents.collect { intent ->
                when (intent) {
                    is MainIntent.RequestForecast -> {

                    }

                    is MainIntent.DismissAlert -> {

                    }

                    /*is MainIntent.ListenToLocationUpdates -> with(locationProvider) {
                        if (intent.isOn) {
                            startListenToLocationUpdates()
                        } else {
                            stopListenToLocationUpdates()
                        }
                    }

                    is MainIntent.RequestAddressList -> {

                    }*/
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
        getWeatherForecastUseCase()/*.combine(addressListFlow) { weatherForecast, addressList ->
            UiState.DataAvailable(
                Content(
                    forecast = weatherForecast.toUiModel(),
                    //addressList = addressList,
                ),
            )
        }*/
            .map {
                UiState.DataAvailable(
                    Content(
                        forecast = it.toUiModel(),
                        //addressList = addressList,
                    )
                )
            }
    )
        .flattenMerge()
        .catch {
            Log.e(TAG, "caught an exception while fetching a forecast", it)
            UiState.Error(currentUiState.content, it)
        }
        .flowOn(Dispatchers.Default)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribedWithDelay(),
            initialValue = UiState.Loading,
        )

    override val currentUiState: UiState by uiStates::value

    override fun accept(intent: MainIntent) {
        viewModelScope.launch {
            intents.emit(intent)
        }
    }
}

private const val TAG = "WeatherViewModel"
