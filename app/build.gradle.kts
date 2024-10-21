plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.homebrew.gifsygrid"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.homebrew.gifsygrid"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true

    }
}

dependencies {


    val nav_version = "2.7.7"
    val room_version = "2.6.1"
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.3")
    implementation("androidx.navigation:navigation-compose:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$nav_version")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0-RC")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")

    implementation("androidx.recyclerview:recyclerview:1.2.1")

    val paging_version = "3.3.0"

    implementation("androidx.paging:paging-runtime:$paging_version")

    implementation("com.github.bumptech.glide:glide:4.14.2")

    //  implementation("androidx.room:room-runtime:2.4.0")

    //   annotationProcessor("androidx.room:room-compiler:2.6.1")
    //  implementation("androidx.paging:paging-runtime-ktx:3.0.1")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:3.9.0")

    // annotationProcessor("androidx.room:room-compiler:$room_version")


    // To use Kotlin annotation processing tool (kapt)
    implementation("androidx.room:room-ktx:$room_version")
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-paging:$room_version" )
    kapt("androidx.room:room-compiler:$room_version")


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}