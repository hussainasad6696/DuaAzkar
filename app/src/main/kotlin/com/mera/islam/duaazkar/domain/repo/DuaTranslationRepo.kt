package  com.mera.islam.duaazkar.domain.repo

import  com.mera.islam.duaazkar.domain.models.relationalModels.DuaWithTranslationRelationalModel
import kotlinx.coroutines.flow.Flow

interface DuaTranslationRepo {
    suspend fun getDuaTranslationByDuaIds(ids: List<Int>,translatorIds: List<Int>): Flow<List<DuaWithTranslationRelationalModel>>
    suspend fun getRandomDuaWithTranslation(languageId: Int): DuaWithTranslationRelationalModel
}