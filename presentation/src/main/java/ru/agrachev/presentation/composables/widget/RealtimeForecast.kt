package ru.agrachev.presentation.composables.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import ru.agrachev.presentation.R
import ru.agrachev.presentation.model.CurrentUiModel
import ru.agrachev.presentation.theme.Typography

@Composable
internal inline fun RealtimeForecast(
    realtimeForecastProvider: () -> CurrentUiModel,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.section_title_now),
            fontWeight = FontWeight.Medium,
            modifier = Modifier.fillMaxWidth(),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max),
        ) {
            CurrentTemperature(
                realtimeForecastProvider,
                modifier = Modifier
                    .height(IntrinsicSize.Max),
            )
            CurrentWeatherCondition(
                realtimeForecastProvider,
                modifier = Modifier
                    .weight(1f),
            )
        }
        MaxMinTemperatureRow(realtimeForecastProvider)
    }
}

@Composable
private inline fun CurrentTemperature(
    realtimeForecastProvider: () -> CurrentUiModel,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
    ) {
        realtimeForecastProvider().also { current ->
            Text(
                text = stringResource(
                    R.string.lbl_degrees_single,
                    current.temperatureC.toInt(),
                ),
                style = Typography.headlineLarge.copy(
                    fontSize = 64.sp,
                ),
                modifier = Modifier
                    .fillMaxHeight(),
            )
            AsyncImage(
                model = current.condition.icon,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight(fraction = .75f)
                    .aspectRatio(1f)
                    .padding(start = 4.dp, top = 4.dp),
            )
        }
    }
}

@Composable
private inline fun CurrentWeatherCondition(
    realtimeForecastProvider: () -> CurrentUiModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        realtimeForecastProvider().also { current ->
            Text(
                text = current.condition.text,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth(),
            )
            Text(
                text = stringResource(R.string.lbl_feels_like, current.feelsLikeC.toInt()),
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth(),
            )
        }
    }
}

@Composable
private inline fun MaxMinTemperatureRow(
    realtimeForecastProvider: () -> CurrentUiModel,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier,
    ) {
        realtimeForecastProvider().also { current ->
            Text(
                text = stringResource(
                    R.string.lbl_maximum, current.maxTemperatureC.toInt(),
                ),
            )
            Text(
                text = "•",
            )
            Text(
                text = stringResource(
                    R.string.lbl_minimum, current.minTemperatureC.toInt(),
                ),
            )
        }
    }
}
