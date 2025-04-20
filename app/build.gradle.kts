plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
    //alias(libs.plugins.kotlin.android)
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

//    kotlinOptions {
//        jvmTarget = "11"
//    }
}

dependencies {
    // Firebase dependencies (update versions in libs.versions.toml if needed)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)

    // Glide for image loading
    implementation(libs.glide)

    // CircleImageView for profile pictures
    implementation(libs.circleimageview)

    // CardStackView for swiping functionality
    implementation(libs.cardstackview)
    implementation(libs.cardview)

    // Material Components for UI elements
    implementation(libs.material)

    // AppCompat for backwards compatibility
    implementation(libs.appcompat)

    // Activity library for Activity-related functionality
    implementation(libs.activity.ktx)

    // ConstraintLayout for layouts
    implementation(libs.constraintlayout)

    // Testing libraries
    testImplementation(libs.junit)
    //androidTestImplementation(libs.junit.ext)  // Uncommented for Android tests
    androidTestImplementation(libs.espresso.core)
}