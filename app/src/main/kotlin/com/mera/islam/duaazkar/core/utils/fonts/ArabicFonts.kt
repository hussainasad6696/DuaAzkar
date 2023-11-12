package  com.mera.islam.duaazkar.core.utils.fonts

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import  com.mera.islam.duaazkar.R

enum class ArabicFonts(val font: Int): LanguageFonts {
    AL_QALAM_QURAN(R.font.al_qalam_quran),
    OMID(R.font.omid);


    override fun getFont(): FontFamily = FontFamily(Font(font))
    override fun font(): Int = font
    override fun fontType(): FontsType = FontsType.ARABIC_FONTS
}