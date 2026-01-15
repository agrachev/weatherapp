package ru.agrachev.feature.weather.forecast.composables.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.agrachev.core.presentation.composables.FeatureContainer
import ru.agrachev.core.presentation.theme.WeatherAppTheme
import ru.agrachev.feature.location.definition.LocationFeatureDefinition
import ru.agrachev.feature.weather.forecast.composables.widget.CenteredProgressIndicator
import ru.agrachev.feature.weather.forecast.composables.widget.DailyForecast
import ru.agrachev.feature.weather.forecast.composables.widget.HourlyForecast
import ru.agrachev.feature.weather.forecast.composables.widget.RealtimeForecast
import ru.agrachev.feature.weather.forecast.composables.widget.RefreshForecastContainer
import ru.agrachev.feature.weather.forecast.composables.widget.RemoteDataLoadingErrorAlert
import ru.agrachev.feature.weather.forecast.core.RepeatRequestCallback
import ru.agrachev.feature.weather.forecast.model.WeatherForecastUiModel
import ru.agrachev.feature.weather.forecast.model.WeatherForecastUiState

@Composable
internal fun WeatherScreenContent(
    uiStateProvider: () -> WeatherForecastUiState,
    repeatRequestCallback: RepeatRequestCallback,
    onDataAvailable: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (val uiState = uiStateProvider()) {
        is WeatherForecastUiState.Loading ->
            CenteredProgressIndicator(
                modifier = modifier,
            )

        else -> {
            RefreshForecastContainer(
                uiStateProvider = uiStateProvider,
                repeatRequestCallback = repeatRequestCallback,
                modifier = modifier then Modifier
                    .padding(horizontal = 16.dp)
            ) {
                val hasContent = uiState.content?.also {
                    WeatherForecastContent(
                        weatherForecastUiModel = it,
                        modifier = Modifier
                            .fillMaxSize(),
                    )
                } != null
                when {
                    !hasContent && uiState is WeatherForecastUiState.Error ->
                        RemoteDataLoadingErrorAlert(
                            uiErrorStateProvider = { uiState },
                            repeatRequestCallback = repeatRequestCallback,
                            modifier = Modifier
                                .fillMaxSize(),
                        )

                    hasContent && uiState is WeatherForecastUiState.DataAvailable ->
                        onDataAvailable()
                }
            }
        }
    }
}

@Composable
private fun WeatherForecastContent(
    weatherForecastUiModel: WeatherForecastUiModel,
    modifier: Modifier = Modifier,
) {
    val model by rememberUpdatedState(weatherForecastUiModel)
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier then Modifier
            .verticalScroll(rememberScrollState()),
    ) {
        FeatureContainer(
            featureId = LocationFeatureDefinition.id,
            modifier = Modifier
                .fillMaxWidth(),
        )
        RealtimeForecast(
            realtimeForecastProvider = {
                model.current
            },
            modifier = Modifier
                .fillMaxWidth(),
        )
        HourlyForecast(
            hourlyForecastProvider = {
                model.forecast.forecastDay[0].hour
            },
            modifier = Modifier
                .fillMaxWidth(),
        )
        DailyForecast(
            dailyForecastProvider = {
                model.forecast
            },
            modifier = Modifier
                .fillMaxWidth(),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherScreenContentPreview() {
    WeatherAppTheme {
        WeatherScreenContent(
            uiStateProvider = {
                WeatherForecastUiState.Loading
            },
            repeatRequestCallback = {

            },
            onDataAvailable = {

            },
            modifier = Modifier
                .fillMaxSize(),
        )
    }
}
