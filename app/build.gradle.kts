plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    //navigation component
    id("androidx.navigation.safeargs.kotlin")
    //plugins for Serializable annotation using in type converter
    id ("org.jetbrains.kotlin.plugin.serialization") version "1.7.20"
}

android {
    namespace = "com.example.quotes"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.quotes"
        minSdk = 26
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        dataBinding=true
        viewBinding=true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    //recyclerView
    implementation ("androidx.recyclerview:recyclerview:1.3.2")
   //View Model
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    // ViewModel Factory
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.6.2")
    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    //convert retrofit
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    //coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    //Gson
    implementation ("com.google.code.gson:gson:2.10.1")
    //Navigation Components
    val nav_version = "2.7.6"
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")
    //OkHttp3
    // define a BOM and its version
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.12.0"))
    // define any required OkHttp artifacts without version
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")
    //room database
    val room_version="2.6.1"
    implementation ("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-common:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    //for Serializable annotation using in type converter
    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1")

}