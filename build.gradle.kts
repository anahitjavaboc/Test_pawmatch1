buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // Rely on version catalog for plugin versions
        classpath(libs.google.services)
        classpath(libs.gradle)
        classpath(libs.kotlin.gradle.plugin)
    }
}

plugins {
    // Use aliases for consistency with version catalog
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.google.gms.google.services) apply false
    //alias(libs.plugins.kotlin.android) apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        // Uncomment if needed for specific dependencies
        // maven { url = uri("https://perimeterx.jfrog.io/artifactory/px-Android-SDK/") }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}