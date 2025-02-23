plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.dagger.hilt)
    kotlin("kapt")
}

android {
    namespace = "com.example.interrapidisimo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.interrapidisimo"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "100.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.constraintlayout)

    // Fragment
    implementation (libs.androidx.fragment.ktx)
    // Activity
    implementation (libs.androidx.activity.ktx)
    // ViewModel
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    // LiveData
    implementation (libs.androidx.lifecycle.livedata.ktx)

    // Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    //Corrutinas
    implementation (libs.kotlinx.coroutines.android)

    implementation (libs.androidx.core.splashscreen)

    implementation("com.google.dagger:hilt-android:2.51.1")
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")

    implementation ("androidx.room:room-ktx:2.6.1")
    kapt ("androidx.room:room-compiler:2.6.1")

    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}