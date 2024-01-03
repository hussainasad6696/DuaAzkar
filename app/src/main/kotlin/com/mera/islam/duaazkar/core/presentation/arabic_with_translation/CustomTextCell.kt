package  com.mera.islam.duaazkar.core.presentation.arabic_with_translation

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.mera.islam.duaazkar.R
import com.mera.islam.duaazkar.core.TEXT_MIN_SIZE
import com.mera.islam.duaazkar.core.extensions.dpToPx
import com.mera.islam.duaazkar.core.substitution.CustomTextModel
import com.mera.islam.duaazkar.core.utils.fonts.ArabicFonts
import com.mera.islam.duaazkar.ui.theme.RobotoFonts
import com.mera.islam.duaazkar.ui.theme.darkTextGrayColor
import com.mera.islam.duaazkar.ui.theme.green
import com.mera.islam.duaazkar.ui.theme.lightTextGrayColor
import com.mera.islam.duaazkar.ui.theme.primary
import com.mera.islam.duaazkar.ui.theme.transliterationBlurColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun CustomTextCell(
    customTextModel: CustomTextModel,
    arabicFont: FontFamily = ArabicFonts.AL_QALAM_QURAN.getFont(),
    arabicColor: Color = Color.darkTextGrayColor,
    translationColor: Color = Color.darkTextGrayColor,
    transliterationColor: Color = Color.transliterationBlurColor,
    textSize: TextUnit = TEXT_MIN_SIZE,
    isDarkTheme: Boolean = false,
    matchTextList: List<String> = emptyList(),
    isPlaying: Boolean = false,
    cardBackgroundColor: Color = Color.White,
    onBookmarkedClick: () -> Unit = {},
    onItemClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onPlayAudio: () -> Unit = {},
    onCopyClick: () -> Unit = {},
    onOpenTashib: () -> Unit = {}
) {
    val animatedBgColor by animateColorAsState(
        cardBackgroundColor,
        label = "color"
    )

    Column(modifier = Modifier
        .fillMaxWidth()
        .drawBehind {
            drawRoundRect(
                color = animatedBgColor,
                cornerRadius = CornerRadius(20.dp.toPx(), 20.dp.toPx())
            )
        }
        .clickable(
            interactionSource = MutableInteractionSource(),
            indication = rememberRipple(
                radius = 16.sdp
            )
        ) { onItemClick() }
        .padding(10.sdp)) {

        Spacer(modifier = Modifier.height(5.sdp))

        CustomText(
            modifier = Modifier.padding(horizontal = 5.sdp),
            customTextModel = customTextModel,
            arabicColor = arabicColor,
            translationColor = translationColor,
            transliterationColor = transliterationColor,
            textSize = textSize,
            arabicFont = arabicFont,
            matchTextList = matchTextList
        )

        Spacer(modifier = Modifier.height(10.sdp))

        CellClickEvents(
            isBookmarked = customTextModel.isFav(),
            isPlaying = isPlaying,
            isDarkTheme = isDarkTheme,
            onPlayAudio = onPlayAudio,
            onOpenTasbih = onOpenTashib,
            onCopyClick = onCopyClick,
            onBookmarkedClick = onBookmarkedClick,
            onShareClick = onShareClick
        )

        Spacer(modifier = Modifier.height(10.sdp))
    }
}

@Composable
fun CellClickEvents(
    modifier: Modifier = Modifier,
    isPlaying: Boolean = false,
    isBookmarked: Boolean,
    isDarkTheme: Boolean = false,
    onPlayAudio: () -> Unit,
    onOpenTasbih: () -> Unit,
    onCopyClick: () -> Unit,
    onBookmarkedClick: () -> Unit,
    onShareClick: () -> Unit
) {
    val dpToPx = 15.dp.dpToPx()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.sdp)
            .drawBehind {
                drawRoundRect(
                    color = Color.Transparent,
                    cornerRadius = CornerRadius(dpToPx, dpToPx),
                )
            }
            .border(width = 1.dp, shape = RoundedCornerShape(dpToPx), color = Color(0x0c252525))
            .then(modifier)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .drawBehind {
                    drawRoundRect(
                        color = if (isDarkTheme) Color(0x0cffffff) else Color(0x0c252525),
                        cornerRadius = CornerRadius(15.dp.toPx(), 15.dp.toPx()),
                    )
                }
                .padding(horizontal = 10.sdp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onPlayAudio) {
                Icon(
                    painter = painterResource(id = if (isPlaying) R.drawable.ic_resume_icon else R.drawable.ic_play_icon),
                    contentDescription = "Play",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(22.sdp)
                )
            }

            val animatedBgColor by animateColorAsState(
                if (isDarkTheme) Color(0x1901ad8e) else Color.White,
                label = "color"
            )

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = animatedBgColor
                ),
                modifier = Modifier
                    .fillMaxHeight(0.75f),
                onClick = onOpenTasbih
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_tasbih_icon),
                    contentDescription = "Tasbih",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(19.sdp)
                )

                Spacer(modifier = Modifier.width(5.sdp))

                Text(
                    text = stringResource(id = R.string.tasbih),
                    color = Color.green,
                    fontSize = 11.ssp,
                    fontFamily = RobotoFonts.ROBOTO_MEDIUM.getFont()
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                onClick = onCopyClick
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_copy_icon),
                    contentDescription = "Copy",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(18.sdp)
                )

                Spacer(modifier = Modifier.width(5.sdp))

                Text(
                    text = stringResource(id = R.string.copy),
                    fontSize = 10.ssp,
                    fontFamily = RobotoFonts.ROBOTO_REGULAR.getFont(),
                    color = Color.lightTextGrayColor
                )
            }


            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                onClick = onBookmarkedClick
            ) {
                Icon(
                    painter = painterResource(
                        id = if (isBookmarked)
                            R.drawable.ic_bookmark_selected_icon
                        else R.drawable.ic_bookmark_light_icon
                    ),
                    contentDescription = "bookmark",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(18.sdp)
                )

                Spacer(modifier = Modifier.width(5.sdp))

                Text(
                    text = stringResource(id = R.string.bookmark),
                    fontSize = 10.ssp,
                    fontFamily = RobotoFonts.ROBOTO_REGULAR.getFont(),
                    color = if (isBookmarked) Color.primary else Color.lightTextGrayColor
                )
            }


            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                onClick = onShareClick
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_share_icon),
                    contentDescription = "Share",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(18.sdp)
                )

                Spacer(modifier = Modifier.width(5.sdp))

                Text(
                    text = stringResource(id = R.string.share),
                    fontSize = 10.ssp,
                    fontFamily = RobotoFonts.ROBOTO_REGULAR.getFont(),
                    color = Color.lightTextGrayColor
                )
            }
        }
    }
}