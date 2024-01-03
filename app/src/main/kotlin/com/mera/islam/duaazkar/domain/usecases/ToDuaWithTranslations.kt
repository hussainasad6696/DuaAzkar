package  com.mera.islam.duaazkar.domain.usecases

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import  com.mera.islam.duaazkar.core.Settings
import  com.mera.islam.duaazkar.core.enums.LanguageDirection
import  com.mera.islam.duaazkar.core.substitution.CustomTextModel
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
): Flow<List<CustomTextModel>> = combine(
    this,
    languageIdsFlow,
    settings.getLeftFont(),
    settings.getRightFont()
) { duas, languageIds, leftFont, rightFont ->

    val fontSize = settings.getDuaTextSize().first()

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
                fontFamily = when (it.duaTranslatorModel.languageDirection) {
                    LanguageDirection.LEFT -> FontFamily(Font(leftFont))
                    LanguageDirection.RIGHT -> FontFamily(Font(rightFont))
                }
            )
        }

        DuaWithTranslationList(
            duaModel = dua,
            duaTranslations = translationList,
            fontSize = fontSize
        )
    }
}

fun Flow<DuaModel>.mapDuaFlowToDuaWithTranslationModelFlow(
    languageIdsFlow: Flow<List<Int>>,
    duaTranslationRepo: DuaTranslationRepo,
    settings: Settings
): Flow<CustomTextModel> = combine(
    this,
    languageIdsFlow,
    settings.getLeftFont(),
    settings.getRightFont()
) { dua, languageIds, leftFont, rightFont ->
    val fontSize = settings.getDuaTextSize().first()

    val duaTrans = duaTranslationRepo.getDuaTranslationByDuaIds(
        ids = listOf(dua.id),
        translatorIds = languageIds
    ).first()

    val translationList = duaTrans.filter {
        it.duaModel.id == dua.id
    }.map {
        DuaTranslationWithTranslators(
            duaTranslation = it.duaTranslationModel,
            duaTranslator = it.duaTranslatorModel,
            fontFamily = when (it.duaTranslatorModel.languageDirection) {
                LanguageDirection.LEFT -> FontFamily(Font(leftFont))
                LanguageDirection.RIGHT -> FontFamily(Font(rightFont))
            }
        )
    }

    DuaWithTranslationList(
        duaModel = dua,
        duaTranslations = translationList,
        fontSize = fontSize
    )
}