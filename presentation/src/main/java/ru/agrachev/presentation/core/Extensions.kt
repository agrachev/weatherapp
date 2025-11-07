package ru.agrachev.presentation.core

import androidx.compose.runtime.staticCompositionLocalOf
import java.time.format.DateTimeFormatter

internal const val DATE_PATTERN = "EEEE, dd MMM"

internal val LocalDateFormatter = staticCompositionLocalOf {
    DateTimeFormatter.ofPattern(DATE_PATTERN)
}

internal fun String.asUrl() = if (this.startsWith("http")) this else "https:$this"
