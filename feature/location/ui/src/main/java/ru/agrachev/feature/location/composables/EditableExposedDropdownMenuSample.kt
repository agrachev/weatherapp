package ru.agrachev.feature.location.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.filter
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.agrachev.core.presentation.currentLocale
import ru.agrachev.feature.location.LocationViewModel
import ru.agrachev.feature.location.core.LocationIntent
import ru.agrachev.feature.location.core.LocationViewModelDefinition

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EditableExposedDropdownMenuSample(
    modifier: Modifier = Modifier,
    viewModel: LocationViewModelDefinition = LocalConfiguration.currentLocale.let { locale ->
        koinViewModel<LocationViewModel>(
            parameters = {
                parametersOf(locale)
            }
        )
    },
) {
    val uiState by viewModel.uiStates.collectAsStateWithLifecycle(viewModel.currentUiState)
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
                    text = uiState.text,
                    style = textStyle,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
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
    LaunchedEffect(textFieldState) {
        snapshotFlow {
            textFieldState.text
        }
            .filter { it.isNotEmpty() }
            .collect {
                viewModel.accept(LocationIntent.RequestAddressList(it.toString()))
            }
    }
}

val options =
    "The text that the user inputs into the text field can be used to filter the options".split(
        " ",
    )
