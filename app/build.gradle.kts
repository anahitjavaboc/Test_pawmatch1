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
    implementation("com.google.firebase:firebase-auth:23.2.0")

    // Firebase Firestore dependency
    implementation("com.google.firebase:firebase-firestore:24.3.0")

    // Firebase Storage dependency
    implementation("com.google.firebase:firebase-storage:20.2.0")

    // Glide for image loading
    implementation("com.github.bumptech.glide:glide:4.14.2")

    // CircleImageView for profile pictures
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // CardStackView for swiping functionality
    implementation("com.yuyakaido.android:card-stack-view:2.3.4") // Yuyakaido CardStackView

    // Material Components for UI elements
    implementation("com.google.android.material:material:1.6.0")

    // AppCompat for backwards compatibility
    implementation("androidx.appcompat:appcompat:1.5.1")

    // Activity library for Activity-related functionality
    implementation("androidx.activity:activity-ktx:1.6.1")

    // ConstraintLayout for layouts
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Testing libraries
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}


