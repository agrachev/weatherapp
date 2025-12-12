package ru.agrachev.presentation.composables.screen

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
import ru.agrachev.presentation.composables.widget.AppBar
import ru.agrachev.presentation.composables.widget.DailyForecast
import ru.agrachev.presentation.composables.widget.HourlyForecast
import ru.agrachev.presentation.composables.widget.RealtimeForecast
import ru.agrachev.presentation.composables.widget.WeatherRequestFailedDialog
import ru.agrachev.presentation.model.UiState
import ru.agrachev.presentation.model.WeatherForecastUiModel
import ru.agrachev.presentation.theme.WeatherAppTheme

@Composable
internal fun WeatherScreenContent(
    loadingStateProvider: () -> Boolean,
    errorStateProvider: () -> Boolean,
    forecastStateProvider: () -> WeatherForecastUiModel?,
    repeatRequestCallback: () -> Unit,
    dismissAlertDialogCallback: () -> Unit,
    startListenToLocationUpdatesCallback: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier then Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        val forecast = forecastStateProvider()
        if (errorStateProvider()) {
            WeatherRequestFailedDialog(
                repeatRequestCallback = repeatRequestCallback,
                dismissAlertDialogCallback = dismissAlertDialogCallback,
            )
        }
        val forecastStateProvider by rememberUpdatedState(forecastStateProvider)
        AppBar(
            loadingStateProvider = loadingStateProvider,
            locationNameProvider = {
                forecastStateProvider()?.location?.name.orEmpty()
            },
            repeatRequestCallback = repeatRequestCallback,
            startListenToLocationUpdatesCallback = startListenToLocationUpdatesCallback,
            modifier = Modifier
                .fillMaxWidth(),
        )
        forecast?.let {
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
}

@Preview(showBackground = true)
@Composable
fun WeatherScreenContentPreview() {
    WeatherAppTheme {
        val state = UiState(
            isLoading = true,
            isError = false,
            forecast = null,
        )
        WeatherScreenContent(
            loadingStateProvider = {
                state.isLoading
            },
            errorStateProvider = {
                state.isError
            },
            forecastStateProvider = {
                state.forecast
            },
            repeatRequestCallback = {

            },
            dismissAlertDialogCallback = {

            },
            startListenToLocationUpdatesCallback = {

            },
            modifier = Modifier
                .fillMaxSize(),
        )
    }
}
