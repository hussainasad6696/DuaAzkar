package  com.mera.islam.duaazkar.domain.usecases

import  com.mera.islam.duaazkar.core.Settings
import  com.mera.islam.duaazkar.core.substitution.CustomTextModel
import  com.mera.islam.duaazkar.domain.models.dua.DuaType
import  com.mera.islam.duaazkar.domain.repo.dua.DuaRepo
import  com.mera.islam.duaazkar.domain.repo.dua.DuaTranslationRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllDuaWithTranslationsUseCase @Inject constructor(
    val duaRepo: DuaRepo,
    private val duaTranslationRepo: DuaTranslationRepo,
    private val settings: Settings
) {
    operator fun invoke(duaType: DuaType): Flow<List<CustomTextModel>> {
        return (if (duaType == DuaType.ALL) duaRepo.getAllDuas()
        else duaRepo.getDuaByDuaType(duaType = duaType)).mapDuaFlowToDuaWithTranslationListFlow(
            languageIdsFlow = settings.getDuaSelectedTranslationIds(),
            duaTranslationRepo = duaTranslationRepo,
            settings = settings
        )
    }
}