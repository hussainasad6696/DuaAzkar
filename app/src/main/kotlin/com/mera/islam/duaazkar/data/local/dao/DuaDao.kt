package  com.mera.islam.duaazkar.data.local.dao

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Query
import  com.mera.islam.duaazkar.data.local.entities.DuaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DuaDao {
    @Query("SELECT * FROM dua")
    fun getAllDuas(): Flow<List<DuaEntity>>

    @Query("SELECT COUNT(*) AS count, dua_type FROM dua GROUP BY dua_type")
    fun getAllDuaTypesAndCounts(): Flow<List<DuaNameAndCount>>

    @Query("UPDATE dua SET is_fav = :bookmark WHERE id = :id")
    suspend fun isBookmarked(bookmark: Boolean, id: Int)

    @Query("SELECT * FROM dua WHERE is_fav = 1")
    fun getBookmarkedDuas(): Flow<List<DuaEntity>>

    @Query("SELECT * FROM dua WHERE id = :id")
    fun getDuaById(id: Int): Flow<DuaEntity>

    @Query("SELECT * FROM dua WHERE dua_type = :duaType")
    fun getDuaByDuaType(duaType: Int): Flow<List<DuaEntity>>

    @Query(
        """SELECT COUNT(*) AS count, dua.dua_type
            FROM dua
            JOIN dua_translation ON dua.id = dua_translation.dua_id
            WHERE dua.translitration LIKE :keyword OR dua.reason LIKE :keyword
            OR dua.reference_from LIKE :keyword OR dua.method LIKE :keyword
            OR dua_translation.translation LIKE :keyword
            GROUP BY dua.dua_type"""
    )
    fun getAllDuaTypesAndCountsByKeyword(keyword: String): Flow<List<DuaNameAndCount>>
}

data class DuaNameAndCount(
    @ColumnInfo(name = "dua_type") val duaType: Int,
    @ColumnInfo(name = "count") val count: Int
)