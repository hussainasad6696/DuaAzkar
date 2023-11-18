package  com.mera.islam.duaazkar.core.presentation.arabic_with_translation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.mera.islam.duaazkar.core.TEXT_MIN_SIZE
import com.mera.islam.duaazkar.core.enums.LanguageDirection
import com.mera.islam.duaazkar.core.extensions.findWordPositions
import com.mera.islam.duaazkar.core.substitution.ArabicWithTranslation
import com.mera.islam.duaazkar.core.utils.fonts.ArabicFonts
import com.mera.islam.duaazkar.core.utils.fonts.LeftLangFonts
import com.mera.islam.duaazkar.ui.theme.darkTextGrayColor
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.runBlocking

@Composable
fun ArabicWithTranslationText(
    modifier: Modifier = Modifier,
    arabicWithTranslation: ArabicWithTranslation,
    arabicColor: Color = darkTextGrayColor,
    translationColor: Color = darkTextGrayColor,
    textSize: TextUnit = TEXT_MIN_SIZE,
    arabicFont: FontFamily = ArabicFonts.AL_QALAM_QURAN.getFont(),
    matchTextList: List<String> = emptyList()
) {

    val annotatedString = buildAnnotatedString {
        withStyle(style = ParagraphStyle(textAlign = TextAlign.End, lineHeight = 30.ssp)) {
            append(
                AnnotatedString(
                    text = arabicWithTranslation.getArabic().replace("\n"," "),
                    spanStyle = SpanStyle(
                        color = arabicColor,
                        fontFamily = arabicFont,
                        fontSize = textSize
                    )
                )
            )
        }

        append("\n")

        arabicWithTranslation.getTranslitration()?.let { translitration ->
            withStyle(style = ParagraphStyle(textAlign = TextAlign.Start)) {
                append(
                    AnnotatedString(
                        text = translitration,
                        spanStyle = SpanStyle(
                            color = Color(0xFF01AD8E),
                            fontFamily = LeftLangFonts.ROBOTO.getFont(),
                            fontSize = (textSize.value * 0.8f).sp
                        )
                    )
                )
            }
        }

        append("\n")

        arabicWithTranslation.getTranslation().forEach { translationWithLanguageDirection ->

            withStyle(
                style = ParagraphStyle(
                    textAlign = if (translationWithLanguageDirection.getLanguageDirection() == LanguageDirection.RIGHT)
                        TextAlign.End
                    else TextAlign.Start
                )
            ) {
                append(
                    AnnotatedString(
                        text = translationWithLanguageDirection.getTranslation(),
                        spanStyle = SpanStyle(
                            color = translationColor,
                            fontFamily = translationWithLanguageDirection.getTranslationFont(),
                            fontSize = (textSize.value * 0.6f).sp
                        )
                    )
                )
                append("\n")
            }
        }

        withStyle(style = ParagraphStyle(textAlign = TextAlign.Start)) {
            append(
                AnnotatedString(
                    text = arabicWithTranslation.reasonOrReference(),
                    spanStyle = SpanStyle(
                        color = translationColor.copy(0.5f),
                        fontFamily = LeftLangFonts.ROBOTO.getFont(),
                        fontSize = (textSize.value * 0.5f).sp
                    )
                )
            )
        }

        matchTextList.forEach { text ->
            this.toAnnotatedString().text.findWordPositions(text).forEach { startingCharacterPosition ->
                addStyle(
                    style = SpanStyle(color = Color.Blue),
                    start = startingCharacterPosition,
                    end = startingCharacterPosition + text.length
                )
            }
        }
    }

    Text(
        text = annotatedString,
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize()
    )
}

fun main() {
    runBlocking {
        val demo = "Allah"
        println(demo.length)
    }
}