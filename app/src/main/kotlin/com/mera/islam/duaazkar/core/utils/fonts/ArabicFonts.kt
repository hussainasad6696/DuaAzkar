package  com.mera.islam.duaazkar.core.utils.fonts

import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import  com.mera.islam.duaazkar.R
import java.util.Locale

enum class ArabicFonts(val font: Int): LanguageFonts {
    AL_QALAM_QURAN(R.font.al_qalam_quran),
    OMID(R.font.omid);
    override fun getFont(): FontFamily = FontFamily(Font(font))
    override fun font(): Int = font
    override fun fontType(): FontsType = FontsType.ARABIC_FONTS
    override fun getFontsList(): List<LanguageFonts> = entries
    override fun getName(): String = name.replace("_", " ")
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    companion object {
        fun getLanguageFont(font: Int): ArabicFonts {
            var defFont = AL_QALAM_QURAN

            for (item in entries) {
                if (item.font == font) {
                    defFont = item
                    break
                }
            }

            return defFont
        }
    }
}