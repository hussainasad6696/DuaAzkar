package  com.mera.islam.duaazkar.data.repo.dua

import  com.mera.islam.duaazkar.core.EntityModelMapper
import  com.mera.islam.duaazkar.data.local.dao.dua.DuaTranslatorDao
import  com.mera.islam.duaazkar.data.local.entities.dua.DuaTranslatorEntity
import  com.mera.islam.duaazkar.domain.models.dua.DuaTranslatorModel
import  com.mera.islam.duaazkar.domain.repo.dua.DuaTranslatorRepo
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