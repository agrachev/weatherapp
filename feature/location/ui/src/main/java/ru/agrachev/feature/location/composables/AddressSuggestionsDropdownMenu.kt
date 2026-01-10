@file:OptIn(ExperimentalMaterial3Api::class)

package ru.agrachev.feature.location.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuBoxScope
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.getKoin
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.agrachev.core.presentation.currentLocale
import ru.agrachev.feature.location.LocationViewModel
import ru.agrachev.feature.location.core.LocationIntent
import ru.agrachev.feature.location.core.LocationViewModelDefinition
import ru.agrachev.feature.location.di.locationViewModelScopeQualifier

@Composable
internal fun AddressSuggestionsDropdownMenu(
    modifier: Modifier = Modifier,
    viewModel: LocationViewModelDefinition = LocalConfiguration.currentLocale.let { locale ->
        koinViewModel<LocationViewModel>(
            scope = with(locationViewModelScopeQualifier) {
                getKoin().getOrCreateScope(this.value, this)
            },
            parameters = {
                parametersOf(locale)
            }
        )
    },
) {
    val focusManager = LocalFocusManager.current
    val uiState by viewModel.uiStates.collectAsStateWithLifecycle(viewModel.currentUiState)
    val textFieldState = rememberTextFieldState()
    val currentAddress by remember {
        derivedStateOf {
            uiState.currentAddress
        }
    }
    val addressSuggestions by remember {
        derivedStateOf {
            uiState.addressSuggestions
        }
    }
    val isListeningToLocationUpdates by remember {
        derivedStateOf {
            uiState.isListeningToLocationUpdates
        }
    }
    val (allowExpanded, setExpanded) = remember {
        mutableStateOf(false)
    }
    val expanded =
        allowExpanded && textFieldState.text.isNotEmpty() && addressSuggestions.isNotEmpty()
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = setExpanded,
        modifier = modifier,
    ) {
        LocationInputText(
            textFieldState = textFieldState,
            currentAddressDescriptionProvider = {
                currentAddress
            },
            isListeningToLocationUpdatesProvider = {
                isListeningToLocationUpdates
            },
            onLocationRequestedCallback = {
                viewModel.accept(LocationIntent.RequestLocationUpdates(it))
            },
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { setExpanded(false) },
            shape = RoundedCornerShape(16.dp),
        ) {
            addressSuggestions.forEach { address ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = address.formattedDescription,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    onClick = {
                        viewModel.accept(LocationIntent.UpdateSelectedLocation(address.geoLocation))
                        focusManager.clearFocus(force = true)
                        textFieldState.clearText()
                        setExpanded(false)
                    },
                )
            }
        }
    }
    LaunchedEffect(textFieldState) {
        snapshotFlow {
            textFieldState.text
        }
            .collect {
                setExpanded(it.isNotEmpty())
                viewModel.accept(
                    LocationIntent.RequestAddressSuggestions(it.toString())
                )
            }
    }
}

@Composable
private fun ExposedDropdownMenuBoxScope.LocationInputText(
    textFieldState: TextFieldState,
    currentAddressDescriptionProvider: () -> String,
    isListeningToLocationUpdatesProvider: () -> Boolean,
    onLocationRequestedCallback: (Boolean) -> Unit,
) {
    val textStyle = MaterialTheme.typography.headlineMedium
    OutlinedTextField(
        state = textFieldState,
        textStyle = textStyle,
        lineLimits = TextFieldLineLimits.SingleLine,
        shape = RoundedCornerShape(16.dp),
        placeholder = {
            Text(
                text = currentAddressDescriptionProvider(),
                style = textStyle,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        },
        leadingIcon = {
            RequestCurrentLocationIcon(
                locationToggleProvider = isListeningToLocationUpdatesProvider,
                onLocationRequestedCallback = onLocationRequestedCallback,
                modifier = Modifier
                    .padding(start = 6.dp),
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable),
    )
}
