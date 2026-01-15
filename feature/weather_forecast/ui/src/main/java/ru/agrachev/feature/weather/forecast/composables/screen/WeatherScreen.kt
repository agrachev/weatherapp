package ru.agrachev.feature.weather.forecast.composables.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import org.koin.compose.getKoin
import org.koin.compose.viewmodel.koinViewModel
import ru.agrachev.core.presentation.currentLocale
import ru.agrachev.feature.weather.forecast.R
import ru.agrachev.feature.weather.forecast.WeatherViewModel
import ru.agrachev.feature.weather.forecast.core.DATE_PATTERN
import ru.agrachev.feature.weather.forecast.core.LocalDateFormatter
import ru.agrachev.feature.weather.forecast.core.RepeatRequestCallback
import ru.agrachev.feature.weather.forecast.core.WeatherForecastIntent
import ru.agrachev.feature.weather.forecast.core.WeatherForecastLabel
import ru.agrachev.feature.weather.forecast.core.WeatherViewModelDefinition
import ru.agrachev.feature.weather.forecast.di.weatherForecastViewModelScopeQualifier
import java.time.format.DateTimeFormatter

@Composable
internal fun WeatherScreen(
    viewModel: WeatherViewModelDefinition = koinViewModel<WeatherViewModel>(
        scope = with(weatherForecastViewModelScopeQualifier) {
            getKoin().getOrCreateScope(this.value, this)
        },
    ),
) {
    val uiState by viewModel.uiStates.collectAsStateWithLifecycle(viewModel.currentUiState)
    val currentLocale = LocalConfiguration.currentLocale
    val dateFormatter = remember(currentLocale) {
        DateTimeFormatter.ofPattern(DATE_PATTERN, currentLocale)
    }
    CompositionLocalProvider(
        LocalDateFormatter provides dateFormatter,
    ) {
        val snackbarHostState = remember { SnackbarHostState() }
        Scaffold(
            snackbarHost = {
                SnackbarHost(snackbarHostState)
            },
            modifier = Modifier
                .fillMaxSize(),
        ) { innerPadding ->
            val repeatRequestCallback = remember {
                {
                    viewModel.accept(WeatherForecastIntent.RequestForecast)
                }
            }
            WeatherScreenContent(
                uiStateProvider = { uiState },
                repeatRequestCallback = repeatRequestCallback,
                onDataAvailable = {
                    snackbarHostState.currentSnackbarData?.dismiss()
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            )
            CollectLabels(viewModel.labels, snackbarHostState, repeatRequestCallback)
        }
    }
}

@Composable
internal fun CollectLabels(
    labels: Flow<WeatherForecastLabel>,
    snackbarHostState: SnackbarHostState,
    repeatRequestCallback: RepeatRequestCallback,
) {
    val message = stringResource(R.string.lbl_data_loading_error)
    val actionLabel = stringResource(R.string.lbl_refresh)
    LaunchedEffect(snackbarHostState) {
        val showSnackbarContext = Job(coroutineContext.job)
        labels.collect {
            when (it) {
                is WeatherForecastLabel.Failure -> {
                    if (it.hasContent) {
                        showSnackbarContext.cancelChildren()
                        launch(showSnackbarContext) {
                            val result = snackbarHostState.showSnackbar(
                                message,
                                actionLabel,
                                withDismissAction = true,
                            )
                            if (result == SnackbarResult.ActionPerformed) {
                                repeatRequestCallback()
                            }
                        }
                    }
                }
            }
        }
    }
}
