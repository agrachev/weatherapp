plugins {
    alias(libs.plugins.conventions.android.library)
    alias(libs.plugins.conventions.repo.remote)
    alias(libs.plugins.conventions.koin)
}

android<Lib> {
    namespace = "ru.agrachev.data.location"
}

dependencies {
    implementation(project(":core-domain"))
    implementation(project(":location-domain"))
    implementation(project(":weather-forecast-domain"))

    implementation(libs.play.services.location)
}
