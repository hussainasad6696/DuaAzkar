buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:8.8.0")
        classpath("com.google.gms:google-services:4.4.2")
        classpath("com.google.firebase:firebase-crashlytics-gradle:3.0.2")
        classpath("org.jetbrains.kotlin:kotlin-serialization:2.0.21")
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.8.0" apply false
    id("org.jetbrains.kotlin.android") version "2.0.21" apply false
    id("com.google.dagger.hilt.android") version "2.53" apply false
    id("com.google.devtools.ksp") version "2.0.21-1.0.28" apply false
    id("com.android.test") version "8.8.0" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21" apply false
}