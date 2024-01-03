package com.mera.islam.duaazkar.domain.usecases

import com.mera.islam.duaazkar.core.Settings
import com.mera.islam.duaazkar.core.substitution.CustomTextModel
import com.mera.islam.duaazkar.domain.repo.dua.DuaRepo
import com.mera.islam.duaazkar.domain.repo.dua.DuaTranslationRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDuaByIdWithTranslationUseCase @Inject constructor(
    private val duaRepo: DuaRepo,
    private val duaTranslationRepo: DuaTranslationRepo,
    private val settings: Settings
) {
    operator fun invoke(duaId: Int): Flow<CustomTextModel> {
        return duaRepo.getDuaById(duaId).mapDuaFlowToDuaWithTranslationModelFlow(
            languageIdsFlow = settings.getDuaSelectedTranslationIds(),
            duaTranslationRepo = duaTranslationRepo,
            settings = settings
        )
    }
}