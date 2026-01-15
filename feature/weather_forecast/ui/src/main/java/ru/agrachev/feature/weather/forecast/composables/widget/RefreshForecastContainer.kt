package ru.agrachev.feature.weather.forecast.composables.widget

import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import ru.agrachev.feature.weather.forecast.core.RepeatRequestCallback
import ru.agrachev.feature.weather.forecast.model.WeatherForecastUiState

@Composable
internal fun RefreshForecastContainer(
    uiStateProvider: () -> WeatherForecastUiState,
    repeatRequestCallback: RepeatRequestCallback,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val pullToRefreshState = rememberPullToRefreshState()
    val isRefreshing by remember {
        derivedStateOf {
            uiStateProvider().isRefreshing && pullToRefreshState.distanceFraction > 0f
        }
    }
    PullToRefreshBox(
        state = pullToRefreshState,
        isRefreshing = isRefreshing,
        onRefresh = repeatRequestCallback,
        modifier = modifier,
    ) {
        content()
    }
}
