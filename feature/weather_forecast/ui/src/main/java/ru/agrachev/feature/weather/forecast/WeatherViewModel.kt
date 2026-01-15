package ru.agrachev.feature.weather.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.agrachev.core.domain.FailureBridge
import ru.agrachev.core.presentation.WhileSubscribedWithDelay
import ru.agrachev.feature.weather.forecast.core.WeatherForecastIntent
import ru.agrachev.feature.weather.forecast.core.WeatherForecastLabel
import ru.agrachev.feature.weather.forecast.core.WeatherViewModelDefinition
import ru.agrachev.feature.weather.forecast.core.asFailureState
import ru.agrachev.feature.weather.forecast.mappers.toUiModel
import ru.agrachev.feature.weather.forecast.model.WeatherForecastUiState
import ru.agrachev.weather.forecast.component.usecase.GetRefreshUpdateStatusUseCase
import ru.agrachev.weather.forecast.component.usecase.GetWeatherForecastUseCase
import ru.agrachev.weather.forecast.component.usecase.RefreshWeatherForecastUseCase
import timber.log.Timber

internal class WeatherViewModel(
    failureBridge: FailureBridge,
    getWeatherForecastUseCase: GetWeatherForecastUseCase,
    getRefreshUpdateStatusUseCase: GetRefreshUpdateStatusUseCase,
    private val refreshWeatherForecastUseCase: RefreshWeatherForecastUseCase,
) : ViewModel(), WeatherViewModelDefinition {

    private val intents = MutableSharedFlow<WeatherForecastIntent>()
    private val _labels = MutableSharedFlow<WeatherForecastLabel>()

    private val intentHandlerFlow
        get() = flow<Nothing> {
            intents.collect { intent ->
                when (intent) {
                    is WeatherForecastIntent.RequestForecast ->
                        refreshWeatherForecastUseCase()
                }
            }
        }

    override val uiStates = merge(
        intentHandlerFlow,

        getWeatherForecastUseCase()
            .map { WeatherForecastUiState.DataAvailable(content = it.toUiModel()) },
        getRefreshUpdateStatusUseCase()
            .map { currentUiState.duplicate(isRefreshing = it) },
        failureBridge()
            .map(::failureUiState)
    )
        .catch {
            Timber.e(it, "caught an exception while fetching a forecast")
            emit(failureUiState(it))
        }
        .flowOn(Dispatchers.Default)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribedWithDelay(),
            initialValue = WeatherForecastUiState.Loading,
        )

    override val labels = _labels.asSharedFlow()

    override val currentUiState: WeatherForecastUiState by uiStates::value

    override fun accept(intent: WeatherForecastIntent) {
        viewModelScope.launch {
            intents.emit(intent)
        }
    }

    private fun failureUiState(throwable: Throwable) =
        currentUiState.asFailureState(throwable).also {
            viewModelScope.launch {
                _labels.emit(
                    WeatherForecastLabel.Failure(
                        throwable = it.throwable,
                        hasContent = it.content != null,
                    )
                )
            }
        }
}
