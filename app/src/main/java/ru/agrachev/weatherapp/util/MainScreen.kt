package ru.agrachev.weatherapp.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import ru.agrachev.core.presentation.Features
import ru.agrachev.core.presentation.LocalFeatures
import ru.agrachev.core.presentation.composables.FeatureContainer
import ru.agrachev.core.presentation.theme.WeatherAppTheme
import ru.agrachev.feature.weather.forecast.definition.WeatherForecastFeatureDefinition

@Composable
internal fun MainScreen(
    features: Features,
) {
    WeatherAppTheme {
        CompositionLocalProvider(
            LocalFeatures provides features,
        ) {
            FeatureContainer(
                featureId = WeatherForecastFeatureDefinition.id,
            )
        }
    }
}
