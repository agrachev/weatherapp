import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.conventions.kotlin.library)
    alias(libs.plugins.conventions.koin)
    id("com.github.gmazzo.buildconfig") version "6.0.6"
}

buildConfig {
    buildConfigField(
        type = "String",
        name = "WEATHER_API_KEY",
        expression = "\"${System.getenv("WEATHER_API_KEY")}\"",
    )
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.retrofit)
    implementation(libs.converter.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.logging.interceptor)
    implementation(libs.androidx.annotation)
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.compilerOptions {
    freeCompilerArgs.set(listOf("-Xannotation-default-target=param-property"))
}
