package ru.agrachev.core.presentation

import android.content.res.Configuration
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.flow.SharingStarted
import ru.agrachev.core.navigation.Destination
import ru.agrachev.core.presentation.feature.Feature
import java.util.Locale

@Suppress("FunctionName")
fun SharingStarted.Companion.WhileSubscribedWithDelay() =
    SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS)

private const val STOP_TIMEOUT_MILLIS = 5_000L

typealias Features = List<Feature<*>>
typealias FeatureDefinition = Destination

val LocalFeatures = staticCompositionLocalOf<Features> {
    emptyList()
}

inline val ProvidableCompositionLocal<Configuration>.currentLocale: Locale
    @Composable
    @ReadOnlyComposable
    get() = with(this.current) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) locales[0] else locale
    }
