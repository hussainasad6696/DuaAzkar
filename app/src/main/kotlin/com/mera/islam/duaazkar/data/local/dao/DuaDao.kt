package  com.mera.islam.duaazkar.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import  com.mera.islam.duaazkar.data.local.entities.DuaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DuaDao {
    @Query("SELECT * FROM dua")
    fun getAllDuas(): Flow<List<DuaEntity>>
    @Query("UPDATE dua SET is_fav = :bookmark WHERE id = :id")
    suspend fun isBookmarked(bookmark: Boolean,id: Int)
    @Query("SELECT * FROM dua WHERE is_fav = 1")
    fun getBookmarkedDuas(): Flow<List<DuaEntity>>
    @Query("SELECT * FROM dua WHERE id = :id")
    fun getDuaById(id: Int): Flow<DuaEntity>
    @Query("SELECT * FROM dua WHERE dua_type = :duaType")
    fun getDuaByDuaType(duaType: Int): Flow<List<DuaEntity>>
}