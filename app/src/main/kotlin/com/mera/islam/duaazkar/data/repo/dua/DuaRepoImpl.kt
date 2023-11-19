package  com.mera.islam.duaazkar.data.repo.dua

import  com.mera.islam.duaazkar.core.EntityModelMapper
import  com.mera.islam.duaazkar.data.local.dao.dua.DuaDao
import com.mera.islam.duaazkar.data.local.dao.dua.DuaNameAndCount
import  com.mera.islam.duaazkar.data.local.entities.dua.DuaEntity
import  com.mera.islam.duaazkar.domain.models.dua.DuaModel
import  com.mera.islam.duaazkar.domain.models.dua.DuaType
import  com.mera.islam.duaazkar.domain.repo.dua.DuaRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class DuaRepoImpl(
    private val duaDao: DuaDao,
    private val duaEntityToModelMapper: EntityModelMapper<DuaEntity, DuaModel>
) : DuaRepo {
    override fun getAllDuas(): Flow<List<DuaModel>> = duaDao.getAllDuas().mapper()
    override fun getAllDuaTypesAndCounts(): Flow<List<DuaNameAndCount>> = duaDao.getAllDuaTypesAndCounts()
    override fun getAllDuaTypesAndCountsByKeyword(keyword: String): Flow<List<DuaNameAndCount>> =
        duaDao.getAllDuaTypesAndCountsByKeyword(keyword)

    override fun getDuaByDuaType(duaType: DuaType): Flow<List<DuaModel>> =
        duaDao.getDuaByDuaType(duaType.type).mapper()

    override fun getDuaById(id: Int): Flow<DuaModel> =
        duaDao.getDuaById(id).map { value: DuaEntity ->
            duaEntityToModelMapper.entityToModelMapper(value)
        }

    override fun getDuaByIds(ids: List<Int>): Flow<List<DuaModel>> {
        val chunkedIds = ids.chunked(200)
        return flow {
            chunkedIds.map { ids ->
                emit(
                    duaDao.getDuaByIds(ids)
                        .map { duaEntity -> duaEntityToModelMapper.entityToModelMapper(duaEntity) })
            }
        }
    }

    override fun getBookmarkedDuas(): Flow<List<DuaModel>> = duaDao.getBookmarkedDuas().mapper()
    override suspend fun isBookmarked(isBookmark: Boolean, id: Int) {
        duaDao.isBookmarked(isBookmark, id)
    }

    private fun Flow<List<DuaEntity>>.mapper(): Flow<List<DuaModel>> = this.map { duaList ->
        duaList.map { duaEntity ->
            duaEntityToModelMapper.entityToModelMapper(duaEntity)
        }
    }
}