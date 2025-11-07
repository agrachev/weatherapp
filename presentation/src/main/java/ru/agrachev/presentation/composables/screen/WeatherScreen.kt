package ru.agrachev.presentation.composables.screen

import android.os.Build
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.agrachev.presentation.WeatherViewModel
import ru.agrachev.presentation.core.DATE_PATTERN
import ru.agrachev.presentation.core.LocalDateFormatter
import ru.agrachev.presentation.core.MainIntent
import java.time.format.DateTimeFormatter

@Composable
internal fun WeatherScreen(
    viewModel: WeatherViewModel,
) {
    val uiState by viewModel.uiStates.collectAsStateWithLifecycle(viewModel.currentUiState)
    val currentLocale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        LocalConfiguration.current.locales[0]
    } else {
        LocalConfiguration.current.locale
    }
    val dateFormatter = remember(currentLocale) {
        DateTimeFormatter.ofPattern(DATE_PATTERN, currentLocale)
    }
    val isError by remember {
        derivedStateOf {
            uiState.isError
        }
    }
    val isLoading by remember {
        derivedStateOf {
            uiState.isLoading
        }
    }
    val forecast by remember {
        derivedStateOf {
            uiState.forecast
        }
    }
    CompositionLocalProvider(
        LocalDateFormatter provides dateFormatter,
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
        ) { innerPadding ->
            WeatherScreenContent(
                loadingStateProvider = { isLoading },
                errorStateProvider = { isError },
                forecastStateProvider = { forecast },
                repeatRequestCallback = {
                    viewModel.accept(MainIntent.RequestForecast)
                },
                dismissAlertDialogCallback = {
                    viewModel.accept(MainIntent.DismissAlert)
                },
                startListenToLocationUpdatesCallback = {
                    viewModel.accept(MainIntent.StartListenToLocationUpdates)
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            )
        }
    }
}
