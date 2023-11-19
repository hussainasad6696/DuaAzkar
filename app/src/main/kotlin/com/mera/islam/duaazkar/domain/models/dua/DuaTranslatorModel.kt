package  com.mera.islam.duaazkar.domain.models.dua

import  com.mera.islam.duaazkar.core.enums.LanguageDirection

data class DuaTranslatorModel(
    val languageCode: String,
    val languageName: String,
    val languageDirection: LanguageDirection,
    val translatorName: String?,
    val translatorImageUrl: String?,
    val id: Int
)