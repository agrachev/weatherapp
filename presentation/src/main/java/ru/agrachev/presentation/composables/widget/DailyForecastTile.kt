package ru.agrachev.presentation.composables.widget

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ru.agrachev.presentation.R
import ru.agrachev.presentation.core.LocalDateFormatter
import ru.agrachev.presentation.model.ForecastdayUiModel
import java.time.format.DateTimeFormatter
import java.util.Locale.getDefault
import kotlin.math.max

@Composable
internal fun DailyForecastTile(
    model: ForecastdayUiModel,
    modifier: Modifier = Modifier,
) {
    var isExpanded by rememberSaveable {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier then Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer,
            )
            .animateContentSize(
                animationSpec = spring(stiffness = Spring.StiffnessHigh),
            ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clickable {
                    isExpanded = !isExpanded
                }
                .padding(horizontal = 12.dp),
        ) {
            Text(
                text = model.date.formatDate(),
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                modifier = Modifier
                    .weight(weight = 3f),
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .weight(1f),
            ) {
                val chanceOfRainOrSnow = with(model.day) {
                    max(dailyWillItRain * dailyChanceOfRain, dailyWillItSnow * dailyChanceOfSnow)
                }
                if (chanceOfRainOrSnow > 0) {
                    Text(
                        text = stringResource(R.string.lbl_percentage, chanceOfRainOrSnow),
                    )
                }
                AsyncImage(
                    model = model.day.condition.icon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp),
                )
            }
            Text(
                text = stringResource(
                    R.string.lbl_degrees_pair,
                    model.day.maxTemperatureC.toInt(),
                    model.day.minTemperatureC.toInt(),
                ),
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            )
        }
        AnimatedVisibility(
            visible = isExpanded,
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            HourlyForecastLazyRow(
                hourlyForecast = model.hour,
                modifier = Modifier
                    .fillMaxWidth(),
            )
        }
    }
}

@Composable
@ReadOnlyComposable
private fun String.formatDate(): String {
    val date = DateTimeFormatter.ISO_DATE.parse(this)
    return LocalDateFormatter.current.format(date)
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(getDefault()) else it.toString() }
}
