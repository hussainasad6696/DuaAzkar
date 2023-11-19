package  com.mera.islam.duaazkar.data.mappers.dua

import  com.mera.islam.duaazkar.core.EntityModelMapper
import  com.mera.islam.duaazkar.data.local.entities.dua.DuaAudioEntity
import  com.mera.islam.duaazkar.domain.models.dua.DuaAudioModel
import javax.inject.Inject

class DuaAudioEntityToModelMapper @Inject constructor(): EntityModelMapper<DuaAudioEntity, DuaAudioModel> {
    override fun entityToModelMapper(entity: DuaAudioEntity): DuaAudioModel {
        return DuaAudioModel(
            duaId = entity.duaId,
            url = entity.url,
            recitationBy = entity.recitationBy,
            reciterImageUrl = entity.reciterImageUrl,
            id = entity.id
        )
    }

    override fun modelToEntityMapper(model: DuaAudioModel): DuaAudioEntity {
        return DuaAudioEntity(
            duaId = model.duaId,
            url = model.url,
            recitationBy = model.recitationBy,
            reciterImageUrl = model.reciterImageUrl
        )
    }
}