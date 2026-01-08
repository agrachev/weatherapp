plugins {
    alias(libs.plugins.conventions.android.library)
    alias(libs.plugins.conventions.compose)
    alias(libs.plugins.conventions.koin)
    alias(libs.plugins.ksp)
}

android {
    namespace = "ru.agrachev.feature.location"
}

dependencies {
    implementation(project(":feature-location-definition"))

    implementation(project(":location-component"))
    implementation(project(":geocode-domain"))
    implementation(project(":location-domain"))
    implementation(project(":core-presentation"))

    implementation(project(":geocode-data"))
    implementation(project(":location-data"))

    implementation(libs.androidx.compose.material.icons.extended)

    implementation(libs.koin.compose)
    implementation(libs.koin.compose.viewwmodel)
    implementation(libs.koin.annotations)
    ksp(libs.koin.ksp.compiler)

    implementation(libs.decompose)
    implementation(libs.decompose.extensions.compose)
}
