package  com.mera.islam.duaazkar.data.repo.dua

import  com.mera.islam.duaazkar.core.EntityModelMapper
import  com.mera.islam.duaazkar.data.local.dao.dua.DuaTranslationDao
import  com.mera.islam.duaazkar.data.local.entities.dua.DuaTranslationEntity
import  com.mera.islam.duaazkar.data.local.entities.dua.relationalEntities.DuaWithTranslationRelationalEntity
import  com.mera.islam.duaazkar.domain.models.dua.DuaTranslationModel
import com.mera.islam.duaazkar.domain.models.dua.DuaType
import  com.mera.islam.duaazkar.domain.models.dua.relationalModels.DuaWithTranslationRelationalModel
import  com.mera.islam.duaazkar.domain.repo.dua.DuaTranslationRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DuaTranslationRepoImpl(
    private val duaTranslationDao: DuaTranslationDao,
    private val duaTranslationEntityToModelMapper: EntityModelMapper<DuaTranslationEntity, DuaTranslationModel>,
    private val duaWithTranslationRelationalEntityToModelMapper: EntityModelMapper<DuaWithTranslationRelationalEntity, DuaWithTranslationRelationalModel>
) : DuaTranslationRepo {
    override suspend fun getDuaTranslationByDuaIds(
        ids: List<Int>,
        translatorIds: List<Int>
    ): Flow<List<DuaWithTranslationRelationalModel>> {
        val chunk = ids.chunked(200)
        return flow {
            emit(chunk.map { duaIds ->
                duaTranslationDao.getDuaTranslationByDuaIds(
                    translatorId = translatorIds,
                    duaIds = duaIds
                )
                    .map { duaWithTranslationsEntity ->
                        duaWithTranslationRelationalEntityToModelMapper.entityToModelMapper(
                            duaWithTranslationsEntity
                        )
                    }
            }.flatten())
        }
    }

    override suspend fun getRandomDuaWithTranslation(languageId: Int): DuaWithTranslationRelationalModel =
        duaWithTranslationRelationalEntityToModelMapper.entityToModelMapper(duaTranslationDao.getRandomDuaWithTranslation(languageId))

    override suspend fun getRandomDuaWithTranslationAndDuaType(
        languageId: Int,
        duaType: DuaType
    ): DuaWithTranslationRelationalModel =
        duaWithTranslationRelationalEntityToModelMapper.entityToModelMapper(duaTranslationDao.getRandomDuaWithTranslationAndDuaType(languageId,duaType.type))
}