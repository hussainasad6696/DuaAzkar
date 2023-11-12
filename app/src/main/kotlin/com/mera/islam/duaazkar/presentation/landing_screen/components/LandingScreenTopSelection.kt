package  com.mera.islam.duaazkar.presentation.landing_screen.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import ir.kaaveh.sdpcompose.sdp

@Composable
fun LandingScreenTopSelection(
    modifier: Modifier = Modifier,
    @DrawableRes resource: Int
) {
    Box(modifier = modifier.wrapContentSize()) {
        Image(
            painter = painterResource(id = resource),
            contentDescription = "Selections",
            modifier = Modifier
                .size(200.sdp)
                .clip(RoundedCornerShape(12)),
            contentScale = ContentScale.Crop
        )
    }
}