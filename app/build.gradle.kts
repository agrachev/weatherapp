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
    implementation(project(":domain"))
    implementation(project(":presentation"))
    implementation(project(":location"))
    implementation(project(":persistence"))
    implementation(project(":network"))
}
