package com.mera.islam.duaazkar.domain.repo

import com.mera.islam.duaazkar.core.enums.TasbihType
import com.mera.islam.duaazkar.domain.models.TasbihModel
import kotlinx.coroutines.flow.Flow

interface TasbihRepo {
    suspend fun getTasbihDetailByTasbihTypeAndKey(tasbihType: TasbihType, key: Int): TasbihModel?
    fun getTasbihCountByTasbihId(id: Int): Flow<Int?>
    fun getTasbihTotalCountByTasbihId(id: Int): Flow<Int?>
    suspend fun insertOrUpdate(tasbihModel: TasbihModel,update: Boolean = false)
    suspend fun insert(tasbihModel: TasbihModel): Int
    suspend fun updateCount(id: Int, count: Int)
    suspend fun updateTotalCount(id: Int, count: Int)
}