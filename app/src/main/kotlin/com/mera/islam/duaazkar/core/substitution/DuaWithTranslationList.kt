package  com.mera.islam.duaazkar.core.substitution

import  com.mera.islam.duaazkar.core.extensions.build
import  com.mera.islam.duaazkar.domain.models.DuaModel

data class DuaWithTranslationList(
    val duaModel: DuaModel,
    val duaTranslations: List<DuaTranslationWithTranslators>
): ArabicWithTranslation {
    override fun getArabic(): String = duaModel.arabic
    override fun getTranslitration(): String = duaModel.translitration
    override fun getTranslation(): List<TranslationWithLanguageDirection> = duaTranslations
    override fun isFav(): Boolean = duaModel.isFav
    override fun getDataId(): Int = duaModel.id
    override fun reasonOrReference(): String = build {
        append(duaModel.reason)
        append("\n")
        append(duaModel.referenceFrom)
    }
    override fun getShareableString(): String = build {
        append(duaModel.arabic)
        append("\n")
        append(duaModel.translitration)
        append("\n")

        duaTranslations.forEach {
            append(it.duaTranslation.translation)
            append("\n")
        }

        append(duaModel.reason)
        append("\n")
        append("${duaModel.referenceType}-${duaModel.referenceFrom}")
    }
}