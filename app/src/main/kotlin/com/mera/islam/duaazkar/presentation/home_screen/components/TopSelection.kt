package  com.mera.islam.duaazkar.presentation.home_screen.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.mera.islam.duaazkar.R
import com.mera.islam.duaazkar.ui.theme.RobotoFonts
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun LandingScreenTopSelection(
    modifier: Modifier = Modifier,
    @StringRes name: Int,
    @DrawableRes resource: Int,
    subTitle: String,
    onItemClick: () -> Unit
) {
    Box(modifier = modifier
        .clickable(
            interactionSource = MutableInteractionSource(),
            indication = rememberRipple()
        ) { onItemClick() }) {
        Image(
            painter = painterResource(id = resource),
            contentDescription = "Selections",
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(12)),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(15.sdp)
        ) {
            Text(
                text = stringResource(id = name),
                color = Color.White,
                fontSize = 13.ssp,
                fontFamily = RobotoFonts.ROBOTO_BOLD.getFont()
            )

            Spacer(modifier = Modifier.height(4.sdp))

            Text(
                text = subTitle,
                color = Color.White.copy(0.5f),
                fontFamily = RobotoFonts.ROBOTO_REGULAR.getFont(),
                fontSize = 10.ssp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}