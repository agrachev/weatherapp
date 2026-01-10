package ru.agrachev.feature.location

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import org.koin.core.module.Module
import ru.agrachev.core.presentation.feature.Feature
import ru.agrachev.core.presentation.feature.UiContent
import ru.agrachev.feature.location.composables.AddressSuggestionsDropdownMenu
import ru.agrachev.feature.location.definition.LocationFeatureDefinition
import ru.agrachev.feature.location.di.locationFeatureModule

internal class LocationFeature : Feature<Module> {

    override val definition = LocationFeatureDefinition

    override val diModule = locationFeatureModule

    override val uiContent: UiContent = {
        AddressSuggestionsDropdownMenu(
            modifier = Modifier
                .fillMaxWidth(),
        )
    }
}
