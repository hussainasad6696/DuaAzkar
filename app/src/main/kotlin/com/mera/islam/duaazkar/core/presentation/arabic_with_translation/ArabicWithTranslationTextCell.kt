package  com.mera.islam.duaazkar.core.presentation.arabic_with_translation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import  com.mera.islam.duaazkar.core.TEXT_MIN_SIZE
import  com.mera.islam.duaazkar.core.substitution.ArabicWithTranslation
import  com.mera.islam.duaazkar.core.utils.ArabicContentType
import  com.mera.islam.duaazkar.core.utils.fonts.ArabicFonts
import ir.kaaveh.sdpcompose.sdp

@Composable
fun ArabicWithTranslationTextCell(
    arabicWithTranslation: ArabicWithTranslation,
    arabicFont: FontFamily = ArabicFonts.AL_QALAM_QURAN.getFont(),
    arabicColor: Color = Color.Black,
    translationColor: Color = Color.Black,
    index: Int = 0,
    textSize: TextUnit = TEXT_MIN_SIZE,
    onBookmarkedClick: () -> Unit = {},
    onItemClick: () -> Unit = {},
    onShareClick: () -> Unit = {}
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable(
            interactionSource = MutableInteractionSource(),
            indication = rememberRipple(
                radius = 16.sdp
            )
        ) { onItemClick() }) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            IconButton(onClick = onShareClick) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share",
                    tint = Color.Black
                )
            }

            IconButton(onClick = onBookmarkedClick) {
                Icon(
                    imageVector = if (arabicWithTranslation.isFav()) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Bookmark",
                    tint = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(5.sdp))

        Text(text = (index + 1).toString())

        Spacer(modifier = Modifier.height(5.sdp))

        ArabicWithTranslationText(
            arabicWithTranslation = arabicWithTranslation,
            arabicColor = arabicColor,
            translationColor = translationColor,
            textSize = textSize,
            arabicFont = arabicFont
        )
    }
}