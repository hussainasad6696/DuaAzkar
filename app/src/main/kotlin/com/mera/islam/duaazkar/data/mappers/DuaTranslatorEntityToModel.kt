package  com.mera.islam.duaazkar.data.mappers

import  com.mera.islam.duaazkar.core.EntityModelMapper
import  com.mera.islam.duaazkar.core.enums.toLanguageDirection
import  com.mera.islam.duaazkar.data.local.entities.DuaTranslatorEntity
import  com.mera.islam.duaazkar.domain.models.DuaTranslatorModel
import javax.inject.Inject

class DuaTranslatorEntityToModel @Inject constructor(): EntityModelMapper<DuaTranslatorEntity,DuaTranslatorModel> {
    override fun entityToModelMapper(entity: DuaTranslatorEntity): DuaTranslatorModel {
        return DuaTranslatorModel(
            languageCode = entity.languageCode,
            languageName = entity.languageName,
            languageDirection = entity.languageDirection.toLanguageDirection(),
            translatorName = entity.translatorName,
            translatorImageUrl = entity.translatorImageUrl,
            id = entity.id
        )
    }

    override fun modelToEntityMapper(model: DuaTranslatorModel): DuaTranslatorEntity {
        return DuaTranslatorEntity(
            languageCode = model.languageCode,
            languageName = model.languageName,
            languageDirection = model.languageDirection.direction,
            translatorName = model.translatorName,
            translatorImageUrl = model.translatorImageUrl
        )
    }
}