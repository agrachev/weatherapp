plugins {
    alias(libs.plugins.conventions.android.library)
    alias(libs.plugins.conventions.compose)
}

android<Lib> {
    namespace = "ru.agrachev.core.presentation"
}

dependencies {
    implementation(project(":core-navigation"))
}
