package  com.mera.islam.duaazkar.domain.repo.dua

import com.mera.islam.duaazkar.domain.models.dua.DuaType
import  com.mera.islam.duaazkar.domain.models.dua.relationalModels.DuaWithTranslationRelationalModel
import kotlinx.coroutines.flow.Flow

interface DuaTranslationRepo {
    suspend fun getDuaTranslationByDuaIds(ids: List<Int>,translatorIds: List<Int>): Flow<List<DuaWithTranslationRelationalModel>>
    suspend fun getRandomDuaWithTranslation(languageId: Int): DuaWithTranslationRelationalModel
    suspend fun getRandomDuaWithTranslationAndDuaType(languageId: Int,duaType: DuaType): DuaWithTranslationRelationalModel
}