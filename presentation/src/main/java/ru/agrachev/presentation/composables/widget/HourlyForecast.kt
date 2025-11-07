package ru.agrachev.presentation.composables.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.agrachev.presentation.R
import ru.agrachev.presentation.model.HourUiModel

@Composable
internal fun HourlyForecast(
    hourlyForecastProvider: () -> List<HourUiModel>,
    modifier: Modifier = Modifier,
) {
    val hourlyForecast = hourlyForecastProvider()
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.section_title_hourly_forecast),
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth(),
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = RoundedCornerShape(size = 16.dp),
                )
        ) {
            HourlyForecastLazyRow(
                hourlyForecast = hourlyForecast,
                modifier = Modifier
                    .fillMaxWidth(),
            )
        }
    }
}
