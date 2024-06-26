package  com.mera.islam.duaazkar.data.mappers.dua

import  com.mera.islam.duaazkar.core.EntityModelMapper
import  com.mera.islam.duaazkar.data.local.entities.dua.DuaTranslationEntity
import  com.mera.islam.duaazkar.domain.models.dua.DuaTranslationModel
import javax.inject.Inject

class DuaTranslationEntityToModelMapper @Inject constructor(): EntityModelMapper<DuaTranslationEntity, DuaTranslationModel> {
    override fun entityToModelMapper(entity: DuaTranslationEntity): DuaTranslationModel {
        return DuaTranslationModel(
            duaId = entity.duaId,
            translation = entity.translation,
            translatorId = entity.translatorId,
            id = entity.id
        )
    }

    override fun modelToEntityMapper(model: DuaTranslationModel): DuaTranslationEntity {
        return DuaTranslationEntity(
            duaId = model.duaId,
            translation = model.translation,
            translatorId = model.translatorId
        )
    }
}