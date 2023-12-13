package com.mera.islam.duaazkar.presentation.asma_ul_husna_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.PrimaryKey
import coil.compose.AsyncImage
import com.mera.islam.duaazkar.R
import com.mera.islam.duaazkar.ui.theme.RobotoFonts
import com.mera.islam.duaazkar.ui.theme.darkTextGrayColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Preview
@Composable
fun AsmaGridItem(
    title: String = "Ar-Rahman",
) {
    Column(
        modifier = Modifier
            .defaultMinSize(minHeight = 120.sdp, minWidth = 120.sdp)
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
            model = painterResource(id = R.drawable.asma_00),
            contentDescription = "AsmaImage",
            contentScale = ContentScale.None,
            modifier = Modifier
                .size(70.sdp)
        )

        Spacer(modifier = Modifier.height(5.sdp))

        Text(
            text = title,
            color = Color.darkTextGrayColor,
            fontSize = 16.ssp,
            fontFamily = RobotoFonts.ROBOTO_MEDIUM.getFont()
        )
    }
}