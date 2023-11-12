package  com.mera.islam.duaazkar.core.substitution

import androidx.compose.ui.text.font.FontFamily
import  com.mera.islam.duaazkar.core.Settings
import  com.mera.islam.duaazkar.core.enums.LanguageDirection
import  com.mera.islam.duaazkar.core.utils.fonts.LeftLangFonts
import  com.mera.islam.duaazkar.domain.models.DuaTranslationModel
import  com.mera.islam.duaazkar.domain.models.DuaTranslatorModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first

data class DuaTranslationWithTranslators(
    val duaTranslation: DuaTranslationModel,
    val duaTranslator: DuaTranslatorModel,
    val fontFamily: FontFamily
) : TranslationWithLanguageDirection {
    override fun getTranslation(): String = duaTranslation.translation
    override fun getLanguageDirection(): LanguageDirection = duaTranslator.languageDirection
    override fun languageId(): Int = duaTranslator.id
    override fun translatorImageUrl(): String? = duaTranslator.translatorImageUrl
    override fun translatorName(): String? = duaTranslator.translatorName
    override fun getTranslationFont(): FontFamily = fontFamily
}
