buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:8.12.1")
        classpath("com.google.gms:google-services:4.4.3")
        classpath("com.google.firebase:firebase-crashlytics-gradle:3.0.6")
        classpath("org.jetbrains.kotlin:kotlin-serialization:2.2.10")
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {

    id("com.android.application") version "8.12.1" apply false
    id("org.jetbrains.kotlin.android") version "2.2.10" apply false
    id("com.google.dagger.hilt.android") version "2.57.1" apply false
    id("com.google.devtools.ksp") version "2.2.10-2.0.2" apply false
    id("com.android.test") version "8.12.1" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.2.10" apply false
}