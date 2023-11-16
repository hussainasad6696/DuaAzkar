package  com.mera.islam.duaazkar.core.presentation.arabic_with_translation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import com.mera.islam.duaazkar.R
import  com.mera.islam.duaazkar.core.TEXT_MIN_SIZE
import  com.mera.islam.duaazkar.core.substitution.ArabicWithTranslation
import  com.mera.islam.duaazkar.core.utils.ArabicContentType
import  com.mera.islam.duaazkar.core.utils.fonts.ArabicFonts
import com.mera.islam.duaazkar.ui.theme.darkTextGrayColor
import com.mera.islam.duaazkar.ui.theme.lightTextGrayColor
import com.mera.islam.duaazkar.ui.theme.primary
import ir.kaaveh.sdpcompose.sdp

@Composable
fun ArabicWithTranslationTextCell(
    arabicWithTranslation: ArabicWithTranslation,
    arabicFont: FontFamily = ArabicFonts.AL_QALAM_QURAN.getFont(),
    arabicColor: Color = darkTextGrayColor,
    translationColor: Color = darkTextGrayColor,
    index: Int = 0,
    textSize: TextUnit = TEXT_MIN_SIZE,
    onBookmarkedClick: () -> Unit = {},
    onItemClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onPlayAudio: () -> Unit = {}
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(color = Color.White, shape = RoundedCornerShape(20.sdp))
        .clickable(
            interactionSource = MutableInteractionSource(),
            indication = rememberRipple(
                radius = 16.sdp
            )
        ) { onItemClick() }
        .padding(horizontal = 5.sdp, vertical = 8.sdp)) {

        Spacer(modifier = Modifier.height(5.sdp))

        ArabicWithTranslationText(
            modifier = Modifier.padding(horizontal = 5.sdp),
            arabicWithTranslation = arabicWithTranslation,
            arabicColor = arabicColor,
            translationColor = translationColor,
            textSize = textSize,
            arabicFont = arabicFont
        )

        Spacer(modifier = Modifier.height(5.sdp))

        Divider(modifier = Modifier.padding(horizontal = 5.sdp))

        Spacer(modifier = Modifier.height(5.sdp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 5.sdp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onPlayAudio) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "Play",
                    tint = primary,
                    modifier = Modifier.size(22.sdp)
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBookmarkedClick) {
                    Icon(
                        imageVector = if (arabicWithTranslation.isFav()) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Play",
                        tint = lightTextGrayColor,
                        modifier = Modifier.size(22.sdp)
                    )
                }

                Spacer(modifier = Modifier.width(5.sdp))

                IconButton(onClick = onBookmarkedClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_content_copy_24),
                        contentDescription = "Copy",
                        tint = lightTextGrayColor,
                        modifier = Modifier.size(22.sdp)
                    )
                }

                Spacer(modifier = Modifier.width(5.sdp))

                IconButton(onClick = onShareClick) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = "Share",
                        tint = lightTextGrayColor,
                        modifier = Modifier.size(22.sdp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(5.sdp))
    }
}