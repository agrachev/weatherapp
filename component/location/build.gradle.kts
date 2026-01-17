plugins {
    alias(libs.plugins.conventions.android.library)
}

android<Lib> {
    namespace = "ru.agrachev.location.component"
}

dependencies {
    implementation(project(":core-domain"))
    implementation(project(":geocode-domain"))
    implementation(project(":location-domain"))
    implementation(libs.timber)
}
