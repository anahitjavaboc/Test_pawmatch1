plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services) // Apply Google Services plugin
}

android {
    namespace = "com.example.test_pawmatch"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.test_pawmatch"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // Firebase Authentication dependency (latest stable version)
    implementation(libs.firebase.auth)

    // Firebase Firestore dependency
    implementation(libs.firebase.firestore.v2430)

    // Firebase Storage dependency
    implementation(libs.firebase.storage.v2020)

    // Glide for image loading
    implementation(libs.glide.v4142)

    // CircleImageView for profile pictures
    implementation(libs.circleimageview)

    // CardStackView for swiping functionality
    implementation(libs.card.stack.view) // Yuyakaido CardStackView

    // Material Components for UI elements
    implementation(libs.material.v160)

    // AppCompat for backwards compatibility
    implementation(libs.appcompat.v151)

    // Activity library for Activity-related functionality
    implementation(libs.activity.ktx)

    // ConstraintLayout for layouts
    implementation(libs.constraintlayout.v214)

    // Testing libraries
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.v113)
    androidTestImplementation(libs.espresso.core.v340)
    implementation(libs.cardstack)
    implementation (libs.cardstackview)
    implementation (libs.cardview)
    implementation (libs.material)
    implementation (libs.constraintlayout.v221)
    implementation (libs.glide)
}


