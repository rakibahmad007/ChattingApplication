plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services") // Required for Firebase integration
}

android {
    namespace = "com.example.chattingapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.chattingapplication"
        minSdk = 24
        targetSdk = 34
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
        // Updated for broader compatibility with modern Java features
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // Firebase BOM for version management
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    // Firebase Analytics
    implementation("com.google.firebase:firebase-analytics")
    // Firebase Authentication (if needed, you can add more Firebase dependencies)
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    //Firebase database
    implementation("com.google.firebase:firebase-database")
    //firebase storage
    implementation("com.google.firebase:firebase-storage")

    // Third-party libraries
    implementation("com.intuit.sdp:sdp-android:1.1.0") // Scaled pixels support
    implementation("de.hdodenhof:circleimageview:3.1.0") // Circular ImageView

    // AndroidX and Material Design dependencies
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //Picaso library
    implementation("com.squareup.picasso:picasso:2.8")

}
