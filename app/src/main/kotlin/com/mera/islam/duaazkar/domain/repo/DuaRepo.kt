package  com.mera.islam.duaazkar.domain.repo

import  com.mera.islam.duaazkar.domain.models.DuaModel
import  com.mera.islam.duaazkar.domain.models.DuaType
import kotlinx.coroutines.flow.Flow

interface DuaRepo {
    fun getAllDuas(): Flow<List<DuaModel>>
    fun getDuaByDuaType(duaType: DuaType): Flow<List<DuaModel>>
    fun getDuaById(id: Int): Flow<DuaModel>
    fun getBookmarkedDuas(): Flow<List<DuaModel>>
    suspend fun isBookmarked(isBookmark: Boolean,id: Int)
}