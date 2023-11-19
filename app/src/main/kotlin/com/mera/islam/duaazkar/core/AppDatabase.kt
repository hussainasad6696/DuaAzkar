package  com.mera.islam.duaazkar.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mera.islam.duaazkar.data.local.dao.asmaUlHusna.AsmaulHusnaDao
import  com.mera.islam.duaazkar.data.local.entities.dua.DuaEntity
import  com.mera.islam.duaazkar.data.local.dao.dua.DuaDao
import  com.mera.islam.duaazkar.data.local.dao.dua.DuaTranslationDao
import  com.mera.islam.duaazkar.data.local.dao.dua.DuaTranslatorDao
import com.mera.islam.duaazkar.data.local.entities.asmaUlHusna.AsmaulHusnaEntity
import  com.mera.islam.duaazkar.data.local.entities.dua.DuaAudioEntity
import  com.mera.islam.duaazkar.data.local.entities.dua.DuaTranslationEntity
import  com.mera.islam.duaazkar.data.local.entities.dua.DuaTranslatorEntity

@Database(
    entities = [
        AsmaulHusnaEntity::class,
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
    abstract fun asmaulHusnaDao(): AsmaulHusnaDao
}