package ru.agrachev.feature.weather.forecast.composables.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.agrachev.feature.weather.forecast.model.HourUiModel

@Composable
internal fun HourlyForecastLazyRow(
    hourlyForecast: List<HourUiModel>,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        contentPadding = PaddingValues(all = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        modifier = modifier,
    ) {
        items(
            count = hourlyForecast.size,
            key = { hourlyForecast[it].time }
        ) { index ->
            HourlyForecastTile(
                model = hourlyForecast[index],
                modifier = Modifier
                    .width(48.dp)
                    .height(100.dp)
            )
        }
    }
}
