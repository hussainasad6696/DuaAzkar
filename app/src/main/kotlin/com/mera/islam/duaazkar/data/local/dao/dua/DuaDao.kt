package  com.mera.islam.duaazkar.data.local.dao.dua

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Query
import  com.mera.islam.duaazkar.data.local.entities.dua.DuaEntity
import com.mera.islam.duaazkar.domain.models.dua.DuaType
import kotlinx.coroutines.flow.Flow

@Dao
interface DuaDao {
    @Query("SELECT * FROM dua")
    fun getAllDuas(): Flow<List<DuaEntity>>

    @Query("SELECT COUNT(*) AS count, dua_type, GROUP_CONCAT(dua.id, ',') AS dua_ids FROM dua GROUP BY dua_type")
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
        """SELECT 
            COUNT(*) AS count, 
            dua.dua_type, 
            GROUP_CONCAT(dua.id, ',') AS dua_ids
            FROM dua
            JOIN dua_translation ON dua.id = dua_translation.dua_id
            WHERE 
                LOWER(dua.translitration) LIKE '%'||LOWER(:keyword)||'%' OR 
                LOWER(dua.reason) LIKE '%'||LOWER(:keyword)||'%' OR
                LOWER(dua.reference_from) LIKE '%'||LOWER(:keyword)||'%' OR 
                LOWER(dua.method) LIKE '%'||LOWER(:keyword)||'%' OR 
                LOWER(dua_translation.translation) LIKE '%'||LOWER(:keyword)||'%'
                GROUP BY dua.dua_type"""
    )
    fun getAllDuaTypesAndCountsByKeyword(keyword: String): Flow<List<DuaNameAndCount>>

    //    SELECT COUNT(*) AS count, dua.dua_type, dua.id
//            FROM dua
//            JOIN dua_translation ON dua.id = dua_translation.dua_id
//            WHERE LOWER(dua.translitration) LIKE '%'||LOWER(:keyword)||'%' OR LOWER(dua.reason) LIKE '%'||LOWER(:keyword)||'%'
//            OR LOWER(dua.reference_from) LIKE '%'||LOWER(:keyword)||'%' OR LOWER(dua.method) LIKE '%'||LOWER(:keyword)||'%'
//            OR LOWER(dua_translation.translation) LIKE '%'||LOWER(:keyword)||'%'
//            GROUP BY dua.dua_type
    @Query("SELECT * FROM dua WHERE id IN (:ids)")
    suspend fun getDuaByIds(ids: List<Int>): List<DuaEntity>
}

data class DuaNameAndCount(
    @ColumnInfo(name = "dua_type") val duaType: Int,
    @ColumnInfo(name = "count") val count: Int,
    @ColumnInfo(name = "dua_ids") val duaIds: String
) {
    fun getIdList() = duaIds.split(",").mapNotNull { it.toIntOrNull() }
    fun getDuaType() = DuaType.toDuaType(duaType)
}