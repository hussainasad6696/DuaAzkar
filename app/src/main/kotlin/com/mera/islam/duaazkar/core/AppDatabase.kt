package  com.mera.islam.duaazkar.core

import androidx.room.Database
import androidx.room.RoomDatabase
import  com.mera.islam.duaazkar.data.local.entities.DuaEntity
import  com.mera.islam.duaazkar.data.local.dao.DuaDao
import  com.mera.islam.duaazkar.data.local.dao.DuaTranslationDao
import  com.mera.islam.duaazkar.data.local.dao.DuaTranslatorDao
import  com.mera.islam.duaazkar.data.local.entities.DuaAudioEntity
import  com.mera.islam.duaazkar.data.local.entities.DuaTranslationEntity
import  com.mera.islam.duaazkar.data.local.entities.DuaTranslatorEntity

@Database(
    entities = [
        DuaEntity::class,
        DuaTranslatorEntity::class,
        DuaAudioEntity::class,
        DuaTranslationEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun duaDao(): DuaDao
    abstract fun duaTranslationDao(): DuaTranslationDao
    abstract fun duaTranslatorDao(): DuaTranslatorDao
}