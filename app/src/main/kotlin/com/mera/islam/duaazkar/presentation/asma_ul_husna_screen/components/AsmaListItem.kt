package com.mera.islam.duaazkar.presentation.asma_ul_husna_screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mera.islam.duaazkar.R
import com.mera.islam.duaazkar.ui.theme.RobotoFonts
import com.mera.islam.duaazkar.ui.theme.darkTextGrayColor
import com.mera.islam.duaazkar.ui.theme.lightTextGrayColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun AsmaListItem(
    index: Int = 1,
    title: String = "Ar-Rahman",
    subTitle: String = "The Beneficent",
    imageTint: Color,
    isPlaying: Boolean = false,
    onPlayClickIcon: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 10.sdp)
            .fillMaxWidth()
            .defaultMinSize(minHeight = 180.sdp)
            .drawBehind {
                drawRoundRect(
                    color = Color.White,
                    cornerRadius = CornerRadius(15.dp.toPx(), 15.dp.toPx())
                )
            }
    ) {
        AsyncImage(
            model = R.drawable.ic_asma_bg,
            contentDescription = "Bg",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize(),
            colorFilter = ColorFilter.tint(color = Color(0xffededed).copy(0.5f))
        )

        AsyncImage(
            model = R.drawable.asma_00,
            contentDescription = "AsmaImage",
            contentScale = ContentScale.None,
            modifier = Modifier
                .padding(top = 15.sdp)
                .align(Alignment.TopCenter)
                .size(70.sdp),
            colorFilter = ColorFilter.tint(color = imageTint)
        )

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(10.sdp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.size(40.sdp), contentAlignment = Alignment.Center) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_listing_icon),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.matchParentSize()
                )

                Text(
                    text = "$index",
                    color = Color.lightTextGrayColor,
                    fontSize = 12.ssp,
                    fontFamily = RobotoFonts.ROBOTO_MEDIUM.getFont(),
                    modifier = Modifier.padding(5.sdp)
                )
            }

            Spacer(modifier = Modifier.width(10.sdp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = Color.darkTextGrayColor,
                    fontSize = 16.ssp,
                    fontFamily = RobotoFonts.ROBOTO_MEDIUM.getFont()
                )

                Spacer(modifier = Modifier.height(2.sdp))

                Text(
                    text = subTitle,
                    color = Color.lightTextGrayColor,
                    fontSize = 16.ssp,
                    fontFamily = RobotoFonts.ROBOTO_REGULAR.getFont(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            IconButton(onClick = onPlayClickIcon) {
                Icon(
                    painter = painterResource(id = if (isPlaying) R.drawable.ic_pause_small_icon else R.drawable.ic_play_small_icon),
                    contentDescription = "Play/Pause",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(27.sdp)
                )
            }
        }
    }
}