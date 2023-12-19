plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "tn.esprit.formtaion"
    compileSdk = 34

    defaultConfig {
        applicationId = "tn.esprit.formtaion"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String","YOUTUBE_API_KEY", "\"AIzaSyCfuH28ftkGS9mKrXHN8dgUA7YdQjxDN1E\"")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }

}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("com.airbnb.android:lottie:4.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.5")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.6.0")
    implementation("com.squareup.retrofit2:converter-gson:2.6.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.5.0")
    implementation ("com.squareup.picasso:picasso:2.71828")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
    implementation ("androidx.fragment:fragment-ktx:1.6.2")


    // Glide image
    implementation("com.github.bumptech.glide:glide:4.14.2")


    //bechir

    implementation ("com.squareup.okhttp3:okhttp:4.9.0")
    implementation ("com.google.code.gson:gson:2.8.9")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation ("androidx.activity:activity-ktx:1.4.0")
    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")

    //ichrak
//    implementation("com.mapbox.maps:android:10.16.3")
//    implementation ("com.mapbox.mapboxsdk:mapbox-android-sdk:10.0.0")



    implementation ("com.google.mlkit:barcode-scanning:17.2.0")
  implementation("com.google.mlkit:barcode-scanning-common:17.0.0")
    implementation("com.google.android.gms:play-services-vision:20.1.3")


}