package  com.mera.islam.duaazkar.domain.usecases

import  com.mera.islam.duaazkar.core.Settings
import  com.mera.islam.duaazkar.core.enums.LanguageDirection
import  com.mera.islam.duaazkar.core.substitution.ArabicWithTranslation
import  com.mera.islam.duaazkar.core.substitution.DuaTranslationWithTranslators
import  com.mera.islam.duaazkar.core.substitution.DuaWithTranslationList
import  com.mera.islam.duaazkar.domain.models.DuaType
import  com.mera.islam.duaazkar.domain.repo.DuaRepo
import  com.mera.islam.duaazkar.domain.repo.DuaTranslationRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetAllDuaWithTranslationsUseCase @Inject constructor(
    val duaRepo: DuaRepo,
    private val duaTranslationRepo: DuaTranslationRepo,
    private val settings: Settings
) {
    operator fun invoke(duaType: DuaType): Flow<List<ArabicWithTranslation>> {
        return (if (duaType == DuaType.ALL) duaRepo.getAllDuas()
        else duaRepo.getDuaByDuaType(duaType = duaType)).mapDuaFlowToDuaWithTranslationListFlow(
            languageIdsFlow = settings.getDuaSelectedTranslationIds(),
            duaTranslationRepo = duaTranslationRepo,
            settings = settings
        )
    }
}