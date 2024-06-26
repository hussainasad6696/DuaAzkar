package  com.mera.islam.duaazkar.di

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import  com.mera.islam.duaazkar.R
import  com.mera.islam.duaazkar.core.AppDatabase
import  com.mera.islam.duaazkar.core.DAILY_DUA_NOTIFICATION_CHANNEL
import  com.mera.islam.duaazkar.core.DAILY_DUA_NOTIFICATION_CHANNEL_ID
import  com.mera.islam.duaazkar.core.EntityModelMapper
import  com.mera.islam.duaazkar.data.local.entities.dua.DuaTranslationEntity
import  com.mera.islam.duaazkar.data.local.entities.dua.relationalEntities.DuaWithTranslationRelationalEntity
import  com.mera.islam.duaazkar.data.repo.dua.DuaTranslationRepoImpl
import  com.mera.islam.duaazkar.domain.models.dua.DuaTranslationModel
import  com.mera.islam.duaazkar.domain.models.dua.relationalModels.DuaWithTranslationRelationalModel
import  com.mera.islam.duaazkar.domain.repo.dua.DuaTranslationRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DuaDbSingletoneModule {
    @Provides
    fun duaWithTranslationRepo(
        appDatabase: AppDatabase,
        duaTranslationEntityToModelMapper: EntityModelMapper<DuaTranslationEntity, DuaTranslationModel>,
        duaWithTranslationRelationalEntityToModelMapper: EntityModelMapper<DuaWithTranslationRelationalEntity, DuaWithTranslationRelationalModel>
    ): DuaTranslationRepo = DuaTranslationRepoImpl(
        duaTranslationDao = appDatabase.duaTranslationDao(),
        duaTranslationEntityToModelMapper = duaTranslationEntityToModelMapper,
        duaWithTranslationRelationalEntityToModelMapper = duaWithTranslationRelationalEntityToModelMapper
    )

    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    fun duaNotificationChannel(
        @ApplicationContext context: Context
    ): NotificationChannel = NotificationChannel(
        DAILY_DUA_NOTIFICATION_CHANNEL_ID,
        DAILY_DUA_NOTIFICATION_CHANNEL,
        NotificationManager.IMPORTANCE_HIGH
    ).apply {
        description = context.getString(R.string.channel_description)
    }
}