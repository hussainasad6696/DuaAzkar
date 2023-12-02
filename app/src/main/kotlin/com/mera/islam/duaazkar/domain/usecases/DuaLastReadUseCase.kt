package  com.mera.islam.duaazkar.domain.usecases

import  com.mera.islam.duaazkar.core.Settings
import  com.mera.islam.duaazkar.core.substitution.ArabicModelWithTranslationModel
import  com.mera.islam.duaazkar.domain.repo.dua.DuaRepo
import  com.mera.islam.duaazkar.domain.repo.dua.DuaTranslationRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DuaLastReadUseCase @Inject constructor(
    private val duaRepo: DuaRepo,
    private val duaTranslationRepo: DuaTranslationRepo,
    private val settings: Settings
) {
    suspend operator fun invoke(): Flow<ArabicModelWithTranslationModel> {
        val duaId = settings.getDuaLastReadId().first()
        return if (duaId == -1) emptyFlow()
        else {
            duaRepo.getDuaById(duaId).mapDuaFlowToDuaWithTranslationModelFlow(
                languageIdsFlow = settings.getDuaSelectedTranslationIds(),
                duaTranslationRepo = duaTranslationRepo,
                settings = settings
            )
        }
    }

    suspend operator fun invoke(duaId: Int) {
        settings.setDuaLastReadId(duaId)
    }
}