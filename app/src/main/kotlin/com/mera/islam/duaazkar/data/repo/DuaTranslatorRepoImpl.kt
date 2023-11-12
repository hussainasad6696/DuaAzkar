package  com.mera.islam.duaazkar.data.repo

import  com.mera.islam.duaazkar.core.EntityModelMapper
import  com.mera.islam.duaazkar.data.local.dao.DuaTranslatorDao
import  com.mera.islam.duaazkar.data.local.entities.DuaTranslatorEntity
import  com.mera.islam.duaazkar.domain.models.DuaTranslatorModel
import  com.mera.islam.duaazkar.domain.repo.DuaTranslatorRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DuaTranslatorRepoImpl(
    private val duaTranslatorDao: DuaTranslatorDao,
    private val duaTranslatorMapper: EntityModelMapper<DuaTranslatorEntity, DuaTranslatorModel>
) : DuaTranslatorRepo {
    override fun getAllTranslators(): Flow<List<DuaTranslatorModel>> =
        duaTranslatorDao.getAllTranslators().map { translatorList ->
            translatorList.map { duaTranslatorEntity ->
                duaTranslatorMapper.entityToModelMapper(duaTranslatorEntity)
            }
        }
}