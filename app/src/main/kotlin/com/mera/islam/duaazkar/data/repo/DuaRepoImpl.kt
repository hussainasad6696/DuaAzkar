package  com.mera.islam.duaazkar.data.repo

import  com.mera.islam.duaazkar.core.EntityModelMapper
import  com.mera.islam.duaazkar.data.local.dao.DuaDao
import com.mera.islam.duaazkar.data.local.dao.DuaNameAndCount
import  com.mera.islam.duaazkar.data.local.entities.DuaEntity
import  com.mera.islam.duaazkar.domain.models.DuaModel
import  com.mera.islam.duaazkar.domain.models.DuaType
import  com.mera.islam.duaazkar.domain.repo.DuaRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DuaRepoImpl(
    private val duaDao: DuaDao,
    private val duaEntityToModelMapper: EntityModelMapper<DuaEntity, DuaModel>
) : DuaRepo {
    override fun getAllDuas(): Flow<List<DuaModel>> = duaDao.getAllDuas().mapper()
    override fun getAllDuaTypesAndCounts(): Flow<List<Pair<DuaType, Int>>> =
        duaDao.getAllDuaTypesAndCounts().map { list ->
            list.map {
                Pair(DuaType.toDuaType(it.duaType), it.count)
            }
        }

    override fun getAllDuaTypesAndCountsByKeyword(keyword: String): Flow<List<Pair<DuaType, Int>>> = duaDao.getAllDuaTypesAndCountsByKeyword(keyword).map { list ->
        list.map {
            Pair(DuaType.toDuaType(it.duaType), it.count)
        }
    }
    override fun getDuaByDuaType(duaType: DuaType): Flow<List<DuaModel>> =
        duaDao.getDuaByDuaType(duaType.type).mapper()

    override fun getDuaById(id: Int): Flow<DuaModel> =
        duaDao.getDuaById(id).map { value: DuaEntity ->
            duaEntityToModelMapper.entityToModelMapper(value)
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