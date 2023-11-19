package com.mera.islam.duaazkar.data.mappers.asmaUlHusna

import com.mera.islam.duaazkar.core.EntityModelMapper
import com.mera.islam.duaazkar.data.local.entities.asmaUlHusna.AsmaulHusnaEntity
import com.mera.islam.duaazkar.domain.models.asmaUlHusna.AsmaulHusnaModel
import javax.inject.Inject

class AsmaulHusnaEntityToModelMapper @Inject constructor() :
    EntityModelMapper<AsmaulHusnaEntity, AsmaulHusnaModel> {
    override fun entityToModelMapper(entity: AsmaulHusnaEntity): AsmaulHusnaModel =
        AsmaulHusnaModel(
            id = entity.id,
            name = entity.name,
            transliteration = entity.transliteration,
            found = entity.found,
            enMeaning = entity.enMeaning,
            enDesc = entity.enDesc
        )

    override fun modelToEntityMapper(model: AsmaulHusnaModel): AsmaulHusnaEntity =
        AsmaulHusnaEntity(
            name = model.name,
            transliteration = model.transliteration,
            found = model.found,
            enMeaning = model.enMeaning,
            enDesc = model.enDesc
        )
}