package  com.mera.islam.duaazkar.core.substitution

interface ArabicWithTranslation {
    fun getArabic(): String
    fun getTranslitration(): String? = null
    fun getTranslation(): List<TranslationWithLanguageDirection>
    fun isFav(): Boolean
    fun getDataId(): Int
    fun getShareableString(): String
    fun reasonOrReference(): String
    fun getDataType(): Any
}