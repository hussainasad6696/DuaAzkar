package  com.mera.islam.duaazkar.domain.usecases

import  com.mera.islam.duaazkar.core.Settings
import  com.mera.islam.duaazkar.core.enums.LanguageDirection
import  com.mera.islam.duaazkar.core.substitution.ArabicModelWithTranslationModel
import  com.mera.islam.duaazkar.core.substitution.DuaTranslationWithTranslators
import  com.mera.islam.duaazkar.core.substitution.DuaWithTranslationList
import  com.mera.islam.duaazkar.domain.models.dua.DuaModel
import  com.mera.islam.duaazkar.domain.repo.dua.DuaTranslationRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first

fun Flow<List<DuaModel>>.mapDuaFlowToDuaWithTranslationListFlow(
    languageIdsFlow: Flow<List<Int>>,
    duaTranslationRepo: DuaTranslationRepo,
    settings: Settings
): Flow<List<ArabicModelWithTranslationModel>> {

    return combine(
        this,
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

            val translationList = duaTrans.filter {
                it.duaModel.id == dua.id
            }.map {
                DuaTranslationWithTranslators(
                    duaTranslation = it.duaTranslationModel,
                    duaTranslator = it.duaTranslatorModel,
                    fontFamily = when(it.duaTranslatorModel.languageDirection) {
                        LanguageDirection.LEFT -> leftFont
                        LanguageDirection.RIGHT -> rightFont
                    }
                )
            }

            DuaWithTranslationList(
                duaModel = dua,
                duaTranslations = translationList
            )
        }
    }
}

suspend inline fun Flow<DuaModel>.mapDuaFlowToDuaWithTranslationModelFlow(
    languageIdsFlow: Flow<List<Int>>,
    duaTranslationRepo: DuaTranslationRepo,
    settings: Settings
): Flow<ArabicModelWithTranslationModel> {
    val duaTransFlow = duaTranslationRepo.getDuaTranslationByDuaIds(
        ids = listOf(this.first().id),
        translatorIds = languageIdsFlow.first()
    )

    return combine(
        this,
        duaTransFlow,
        settings.getLeftFont(),
        settings.getRightFont()
    ) { dua, translations, leftFont, rightFont ->
        val translationList = translations.filter {
            it.duaModel.id == dua.id
        }.map {
            DuaTranslationWithTranslators(
                duaTranslation = it.duaTranslationModel,
                duaTranslator = it.duaTranslatorModel,
                fontFamily = when(it.duaTranslatorModel.languageDirection) {
                    LanguageDirection.LEFT -> leftFont
                    LanguageDirection.RIGHT -> rightFont
                }
            )
        }

        DuaWithTranslationList(
            duaModel = dua,
            duaTranslations = translationList
        )
    }
}