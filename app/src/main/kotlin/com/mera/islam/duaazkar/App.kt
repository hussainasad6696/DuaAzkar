package com.mera.islam.duaazkar

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.GeneratesRootInput
import dagger.hilt.android.HiltAndroidApp
import java.lang.annotation.ElementType
import java.lang.annotation.RetentionPolicy
import javax.inject.Inject

@HiltAndroidApp
class App: Application(),Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

}