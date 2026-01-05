package ru.agrachev.feature.weather.forecast.composables.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ru.agrachev.feature.weather.forecast.theme.Typography

@Composable
internal fun AppBar(
    loadingStateProvider: () -> Boolean,
    locationNameProvider: () -> String,
    repeatRequestCallback: () -> Unit,
    startListenToLocationUpdatesCallback: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier then Modifier
            .height(IntrinsicSize.Min),
    ) {
        val locationName = locationNameProvider()
        if (locationName.isNotBlank()) {
            RequestCurrentLocationIcon(
                onLocationRequestedCallback = startListenToLocationUpdatesCallback,
                modifier = Modifier
                    .fillMaxHeight(fraction = .65f)
                    .aspectRatio(1f, true)
                    .clip(CircleShape),
            )
        }
        Text(
            text = locationName,
            style = Typography.headlineLarge,
            autoSize = TextAutoSize.StepBased(),
            maxLines = 1,
            modifier = Modifier
                .weight(1f),
        )
        RefreshButton(
            loadingStateProvider = loadingStateProvider,
            repeatRequestCallback = repeatRequestCallback,
        )
    }
}
