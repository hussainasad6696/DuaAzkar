package  com.mera.islam.duaazkar.core.utils.fonts

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import  com.mera.islam.duaazkar.R
import java.util.Locale

enum class RightLangFonts(val font: Int) : LanguageFonts {
    JAMEEL_NOORI_URDU(R.font.jameel_noori_urdu),
    GANDHARA_REGULAR(R.font.gandhara_regular);

    override fun getFont(): FontFamily = FontFamily(Font(font))
    override fun font(): Int = font
    override fun fontType(): FontsType = FontsType.RIGHT_FONTS
    override fun getFontsList(): List<LanguageFonts> = entries
    override fun getName(): String = name.replace("_", " ")
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

    companion object {
        fun getLanguageFont(font: Int): LanguageFonts {
            var defFont = JAMEEL_NOORI_URDU

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