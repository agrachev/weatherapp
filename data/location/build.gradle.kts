plugins {
    alias(libs.plugins.conventions.android.library)
    alias(libs.plugins.conventions.koin)
}

android {
    namespace = "ru.agrachev.data.location"
}

dependencies {
    implementation(project(":domain"))
    implementation(libs.play.services.location)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
