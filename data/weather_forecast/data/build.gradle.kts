plugins {
    alias(libs.plugins.conventions.kotlin.library)
    alias(libs.plugins.conventions.repo.remote)
    alias(libs.plugins.gmazzo.buildconfig)
    alias(libs.plugins.protobuf.plugin)
    alias(libs.plugins.conventions.koin)
}

buildConfig {
    buildConfigField(
        type = "String",
        name = "WEATHER_API_KEY",
        value = "${System.getenv("WEATHER_API_KEY")}",
    )
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:4.32.1"
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                getByName("java") {
                    option("lite")
                }
                create("kotlin")
            }
        }
    }
}

dependencies {
    implementation(project(":core-domain"))
    implementation(project(":weather-forecast-domain"))

    implementation(libs.androidx.annotation)
    implementation(libs.protobuf.kotlin.lite)
}
