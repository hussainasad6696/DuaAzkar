package com.mera.islam.duaazkar.presentation.dua_bookmark_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mera.islam.duaazkar.R
import com.mera.islam.duaazkar.ui.theme.RobotoFonts
import com.mera.islam.duaazkar.ui.theme.darkTextGrayColor
import com.mera.islam.duaazkar.ui.theme.lightTextGrayColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun IndexedItems(index: Int,dua: String,onItemClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                drawRoundRect(
                    color = Color.White,
                    cornerRadius = CornerRadius(50.dp.toPx(), 50.dp.toPx()),
                )
            }
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple()
            ) { onItemClick() }
    ) {
        Spacer(modifier = Modifier.height(12.sdp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(10.sdp))
            Box(modifier = Modifier.size(40.sdp), contentAlignment = Alignment.Center) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_listing_icon),
                    contentDescription = "DuaType icon",
                    tint = Color.Unspecified,
                    modifier = Modifier.matchParentSize()
                )

                Text(
                    text = index.toString(),
                    color = Color.lightTextGrayColor,
                    fontSize = 12.ssp,
                    fontFamily = RobotoFonts.ROBOTO_MEDIUM.getFont()
                )
            }
            Spacer(modifier = Modifier.width(10.sdp))

            Text(
                text = dua,
                color = Color.darkTextGrayColor,
                fontSize = 13.ssp,
                fontFamily = RobotoFonts.ROBOTO_REGULAR.getFont(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(end = 15.sdp)
            )
        }
        Spacer(modifier = Modifier.height(12.sdp))
    }
}