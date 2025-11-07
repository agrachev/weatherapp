plugins {
    alias(libs.plugins.conventions.kotlin.library)
    alias(libs.plugins.conventions.koin)
}

dependencies {
    implementation(project(":domain"))
    implementation(libs.converter.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.retrofit)
}
