package com.mera.islam.duaazkar.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mera.islam.duaazkar.core.enums.TasbihType
import com.mera.islam.duaazkar.data.local.entities.TasbihEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TasbihDao {
    @Query("SELECT * FROM tasbih WHERE tasbih_type = :tasbihType AND dua_or_asma_id = :key LIMIT 1")
    suspend fun getTasbihDetailByTasbihTypeAndKey(tasbihType: TasbihType,key: Int): TasbihEntity?
    @Query("SELECT counted FROM tasbih WHERE id = :id")
    fun getTasbihCountByTasbihId(id: Int): Flow<Int?>
    @Query("SELECT total_count FROM tasbih WHERE id = :id")
    fun getTasbihTotalCountByTasbihId(id: Int): Flow<Int?>
    @Insert
    suspend fun insert(tasbihEntity: TasbihEntity): Long
    @Query("UPDATE tasbih SET counted = :count WHERE id = :id")
    suspend fun updateTasbihCount(count: Int,id: Int)
    @Query("UPDATE tasbih SET total_count = :count WHERE id = :id")
    suspend fun updateTotalCount(count: Int,id: Int)
}