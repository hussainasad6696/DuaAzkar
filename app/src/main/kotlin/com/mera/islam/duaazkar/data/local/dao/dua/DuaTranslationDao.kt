package  com.mera.islam.duaazkar.data.local.dao.dua

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import  com.mera.islam.duaazkar.data.local.entities.dua.relationalEntities.DuaWithTranslationRelationalEntity

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
    @Transaction
    @Query("""
        SELECT * FROM dua_translation
        JOIN dua ON dua_translation.dua_id = dua.id
        WHERE translator_id = :languageId AND dua_type = :type ORDER BY RANDOM() LIMIT 1
    """)
    fun getRandomDuaWithTranslationAndDuaType(
        languageId: Int,
        type: Int
    ): DuaWithTranslationRelationalEntity
}

