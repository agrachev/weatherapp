package ru.agrachev.feature.weather.forecast.composables.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.agrachev.core.presentation.theme.WeatherAppTheme
import ru.agrachev.feature.weather.forecast.composables.widget.DailyForecast
import ru.agrachev.feature.weather.forecast.composables.widget.HourlyForecast
import ru.agrachev.feature.weather.forecast.composables.widget.RealtimeForecast
import ru.agrachev.feature.weather.forecast.composables.widget.WeatherRequestFailedDialog
import ru.agrachev.feature.weather.forecast.model.UiState

@Composable
internal fun WeatherScreenContent(
    uiStateProvider: () -> UiState,
    repeatRequestCallback: () -> Unit,
    dismissAlertDialogCallback: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier then Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        val uiState = uiStateProvider()
        when {
            uiState.content != null -> {
                uiState.content.forecast.also {
                    RealtimeForecast(
                        realtimeForecastProvider = {
                            it.current
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                    )
                    HourlyForecast(
                        hourlyForecastProvider = {
                            it.forecast.forecastDay[0].hour
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                    )
                    DailyForecast(
                        dailyForecastProvider = {
                            it.forecast
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                    )
                }
            }

            uiState is UiState.Loading -> {

            }

            uiState is UiState.Error -> {
                WeatherRequestFailedDialog(
                    repeatRequestCallback = repeatRequestCallback,
                    dismissAlertDialogCallback = dismissAlertDialogCallback,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherScreenContentPreview() {
    WeatherAppTheme {
        WeatherScreenContent(
            uiStateProvider = {
                UiState.Loading
            },
            repeatRequestCallback = {

            },
            dismissAlertDialogCallback = {

            },
            modifier = Modifier
                .fillMaxSize(),
        )
    }
}
