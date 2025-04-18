buildscript {
    repositories {
        google()        // Google's Maven repository
        mavenCentral()  // Maven Central repository
    }
    dependencies {
        // Use explicit versions if not using a version catalog, or ensure these are defined in gradle/libs.versions.toml
        classpath(libs.google.services)          // Google Services Gradle Plugin
        classpath(libs.gradle)                   // Android Gradle Plugin
        classpath(libs.kotlin.gradle.plugin)     // Kotlin Gradle Plugin
    }
}

plugins {
    // Plugins declaration
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.google.gms.google.services) apply false
    id("com.android.application") version "8.7.3" apply false
    id("org.jetbrains.kotlin.android") version "2.1.20" apply false
    //alias(libs.plugins.kotlin.android) apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://perimeterx.jfrog.io/artifactory/px-Android-SDK/") }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)  // Task to clean the build directory
}

//For anyone else having the same issue, it has changed source:
//
//com.yuyakaido.android:card-stack-view -> com.github.yuyakaido:cardstackview.
//
//Finally, make sure to add the following under project's build.gradle:
//
//allprojects {
//    repositories {
//        maven { url 'https://perimeterx.jfrog.io/artifactory/px-Android-SDK/' }
//    }
//}







