package ru.agrachev.feature.weather.forecast.composables.widget

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.agrachev.feature.weather.forecast.R

@Composable
internal fun WeatherRequestFailedDialog(
    repeatRequestCallback: () -> Unit,
    dismissAlertDialogCallback: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = dismissAlertDialogCallback,
        text = {
            Text(
                text = stringResource(R.string.lbl_alert_text),
            )
        },
        confirmButton = {
            TextButton(onClick = repeatRequestCallback) {
                Text(
                    text = stringResource(R.string.lbl_retry),
                )
            }
        },
        dismissButton = {
            TextButton(onClick = dismissAlertDialogCallback) {
                Text(
                    text = stringResource(R.string.lbl_close),
                )
            }
        },
    )
}
