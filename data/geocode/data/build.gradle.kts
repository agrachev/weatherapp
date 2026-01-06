plugins {
    alias(libs.plugins.conventions.android.library)
    alias(libs.plugins.conventions.koin)
}

android {
    namespace = "ru.agrachev.geocode.data"
}

dependencies {
    implementation(project(":core-domain"))
    implementation(project(":geocode-domain"))
    implementation(project(":weather-forecast-domain"))

    implementation(libs.play.services.location)
}
