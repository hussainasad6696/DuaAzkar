package com.mera.islam.duaazkar.data.local.dao.asmaUlHusna

import androidx.room.Dao
import androidx.room.Query
import com.mera.islam.duaazkar.data.local.entities.asmaUlHusna.AsmaulHusnaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AsmaulHusnaDao {
    @Query("SELECT * FROM asma_ul_husna")
    fun getAllAsmaulHusna(): Flow<List<AsmaulHusnaEntity>>
}