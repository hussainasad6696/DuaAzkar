package  com.mera.islam.duaazkar.domain.usecases

import  com.mera.islam.duaazkar.core.Settings
import  com.mera.islam.duaazkar.core.substitution.ArabicWithTranslation
import  com.mera.islam.duaazkar.core.substitution.DuaTranslationWithTranslators
import  com.mera.islam.duaazkar.core.substitution.DuaWithTranslationList
import  com.mera.islam.duaazkar.domain.repo.DuaRepo
import  com.mera.islam.duaazkar.domain.repo.DuaTranslationRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetBookmarkedDuasWithTranslations @Inject constructor(
    private val duaRepo: DuaRepo,
    private val duaTranslationRepo: DuaTranslationRepo,
    private val settings: Settings
) {
    operator fun invoke(): Flow<List<ArabicWithTranslation>> = duaRepo.getBookmarkedDuas()
        .mapDuaFlowToDuaWithTranslationListFlow(
            settings.getDuaSelectedTranslationIds(),
            duaTranslationRepo,
            settings
        )
}