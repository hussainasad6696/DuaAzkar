package com.mera.islam.duaazkar.presentation.dua_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mera.islam.duaazkar.R
import com.mera.islam.duaazkar.ui.theme.RobotoFonts
import com.mera.islam.duaazkar.ui.theme.darkTextGrayColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun DuaSettingsNavigationTitle(title: String,onClose: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.sdp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            color = darkTextGrayColor,
            fontSize = 20.ssp,
            fontFamily = RobotoFonts.ROBOTO_BOLD.getFont()
        )

        IconButton(onClick = onClose) {
            Icon(
                painter = painterResource(id = R.drawable.ic_cancel_close_icon),
                contentDescription = "close",
                tint = Color.Unspecified
            )
        }
    }

    Spacer(modifier = Modifier.height(5.sdp))

    Divider(thickness = 1.dp, color = darkTextGrayColor.copy(0.10f))

    Spacer(modifier = Modifier.height(10.sdp))
}