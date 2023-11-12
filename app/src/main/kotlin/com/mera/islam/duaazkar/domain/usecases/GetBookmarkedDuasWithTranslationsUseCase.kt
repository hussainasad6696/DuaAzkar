package  com.mera.islam.duaazkar.domain.usecases

import  com.mera.islam.duaazkar.core.Settings
import  com.mera.islam.duaazkar.core.substitution.ArabicWithTranslation
import  com.mera.islam.duaazkar.domain.repo.DuaRepo
import  com.mera.islam.duaazkar.domain.repo.DuaTranslationRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookmarkedDuasWithTranslationsUseCase @Inject constructor(
    val duaRepo: DuaRepo,
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