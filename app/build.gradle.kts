plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)  // Apply Google Services plugin
    //alias(libs.plugins.kotlin.android)  // Apply Kotlin plugin
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
//
//    kotlinOptions {
//        jvmTarget = "11"  // Match Java version
//    }
//}

    dependencies {
        // Firebase dependencies
        implementation(libs.firebase.auth)  // 23.2.0
        implementation(libs.firebase.firestore)  // 25.1.3
        implementation(libs.firebase.storage)  // 21.0.1

        // Glide for image loading
        implementation(libs.glide)  // 4.16.0

        // CircleImageView for profile pictures
        implementation(libs.circleimageview)  // 3.1.0

        // CardStackView for swiping functionality
        implementation(libs.cardstackview)  // com.github.yuyakaido:CardStackView:2.3.4
        implementation(libs.cardview)  // 1.0.0

        // Material Components for UI elements
        implementation(libs.material)  // 1.12.0

        // AppCompat for backwards compatibility
        implementation(libs.appcompat)  // 1.7.0

        // Activity library for Activity-related functionality
        implementation(libs.activity.ktx)  // 1.10.1

        // ConstraintLayout for layouts
        implementation(libs.constraintlayout)  // 2.2.1

        // Testing libraries
        testImplementation(libs.junit)  // 4.13.2
        //androidTestImplementation(libs.junit.ext)  // 1.2.1
        androidTestImplementation(libs.espresso.core)  // 3.6.1
    }
}