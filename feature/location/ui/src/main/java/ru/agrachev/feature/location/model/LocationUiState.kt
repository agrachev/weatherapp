package ru.agrachev.feature.location.model

import androidx.compose.runtime.Immutable

@Immutable
internal data class LocationUiState(
    val currentAddress: String = "",
    val addressSuggestions: List<AddressUiModel> = emptyList(),
    val isListeningToLocationUpdates: Boolean = false,
)
