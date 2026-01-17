@file:Suppress("UnstableApiUsage")

pluginManagement {
    includeBuild("build-logic")
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "dev.detekt") {
                useModule("dev.detekt:detekt-gradle-plugin:${requested.version}")
            }
        }
    }
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven {
            url = uri("https://central.sonatype.com/repository/maven-snapshots/")
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://central.sonatype.com/repository/maven-snapshots/")
        }
    }
}

rootProject.name = "Weather App"

include(":app")
include(":core-domain")
project(":core-domain").projectDir = file("core/domain")
include(":core-data")
project(":core-data").projectDir = file("core/data")
include(":core-presentation")
project(":core-presentation").projectDir = file("core/presentation")
include(":core-navigation")
project(":core-navigation").projectDir = file("core/navigation")
include(":weather-forecast-domain")
project(":weather-forecast-domain").projectDir = file("data/weather_forecast/domain")
include(":weather-forecast-data")
project(":weather-forecast-data").projectDir = file("data/weather_forecast/data")
include(":location-domain")
project(":location-domain").projectDir = file("data/location/domain")
include(":location-data")
project(":location-data").projectDir = file("data/location/data")
include(":geocode-domain")
project(":geocode-domain").projectDir = file("data/geocode/domain")
include(":geocode-data")
project(":geocode-data").projectDir = file("data/geocode/data")
include(":weather-forecast-component")
project(":weather-forecast-component").projectDir = file("component/weather_forecast")
include(":location-component")
project(":location-component").projectDir = file("component/location")
include(":feature-weather-forecast-definition")
project(":feature-weather-forecast-definition").projectDir =
    file("feature/weather_forecast/definition")
include(":feature-weather-forecast")
project(":feature-weather-forecast").projectDir = file("feature/weather_forecast/ui")
include(":feature-location-definition")
project(":feature-location-definition").projectDir =
    file("feature/location/definition")
include(":feature-location")
project(":feature-location").projectDir = file("feature/location/ui")
