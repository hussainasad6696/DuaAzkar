package  com.mera.islam.duaazkar.core.substitution

import androidx.compose.ui.unit.TextUnit

interface ArabicModelWithTranslationModel {
    fun getArabic(): String
    fun getTransliteration(): String? = null
    fun getTranslation(): List<TranslationWithLanguageDirection>
    fun isFav(): Boolean
    fun getDataId(): Int
    fun getShareableString(): String
    fun reasonOrReference(): String
    fun getDataType(): Any
    fun fontSize(): TextUnit
}