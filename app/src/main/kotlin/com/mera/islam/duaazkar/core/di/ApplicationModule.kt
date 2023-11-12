package  com.mera.islam.duaazkar.core.di

import android.content.Context
import androidx.room.Room
import  com.mera.islam.duaazkar.core.AppDatabase
import  com.mera.islam.duaazkar.core.Settings
import  com.mera.islam.duaazkar.core.utils.alarmManager.AlarmScheduler
import  com.mera.islam.duaazkar.core.utils.alarmManager.AlarmSchedulerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.time.Duration
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun getSettings(@ApplicationContext context: Context): Settings {
        return Settings(context)
    }

    @Provides
    @Singleton
    fun appDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "local")
            .createFromAsset("database/local.db")
            .build()
    }

    @Provides
    @Singleton
    fun scheduleAlarm(@ApplicationContext context: Context): AlarmScheduler =
        AlarmSchedulerImpl(context)

    @Provides
    @Singleton
    fun okHttpClient(): OkHttpClient {
        val okHttp = OkHttpClient.Builder()
        okHttp.connectTimeout(8, TimeUnit.SECONDS)
        okHttp.readTimeout(8, TimeUnit.SECONDS)
        return okHttp.build()
    }
}