// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()  // Google's Maven repository
        mavenCentral()  // Maven Central repository
    }

    dependencies {
        // Add the classpath for Google Services Gradle Plugin
        classpath(libs.google.services)  // This is the version for the plugin
        classpath(libs.gradle)  // Gradle plugin dependency (assuming 'libs.gradle' is defined elsewhere)
        classpath(libs.kotlin.gradle.plugin)  // Kotlin plugin dependency
    }
}

plugins {
    // Plugins declaration
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.google.gms.google.services) apply false
}

allprojects {
    repositories {
        google()  // Google's Maven repository
        mavenCentral()  // Maven Central repository
        maven { url = uri("https://jitpack.io") }  // JitPack repository for additional dependencies
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)  // Task to clean the build directory
}

