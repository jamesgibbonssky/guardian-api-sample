plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    kotlin("plugin.serialization") version "2.0.21"
}

android {
    namespace = "com.gibconsulting.guardianapisample"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":core"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.kotlinx.serialization)
    implementation(libs.timber)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.moshi)
    implementation(libs.okhttp.logging)
    implementation(libs.moshi)
    implementation(libs.moshi.adapters)
    implementation(libs.moshi.kotlin)
    implementation(libs.androidx.datastore)
    implementation(libs.androidx.datastore.core)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}