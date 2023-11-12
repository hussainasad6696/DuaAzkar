package  com.mera.islam.duaazkar.core.substitution

import androidx.compose.ui.text.font.FontFamily
import  com.mera.islam.duaazkar.core.Settings
import  com.mera.islam.duaazkar.core.enums.LanguageDirection
import kotlinx.coroutines.flow.Flow

interface TranslationWithLanguageDirection {
    fun getTranslation(): String
    fun getLanguageDirection(): LanguageDirection
    fun languageId(): Int
    fun translatorName(): String? = null
    fun translatorImageUrl(): String? = null
    fun getTranslationFont(): FontFamily
}