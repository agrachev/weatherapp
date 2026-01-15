plugins {
    alias(libs.plugins.conventions.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.conventions.koin)
    alias(libs.plugins.ksp)
}

android {
    namespace = "ru.agrachev.weatherapp"

    defaultConfig {
        applicationId = "ru.agrachev.weatherapp"
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":core-domain"))
    implementation(project(":core-data"))
    implementation(project(":core-presentation"))

    implementation(project(":feature-weather-forecast"))
    implementation(project(":feature-location"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.koin.annotations)
    ksp(libs.koin.ksp.compiler)
}
