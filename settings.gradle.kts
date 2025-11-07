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
include(":network")
project(":network").projectDir = file("data/network")
include(":persistence")
project(":persistence").projectDir = file("data/persistence")
include(":location")
project(":location").projectDir = file("data/location")
include(":domain")
include(":presentation")
