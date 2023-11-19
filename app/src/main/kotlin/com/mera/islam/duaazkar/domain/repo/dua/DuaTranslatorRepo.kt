package  com.mera.islam.duaazkar.domain.repo.dua

import  com.mera.islam.duaazkar.domain.models.dua.DuaTranslatorModel
import kotlinx.coroutines.flow.Flow

interface DuaTranslatorRepo {
    fun getAllTranslators(): Flow<List<DuaTranslatorModel>>
}