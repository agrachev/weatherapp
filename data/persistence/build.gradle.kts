plugins {
    alias(libs.plugins.conventions.android.library)
    alias(libs.plugins.conventions.koin)
}

android {
    namespace = "ru.agrachev.data.persistence"
}

dependencies {
    implementation(project(":domain"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
