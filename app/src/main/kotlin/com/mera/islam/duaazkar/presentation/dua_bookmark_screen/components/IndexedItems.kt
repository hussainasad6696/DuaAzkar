package com.mera.islam.duaazkar.presentation.dua_bookmark_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
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
            .background(color = Color.White, shape = RoundedCornerShape(35))
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
                    color = lightTextGrayColor,
                    fontSize = 12.ssp,
                    fontFamily = RobotoFonts.ROBOTO_MEDIUM.getFont()
                )
            }
            Spacer(modifier = Modifier.width(10.sdp))
            Text(
                text = dua,
                color = darkTextGrayColor,
                fontSize = 13.ssp,
                fontFamily = RobotoFonts.ROBOTO_REGULAR.getFont(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.width(15.sdp))
        }
        Spacer(modifier = Modifier.height(12.sdp))
    }
}