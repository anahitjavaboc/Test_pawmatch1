pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }  // For CardStackView
        maven { url = uri("https://perimeterx.jfrog.io/artifactory/px-Android-SDK/") }  // For PerimeterX SDK
    }
}

rootProject.name = "PawMatch"
include(":app")