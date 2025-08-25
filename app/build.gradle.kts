plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("kotlinx-serialization")
    id("org.jetbrains.kotlin.plugin.compose")

}

android {
    namespace = "com.mera.islam.duaazkar"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.mera.islam.duaazkar"
        minSdk = 24
        targetSdk = 36
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
        kotlinCompilerExtensionVersion = "1.5.15" // match Compose version
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

    implementation("androidx.core:core-ktx:1.17.0")
    implementation("com.google.firebase:firebase-crashlytics:20.0.0")
    implementation("com.google.firebase:firebase-analytics:23.0.0")
    implementation("com.google.android.gms:play-services-location:21.3.0")
    implementation("androidx.compose.material3:material3-window-size-class:1.3.2")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.1")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.5")

    //lifecycle aware components
    val lifecycleVersion = "2.9.2"
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycleVersion")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.9.2")
    implementation("androidx.activity:activity-compose:1.10.1")
    implementation(platform("androidx.compose:compose-bom:2025.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.3.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")
    androidTestImplementation(platform("androidx.compose:compose-bom:2025.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("androidx.compose.material:material")

    //jetpack compose animations
    implementation("androidx.compose.animation:animation:1.9.0")

    //hilt
    val hiltVersion = "2.57.1"
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    ksp("com.google.dagger:hilt-android-compiler:$hiltVersion")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    //room
    val roomVersion = "2.7.2"
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    //dataStore
    val dataStoreVersion = "1.1.7"
    implementation("androidx.datastore:datastore-core:$dataStoreVersion")
    implementation("androidx.datastore:datastore-preferences:$dataStoreVersion")

    //navController
    val navVersion = "2.9.3"
    implementation("androidx.navigation:navigation-compose:$navVersion")

    //sdp
    implementation("com.github.Kaaveh:sdp-compose:1.1.0")

    //threetenbp
    implementation("org.threeten:threetenbp:1.7.2")

    //okayHttp3
    implementation("com.squareup.okhttp3:okhttp:5.1.0")

    //worker
    val workVersion = "2.10.3"
    implementation("androidx.work:work-runtime-ktx:$workVersion")
    implementation("androidx.hilt:hilt-work:1.2.0")
    ksp ("androidx.hilt:hilt-compiler:1.2.0")

    //adhan
    implementation(project(":adhan"))

    //sliders
    implementation(project(":slider"))

    //coil
    implementation("io.coil-kt:coil-compose:2.7.0")

    //palette
    implementation("androidx.palette:palette-ktx:1.0.0")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
}