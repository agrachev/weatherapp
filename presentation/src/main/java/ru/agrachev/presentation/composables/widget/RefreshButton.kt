package ru.agrachev.presentation.composables.widget

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import ru.agrachev.presentation.R

@Composable
internal fun RefreshButton(
    loadingStateProvider: () -> Boolean,
    repeatRequestCallback: () -> Unit,
) {
    val isLoading = loadingStateProvider()
    val measurer = rememberTextMeasurer()
    val mt = measurer.measure(
        text = stringResource(R.string.lbl_refresh),
    )
    val horizontalPadding = with(LocalDensity.current) {
        ((mt.size.width - 16.dp.toPx()) / 2f).toDp() + HALF_OFFSET.dp
    }
    Button(
        enabled = !isLoading,
        onClick = repeatRequestCallback,
    ) {
        if (!isLoading) {
            Text(
                text = stringResource(R.string.lbl_refresh),
            )
        } else {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(horizontal = horizontalPadding)
                    .size(16.dp)
            )
        }
    }
}

private const val HALF_OFFSET = .5
