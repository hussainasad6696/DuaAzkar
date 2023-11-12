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
        val duaFlow = if (duaType == DuaType.ALL) duaRepo.getAllDuas()
        else duaRepo.getDuaByDuaType(duaType = duaType)

        val languageIdsFlow = settings.getDuaSelectedTranslationIds()

        return combine(
            duaFlow,
            languageIdsFlow,
            settings.getLeftFont(),
            settings.getRightFont()
        ) { duas, languageIds, leftFont, rightFont ->
            val duaTrans = duaTranslationRepo.getDuaTranslationByDuaIds(
                ids = duas.map { it.id },
                translatorIds = languageIds
            ).first()

            List(duas.size) { index ->
                val dua = duas[index]

                val translations = duaTrans.filter {
                    it.duaModel.id == dua.id
                }.map {
                    DuaTranslationWithTranslators(
                        duaTranslation = it.duaTranslationModel,
                        duaTranslator = it.duaTranslatorModel,
                        fontFamily = when (it.duaTranslatorModel.languageDirection) {
                            LanguageDirection.RIGHT -> rightFont
                            LanguageDirection.LEFT -> leftFont
                        }
                    )
                }

                DuaWithTranslationList(
                    duaModel = dua,
                    duaTranslations = translations
                )
            }
        }
    }
}