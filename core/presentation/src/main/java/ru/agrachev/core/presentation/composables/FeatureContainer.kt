package ru.agrachev.core.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.util.fastFirstOrNull
import ru.agrachev.core.presentation.LocalFeatures
import ru.agrachev.core.presentation.feature.FeatureId

@Composable
fun FeatureContainer(
    featureId: FeatureId,
    modifier: Modifier = Modifier,
) {
    LocalFeatures.current.fastFirstOrNull {
        it.definition.id == featureId
    }?.let {
        Box(
            modifier = modifier,
        ) {
            it.uiContent()
        }
    }
}
