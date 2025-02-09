package  com.mera.islam.duaazkar.domain.repo.dua

import com.mera.islam.duaazkar.data.local.dao.dua.DuaNameAndCount
import com.mera.islam.duaazkar.domain.models.dua.DuaModel
import com.mera.islam.duaazkar.domain.models.dua.DuaType
import kotlinx.coroutines.flow.Flow

interface DuaRepo {
    fun getAllDuas(): Flow<List<DuaModel>>
    fun getAllDuaTypesAndCounts(): Flow<List<DuaNameAndCount>>
    fun getAllDuaTypesAndCountsByKeyword(keyword: String): Flow<List<DuaNameAndCount>>
    fun getDuaByDuaType(duaType: DuaType): Flow<List<DuaModel>>
    fun getDuaById(id: Int): Flow<DuaModel>
    fun getDuaByIds(ids: List<Int>): Flow<List<DuaModel>>
    fun getBookmarkedDuas(): Flow<List<DuaModel>>
    suspend fun isBookmarked(isBookmark: Boolean,id: Int)
}