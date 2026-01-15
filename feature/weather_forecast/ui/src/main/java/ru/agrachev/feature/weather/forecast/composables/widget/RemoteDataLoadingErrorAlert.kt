package ru.agrachev.feature.weather.forecast.composables.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.agrachev.feature.weather.forecast.R
import ru.agrachev.feature.weather.forecast.core.RepeatRequestCallback
import ru.agrachev.feature.weather.forecast.model.WeatherForecastUiState

@Composable
internal fun RemoteDataLoadingErrorAlert(
    uiErrorStateProvider: () -> WeatherForecastUiState.Error,
    repeatRequestCallback: RepeatRequestCallback,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                text = stringResource(R.string.lbl_data_loading_error),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth(),
            )
            uiErrorStateProvider().throwable.message?.let {
                Text(
                    text = it,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth(),
                )
            }
            val enabled = !uiErrorStateProvider().isRefreshing
            Button(
                enabled = enabled,
                onClick = repeatRequestCallback,
                modifier = Modifier
                    .fillMaxWidth(fraction = .4f),
            ) {
                if (enabled) {
                    Text(
                        text = stringResource(R.string.lbl_refresh),
                    )
                } else {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(16.dp),
                    )
                }
            }
        }
    }
}
