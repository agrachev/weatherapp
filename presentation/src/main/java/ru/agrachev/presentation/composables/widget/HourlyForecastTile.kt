package ru.agrachev.presentation.composables.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ru.agrachev.presentation.R
import ru.agrachev.presentation.core.asUrl
import ru.agrachev.presentation.model.HourUiModel

@Composable
internal fun HourlyForecastTile(
    model: HourUiModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.lbl_degrees_single, model.temperatureCelsius.toInt()),
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        )
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(2f),
        ) {
            val chanceOfRainOrSnow = with(model) {
                will_it_rain * chance_of_rain + will_it_snow * chance_of_snow
            }
            if (chanceOfRainOrSnow > 0) {
                Text(
                    text = stringResource(R.string.lbl_percentage, chanceOfRainOrSnow),
                )
            }
            AsyncImage(
                model = model.condition.icon.asUrl(),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp),
            )
        }
        Text(
            text = model.time.split(" ").getOrNull(1).orEmpty(),
            maxLines = 1,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}
