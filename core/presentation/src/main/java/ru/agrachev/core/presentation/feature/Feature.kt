package ru.agrachev.core.presentation.feature

import androidx.compose.runtime.Composable
import ru.agrachev.core.presentation.FeatureDefinition

typealias FeatureId = String
typealias UiContent = @Composable () -> Unit

interface Feature<Module : Any> {

    val definition: FeatureDefinition

    val diModule: Module

    val uiContent: UiContent
}
