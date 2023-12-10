package com.mera.islam.duaazkar.data.mappers

import com.mera.islam.duaazkar.core.EntityModelMapper
import com.mera.islam.duaazkar.core.enums.TasbihType
import com.mera.islam.duaazkar.data.local.entities.TasbihEntity
import com.mera.islam.duaazkar.domain.models.TasbihModel
import javax.inject.Inject

class TasbihEntityToModelMapper @Inject constructor(): EntityModelMapper<TasbihEntity,TasbihModel> {
    override fun entityToModelMapper(entity: TasbihEntity): TasbihModel = TasbihModel(
        count = entity.count,
        totalCount = entity.totalCount,
        key = entity.key,
        tasbihType = entity.tasbihType,
        id = entity.id
    )

    override fun modelToEntityMapper(model: TasbihModel): TasbihEntity = TasbihEntity(
        count = model.count,
        totalCount = model.totalCount,
        key = model.key,
        tasbihType = model.tasbihType
    )
}