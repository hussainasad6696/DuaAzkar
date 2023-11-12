package  com.mera.islam.duaazkar.core.utils.fonts

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import  com.mera.islam.duaazkar.R

enum class LeftLangFonts(val font: Int): LanguageFonts {
    ROBOTO(R.font.roboto),
    ALATA(R.font.alata),
    ADAMINA(R.font.adamina);

    override fun getFont(): FontFamily = FontFamily(Font(font))
    override fun font(): Int = font
    override fun fontType(): FontsType = FontsType.LEFT_FONTS
}