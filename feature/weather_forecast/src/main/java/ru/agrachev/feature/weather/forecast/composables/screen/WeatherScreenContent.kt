package ru.agrachev.feature.weather.forecast.composables.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOff
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.filter
import ru.agrachev.feature.weather.forecast.composables.widget.DailyForecast
import ru.agrachev.feature.weather.forecast.composables.widget.HourlyForecast
import ru.agrachev.feature.weather.forecast.composables.widget.RealtimeForecast
import ru.agrachev.feature.weather.forecast.composables.widget.WeatherRequestFailedDialog
import ru.agrachev.feature.weather.forecast.model.UiState
import ru.agrachev.feature.weather.forecast.theme.WeatherAppTheme

@Composable
internal fun WeatherScreenContent(
    uiStateProvider: () -> UiState,
    repeatRequestCallback: () -> Unit,
    dismissAlertDialogCallback: () -> Unit,
    locationRequestChangedCallback: (String) -> Unit,
    startListenToLocationUpdatesCallback: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier then Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        val uiState = uiStateProvider()
        when {
            uiState.content != null -> {
                uiState.content.forecast.also {
                    //val forecastStateProvider by rememberUpdatedState(forecastStateProvider)
                    /*AppBar(
                        loadingStateProvider = loadingStateProvider,
                        locationNameProvider = {
                            forecastStateProvider()?.location?.name.orEmpty()
                        },
                        repeatRequestCallback = repeatRequestCallback,
                        startListenToLocationUpdatesCallback = startListenToLocationUpdatesCallback,
                        modifier = Modifier
                            .fillMaxWidth(),
                    )*/
                    LocationSearchTextField(
                        onTextChangedCallback = locationRequestChangedCallback,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    EditableExposedDropdownMenuSample()
                    RealtimeForecast(
                        realtimeForecastProvider = {
                            it.current
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                    )
                    HourlyForecast(
                        hourlyForecastProvider = {
                            it.forecast.forecastDay[0].hour
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                    )
                    DailyForecast(
                        dailyForecastProvider = {
                            it.forecast
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                    )
                }
            }

            uiState is UiState.Loading -> {

            }

            uiState is UiState.Error -> {
                WeatherRequestFailedDialog(
                    repeatRequestCallback = repeatRequestCallback,
                    dismissAlertDialogCallback = dismissAlertDialogCallback,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherScreenContentPreview() {
    WeatherAppTheme {
        WeatherScreenContent(
            uiStateProvider = {
                UiState.Loading
            },
            repeatRequestCallback = {

            },
            dismissAlertDialogCallback = {

            },
            startListenToLocationUpdatesCallback = {

            },
            locationRequestChangedCallback = {

            },
            modifier = Modifier
                .fillMaxSize(),
        )
    }
}

@Composable
private fun LocationSearchTextField(
    onTextChangedCallback: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val textFieldState = rememberTextFieldState()
    OutlinedTextField(
        state = textFieldState,
        modifier = modifier,
    )
    LaunchedEffect(textFieldState) {
        snapshotFlow {
            textFieldState.text
        }
            .filter { it.isNotEmpty() }
            .collect {
                onTextChangedCallback(it.toString())
            }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditableExposedDropdownMenuSample(
    modifier: Modifier = Modifier,
) {
    val textFieldState = rememberTextFieldState()
    val filteredOptions by remember {
        derivedStateOf {
            options.filter {
                textFieldState.text.isNotEmpty() && it.contains(
                    textFieldState.text,
                    ignoreCase = false
                )
            }
        }
    }
    val (allowExpanded, setExpanded) = remember {
        mutableStateOf(false)
    }
    val expanded = allowExpanded && filteredOptions.isNotEmpty()
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = setExpanded,
        modifier = modifier,
    ) {
        val textStyle = MaterialTheme.typography.headlineMedium
        OutlinedTextField(
            state = textFieldState,
            textStyle = textStyle,
            lineLimits = TextFieldLineLimits.SingleLine,
            shape = RoundedCornerShape(16.dp),
            placeholder = {
                Text(
                    text = "Label",
                    style = textStyle,
                )
            },
            leadingIcon = {
                IconButton(
                    onClick = {

                    },
                    modifier = Modifier
                        .padding(start = 6.dp),
                ) {
                    Icon(
                        imageVector = Icons.Filled.LocationOff,
                        contentDescription = null,
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { setExpanded(false) },
            shape = RoundedCornerShape(16.dp),
        ) {
            filteredOptions.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    onClick = {
                        textFieldState.setTextAndPlaceCursorAtEnd(option)
                        setExpanded(false)
                    },
                )
            }
        }
    }
}

val options =
    "The text that the user inputs into the text field can be used to filter the options".split(
        " ",
    )
