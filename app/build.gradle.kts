plugins {
    alias(libs.plugins.conventions.android.application)
    alias(libs.plugins.conventions.koin)
}

android {
    namespace = "ru.agrachev.weatherapp"

    defaultConfig {
        applicationId = "ru.agrachev.weatherapp"
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation(project(":core-domain"))
    implementation(project(":core-data"))
    implementation(project(":feature-weather-forecast"))
}
