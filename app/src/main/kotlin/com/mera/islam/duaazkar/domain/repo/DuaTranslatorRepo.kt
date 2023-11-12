package  com.mera.islam.duaazkar.domain.repo

import  com.mera.islam.duaazkar.domain.models.DuaTranslatorModel
import kotlinx.coroutines.flow.Flow

interface DuaTranslatorRepo {
    fun getAllTranslators(): Flow<List<DuaTranslatorModel>>
}