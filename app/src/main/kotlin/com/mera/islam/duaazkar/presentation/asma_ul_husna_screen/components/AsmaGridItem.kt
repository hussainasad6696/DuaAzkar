package com.mera.islam.duaazkar.presentation.asma_ul_husna_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.PrimaryKey
import coil.compose.AsyncImage
import com.mera.islam.duaazkar.R
import com.mera.islam.duaazkar.ui.theme.RobotoFonts
import com.mera.islam.duaazkar.ui.theme.darkTextGrayColor
import com.mera.islam.duaazkar.ui.theme.lightTextGrayColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun AsmaGridItem(
    title: String = "Ar-Rahman",
    imageTint: Color
) {
    Column(
        modifier = Modifier
            .defaultMinSize(minHeight = 104.sdp, minWidth = 104.sdp)
            .drawBehind {
                drawRoundRect(
                    color = Color.White,
                    cornerRadius = CornerRadius(15.dp.toPx(), 15.dp.toPx())
                )
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = R.drawable.asma_00,
            contentDescription = "AsmaImage",
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .width(55.sdp)
                .height(47.sdp),
            colorFilter = ColorFilter.tint(color = imageTint)
        )

        Spacer(modifier = Modifier.height(10.sdp))

        Text(
            text = title,
            color = Color.lightTextGrayColor,
            fontSize = 12.ssp,
            fontFamily = RobotoFonts.ROBOTO_REGULAR.getFont(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 2.sdp)
        )
    }
}