package  com.mera.islam.duaazkar.data.mappers

import  com.mera.islam.duaazkar.core.EntityModelMapper
import  com.mera.islam.duaazkar.data.local.entities.DuaEntity
import  com.mera.islam.duaazkar.domain.models.DuaModel
import  com.mera.islam.duaazkar.domain.models.DuaType
import javax.inject.Inject

class DuaEntityToModelMapper @Inject constructor(): EntityModelMapper<DuaEntity,DuaModel> {
    override fun entityToModelMapper(entity: DuaEntity): DuaModel {
        return DuaModel(
            arabic = entity.arabic,
            translitration = entity.translitration,
            reason = entity.reason,
            referenceFrom = entity.referenceFrom,
            referenceType = entity.referenceType,
            duaType = DuaType.toDuaType(entity.duaType),
            isFav = entity.isFav,
            id = entity.id,
            method = entity.method
        )
    }

    override fun modelToEntityMapper(model: DuaModel): DuaEntity {
        return DuaEntity(
            arabic = model.arabic,
            translitration = model.translitration,
            reason = model.reason,
            referenceFrom = model.referenceFrom,
            referenceType = model.referenceType,
            duaType = model.duaType.type,
            isFav = model.isFav,
            method = model.method
        )
    }
}