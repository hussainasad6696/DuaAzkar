package  com.mera.islam.duaazkar.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Transaction
import androidx.sqlite.db.SimpleSQLiteQuery
import  com.mera.islam.duaazkar.data.local.entities.relationalEntities.DuaWithTranslationRelationalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DuaTranslationDao {
    @Transaction
    @Query(
        """SELECT * FROM dua_translation 
            WHERE dua_id IN (:duaIds) AND translator_id IN (:translatorId)
    """
    )
    suspend fun getDuaTranslationByDuaIds(
        translatorId: List<Int>,
        duaIds: List<Int>
    ): List<DuaWithTranslationRelationalEntity>

    @Transaction
    @Query("SELECT * FROM dua_translation WHERE translator_id = :languageId ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomDuaWithTranslation(languageId: Int): DuaWithTranslationRelationalEntity
}

