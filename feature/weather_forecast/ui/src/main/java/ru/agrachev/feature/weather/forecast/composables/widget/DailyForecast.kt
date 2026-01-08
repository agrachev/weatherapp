package ru.agrachev.feature.weather.forecast.composables.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.agrachev.feature.weather.forecast.R
import ru.agrachev.feature.weather.forecast.model.ForecastUiModel

@Composable
internal fun DailyForecast(
    dailyForecastProvider: () -> ForecastUiModel,
    modifier: Modifier = Modifier,
) {
    val dailyForecast = dailyForecastProvider()
    val size = dailyForecast.forecastDay.size
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        Text(
            text = pluralStringResource(R.plurals.section_title_daily_forecast, size, size),
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth(),
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)),
        ) {
            repeat(size) { index ->
                DailyForecastTile(
                    model = dailyForecast.forecastDay[index],
                    modifier = Modifier
                        .fillMaxWidth(),
                )
            }
        }
    }
}
