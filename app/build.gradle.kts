val implementation: Unit
    get() {
        TODO()
    }

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
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
    implementation(libs.firebase.auth.v2231)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    implementation (libs.card.stack.view)

    implementation (libs.card.stack.view)


    // Firebase Firestorm
    implementation(libs.firebase.firestore)

    // Firebase Storage
    implementation(libs.firebase.storage)

    // Glide for image loading
    implementation(libs.glide)

    // CircleImageView for profile pictures
    implementation(libs.circleimageview)

    // CardStackView for swiping
    implementation(libs.card.stack.view)
//    implementation 'com.android.support:appcompat-v7:28.0.0'
//    implementation 'com.yuyakaido.android:card-stack-view:2.3.4';




    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)


}
//dependencies {
//    implementation 'com.github.yuyakaido:CardStackView:v2.3.4'
//}
//
