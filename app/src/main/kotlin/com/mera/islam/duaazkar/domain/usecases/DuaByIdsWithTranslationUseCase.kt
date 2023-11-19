package com.mera.islam.duaazkar.domain.usecases

import com.mera.islam.duaazkar.core.Settings
import com.mera.islam.duaazkar.domain.repo.dua.DuaRepo
import com.mera.islam.duaazkar.domain.repo.dua.DuaTranslationRepo
import javax.inject.Inject

class DuaByIdsWithTranslationUseCase @Inject constructor(
    private val duaRepo: DuaRepo,
    private val duaTranslationRepo: DuaTranslationRepo,
    private val settings: Settings
) {
    operator fun invoke(duaIds: List<Int>) = duaRepo.getDuaByIds(duaIds).mapDuaFlowToDuaWithTranslationListFlow(
        languageIdsFlow = settings.getDuaSelectedTranslationIds(),
        duaTranslationRepo = duaTranslationRepo,
        settings = settings
    )
}