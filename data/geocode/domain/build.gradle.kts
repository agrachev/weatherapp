plugins {
    alias(libs.plugins.conventions.kotlin.library)
}

dependencies {
    implementation(project(":core-domain"))
}
