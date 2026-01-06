plugins {
    alias(libs.plugins.conventions.android.library)
    alias(libs.plugins.conventions.compose)
    alias(libs.plugins.conventions.koin)
}

android {
    namespace = "ru.agrachev.feature.weather.forecast"
}

dependencies {
    implementation(project(":weather-forecast-component"))
    implementation(project(":weather-forecast-domain"))
    implementation(project(":location-domain"))
    implementation(project(":core-presentation"))

    implementation(project(":weather-forecast-data"))
    implementation(project(":location-data"))

    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    implementation(libs.koin.compose)
    implementation(libs.koin.compose.viewwmodel)

    implementation(libs.decompose)
    implementation(libs.decompose.extensions.compose)
}
