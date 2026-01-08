plugins {
    alias(libs.plugins.conventions.android.library)
    alias(libs.plugins.conventions.compose)
}

android {
    namespace = "ru.agrachev.core.presentation"
}

dependencies {
    implementation(project(":core-navigation"))
}
