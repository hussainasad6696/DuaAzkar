package com.mera.islam.duaazkar.data.repo

import com.mera.islam.duaazkar.core.EntityModelMapper
import com.mera.islam.duaazkar.core.enums.TasbihType
import com.mera.islam.duaazkar.data.local.dao.TasbihDao
import com.mera.islam.duaazkar.data.local.entities.TasbihEntity
import com.mera.islam.duaazkar.domain.models.TasbihModel
import com.mera.islam.duaazkar.domain.repo.TasbihRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TasbihRepoImpl(
    private val tasbihDao: TasbihDao,
    private val tasbihEntityToModelMapper: EntityModelMapper<TasbihEntity, TasbihModel>
) : TasbihRepo {
    override suspend fun getTasbihDetailByTasbihTypeAndKey(
        tasbihType: TasbihType,
        key: Int
    ): TasbihModel? =
        tasbihDao.getTasbihDetailByTasbihTypeAndKey(tasbihType, key)
            ?.let { tasbihEntityToModelMapper.entityToModelMapper(it) }
    override fun getTasbihCountByTasbihId(
        id: Int
    ): Flow<Int?> = tasbihDao.getTasbihCountByTasbihId(id)

    override fun getTasbihTotalCountByTasbihId(id: Int): Flow<Int?> = tasbihDao.getTasbihTotalCountByTasbihId(id)
    override suspend fun insertOrUpdate(tasbihModel: TasbihModel, update: Boolean) {
        if (update) tasbihDao.updateTasbihCount(
            count = tasbihModel.count,
            id = tasbihModel.id
        )
        else tasbihDao.insert(tasbihEntityToModelMapper.modelToEntityMapper(tasbihModel))
    }

    override suspend fun updateCount(id: Int, count: Int) {
        tasbihDao.updateTasbihCount(count = count, id = id)
    }

    override suspend fun updateTotalCount(id: Int, count: Int) {
        tasbihDao.updateTotalCount(count = count, id = id)
    }
    override suspend fun insert(tasbihModel: TasbihModel) = tasbihDao.insert(tasbihEntityToModelMapper.modelToEntityMapper(tasbihModel)).toInt()
}