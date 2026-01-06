@file:Suppress("UnstableApiUsage")

pluginManagement {
    includeBuild("build-logic")
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
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
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
include(":feature-weather-forecast")
project(":feature-weather-forecast").projectDir = file("feature/weather_forecast")
include(":feature-location")
project(":feature-location").projectDir = file("feature/location")

private fun includeProjects(vararg paths: String) {
    paths.forEach { path ->
        val projectName = ":${path.replace('/', '-')}"
        include(projectName)
        project(projectName).projectDir = file(path)
    }
}
