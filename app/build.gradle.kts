plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id(libs.plugins.safeArgs.get().pluginId)
    alias(libs.plugins.kapt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "com.example.challenge"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.challenge"
        minSdk = 24
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
            buildConfigField("String", "BASE_URL", "\"https://run.mocky.io/v3/\"")
        }
        debug {
            buildConfigField("String", "BASE_URL", "\"https://run.mocky.io/v3/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.converter.moshi)
    implementation(libs.converter.moshi)
    implementation(libs.retrofit)
    implementation(libs.logging.interceptor)
    implementation(libs.moshi)
    implementation(libs.okhttp)
    ksp(libs.moshi.kotlin.codegen)
    implementation(libs.moshi.kotlin)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.navigation.ui)
    implementation(libs.navigation.fragment)
    implementation(libs.dagger.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.glide)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

}