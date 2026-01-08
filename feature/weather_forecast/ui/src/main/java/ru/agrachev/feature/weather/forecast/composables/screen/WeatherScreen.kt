package ru.agrachev.feature.weather.forecast.composables.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import ru.agrachev.core.presentation.currentLocale
import ru.agrachev.feature.weather.forecast.WeatherViewModel
import ru.agrachev.feature.weather.forecast.core.DATE_PATTERN
import ru.agrachev.feature.weather.forecast.core.LocalDateFormatter
import ru.agrachev.feature.weather.forecast.core.MainIntent
import ru.agrachev.feature.weather.forecast.core.WeatherViewModelDefinition
import java.time.format.DateTimeFormatter

@Composable
internal fun WeatherScreen(
    viewModel: WeatherViewModelDefinition = koinViewModel<WeatherViewModel>(),
) {
    val uiState by viewModel.uiStates.collectAsStateWithLifecycle(viewModel.currentUiState)
    val currentLocale = LocalConfiguration.currentLocale
    val dateFormatter = remember(currentLocale) {
        DateTimeFormatter.ofPattern(DATE_PATTERN, currentLocale)
    }
    CompositionLocalProvider(
        LocalDateFormatter provides dateFormatter,
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
        ) { innerPadding ->
            WeatherScreenContent(
                uiStateProvider = {
                    uiState
                },
                repeatRequestCallback = {
                    viewModel.accept(MainIntent.RequestForecast)
                },
                dismissAlertDialogCallback = {
                    viewModel.accept(MainIntent.DismissAlert)
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            )
        }
    }
}
