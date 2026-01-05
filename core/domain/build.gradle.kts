plugins {
    alias(libs.plugins.conventions.kotlin.library)
    alias(libs.plugins.conventions.koin)
}

dependencies {
    api(libs.kotlinx.coroutines.core)
}
