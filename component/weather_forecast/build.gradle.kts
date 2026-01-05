plugins {
    alias(libs.plugins.conventions.kotlin.library)
}

dependencies {
    implementation(project(":core-domain"))
    implementation(project(":weather-forecast-domain"))
    implementation(project(":location-domain"))
}
