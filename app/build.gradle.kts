plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("kotlinx-serialization")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.mera.islam.duaazkar"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.mera.islam.duaazkar"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        
        vectorDrawables {
            useSupportLibrary = true
        }

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true"
                )
            }
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

        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        create("benchmark") {
            initWith(buildTypes.getByName("release"))
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            isDebuggable = false
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

composeCompiler {
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    stabilityConfigurationFile = rootProject.layout.projectDirectory.file("stability_config.conf")
}

dependencies {

    implementation("androidx.core:core-ktx:1.15.0")
    implementation("com.google.firebase:firebase-crashlytics:19.4.0")
    implementation("com.google.firebase:firebase-analytics:22.2.0")
    implementation("com.google.android.gms:play-services-location:21.3.0")
    implementation("androidx.compose.material3:material3-window-size-class:1.3.1")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.0")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.4")

    //lifecycle aware components
    val lifecycleVersion = "2.8.4"
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycleVersion")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.activity:activity-compose:1.10.0")
    implementation(platform("androidx.compose:compose-bom:2025.01.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2025.01.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("androidx.compose.material:material")

    //jetpack compose animations
    implementation("androidx.compose.animation:animation:1.7.7")

    //hilt
    val hiltVersion = "2.53"
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    //room
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    //dataStore
    val dataStoreVersion = "1.1.1"
    implementation("androidx.datastore:datastore-core:$dataStoreVersion")
    implementation("androidx.datastore:datastore-preferences:$dataStoreVersion")

    //navController
    val navVersion = "2.8.6"
    implementation("androidx.navigation:navigation-compose:$navVersion")

    //sdp
    implementation("com.github.Kaaveh:sdp-compose:1.1.0")

    //threetenbp
    implementation("org.threeten:threetenbp:1.6.9")

    //okayHttp3
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    //worker
    val workVersion = "2.10.0"
    implementation("androidx.work:work-runtime-ktx:$workVersion")
    implementation("androidx.hilt:hilt-work:1.2.0")
    kapt ("androidx.hilt:hilt-compiler:1.2.0")

    //adhan
    implementation(project(":adhan"))

    //sliders
    implementation(project(":slider"))

    //coil
    implementation("io.coil-kt:coil-compose:2.7.0")

    //palette
    implementation("androidx.palette:palette-ktx:1.0.0")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
}