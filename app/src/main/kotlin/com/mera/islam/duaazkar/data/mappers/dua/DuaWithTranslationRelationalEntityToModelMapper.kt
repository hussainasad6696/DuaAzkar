package  com.mera.islam.duaazkar.data.mappers.dua

import  com.mera.islam.duaazkar.core.EntityModelMapper
import  com.mera.islam.duaazkar.data.local.entities.dua.DuaEntity
import  com.mera.islam.duaazkar.data.local.entities.dua.DuaTranslationEntity
import  com.mera.islam.duaazkar.data.local.entities.dua.DuaTranslatorEntity
import  com.mera.islam.duaazkar.data.local.entities.dua.relationalEntities.DuaWithTranslationRelationalEntity
import  com.mera.islam.duaazkar.domain.models.dua.DuaModel
import  com.mera.islam.duaazkar.domain.models.dua.DuaTranslationModel
import  com.mera.islam.duaazkar.domain.models.dua.DuaTranslatorModel
import  com.mera.islam.duaazkar.domain.models.dua.relationalModels.DuaWithTranslationRelationalModel
import javax.inject.Inject

class DuaWithTranslationRelationalEntityToModelMapper @Inject constructor(
    private val duaEntityModelMapper: EntityModelMapper<DuaEntity, DuaModel>,
    private val duaTranslationEntityModelMapper: EntityModelMapper<DuaTranslationEntity, DuaTranslationModel>,
    private val duaTranslatorEntityModelMapper: EntityModelMapper<DuaTranslatorEntity, DuaTranslatorModel>
) :
    EntityModelMapper<DuaWithTranslationRelationalEntity, DuaWithTranslationRelationalModel> {
    override fun entityToModelMapper(entity: DuaWithTranslationRelationalEntity): DuaWithTranslationRelationalModel {
        return DuaWithTranslationRelationalModel(
            duaModel = duaEntityModelMapper.entityToModelMapper(entity.duaEntity),
            duaTranslationModel = duaTranslationEntityModelMapper.entityToModelMapper(entity.duaTranslationEntity),
            duaTranslatorModel = duaTranslatorEntityModelMapper.entityToModelMapper(entity.duaTranslatorEntity)
        )
    }

    override fun modelToEntityMapper(model: DuaWithTranslationRelationalModel): DuaWithTranslationRelationalEntity {
        return DuaWithTranslationRelationalEntity(
            duaEntity = duaEntityModelMapper.modelToEntityMapper(model.duaModel),
            duaTranslationEntity = duaTranslationEntityModelMapper.modelToEntityMapper(model.duaTranslationModel),
            duaTranslatorEntity = duaTranslatorEntityModelMapper.modelToEntityMapper(model.duaTranslatorModel)
        )
    }
}