plugins {
    alias(libs.plugins.conventions.android.library)
    alias(libs.plugins.conventions.koin)
}

android {
    namespace = "ru.agrachev.core.data"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":core-domain"))

    implementation(libs.androidx.datastore)
    implementation(libs.retrofit)
    implementation(libs.moshi.kotlin)
    implementation(libs.converter.moshi)
    implementation(libs.logging.interceptor)
    implementation(libs.protobuf.kotlin.lite)
}
