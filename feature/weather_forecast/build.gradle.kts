plugins {
    alias(libs.plugins.conventions.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.conventions.koin)
}

android {
    namespace = "ru.agrachev.feature.weather.forecast"

    buildFeatures {
        compose = true
    }
}

dependencies {
    //implementation(project(":core-domain"))
    implementation(project(":weather-forecast-component"))
    implementation(project(":weather-forecast-domain"))
    implementation(project(":location-domain"))

    implementation(project(":weather-forecast-data"))
    implementation(project(":location-data"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.material.icons.extended)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    implementation(libs.decompose)
    implementation(libs.decompose.extensions.compose)
}
