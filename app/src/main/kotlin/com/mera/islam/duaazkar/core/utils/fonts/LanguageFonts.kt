package  com.mera.islam.duaazkar.core.utils.fonts

import androidx.compose.ui.text.font.FontFamily

interface LanguageFonts {
    fun getFont(): FontFamily
    fun font(): Int
    fun fontType(): FontsType
}