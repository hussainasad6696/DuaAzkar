package  com.mera.islam.duaazkar.core.utils.fonts

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import  com.mera.islam.duaazkar.R
import java.util.Locale

enum class LeftLangFonts(val font: Int): LanguageFonts {
    ROBOTO(R.font.roboto),
    ALATA(R.font.alata),
    ADAMINA(R.font.adamina);

    override fun getFont(): FontFamily = FontFamily(Font(font))
    override fun font(): Int = font
    override fun fontType(): FontsType = FontsType.LEFT_FONTS
    override fun getFontsList(): List<LanguageFonts> = entries
    override fun getName(): String = name.replace("_", " ")
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    companion object {
        fun getLanguageFont(font: Int): LanguageFonts {
            var defFont = ROBOTO

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