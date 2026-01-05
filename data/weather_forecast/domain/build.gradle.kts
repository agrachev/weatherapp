plugins {
    alias(libs.plugins.conventions.kotlin.library)
    alias(libs.plugins.conventions.koin)
}

dependencies {
    implementation(project(":core-domain"))
}
