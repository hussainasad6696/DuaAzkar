package  com.mera.islam.duaazkar.core.utils.fonts

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import  com.mera.islam.duaazkar.R

enum class RightLangFonts(val font: Int): LanguageFonts {
    JAMEEL_NOORI_URDU(R.font.jameel_noori_urdu),
    GANDHARA_REGULAR(R.font.gandhara_regular);

    override fun getFont(): FontFamily = FontFamily(Font(font))
    override fun font(): Int = font
    override fun fontType(): FontsType = FontsType.RIGHT_FONTS
}