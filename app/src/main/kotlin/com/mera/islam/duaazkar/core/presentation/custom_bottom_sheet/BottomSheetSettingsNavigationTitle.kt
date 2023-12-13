package com.mera.islam.duaazkar.core.presentation.custom_bottom_sheet

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
import androidx.compose.ui.unit.dp
import com.mera.islam.duaazkar.R
import com.mera.islam.duaazkar.ui.theme.RobotoFonts
import com.mera.islam.duaazkar.ui.theme.darkTextGrayColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun BottomSheetSettingsNavigationTitle(
    title: String,
    addCloseButton: Boolean = true,
    onClose: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.sdp, vertical = 10.sdp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            color = Color.darkTextGrayColor,
            fontSize = 20.ssp,
            fontFamily = RobotoFonts.ROBOTO_BOLD.getFont()
        )

        if (addCloseButton)
            IconButton(onClick = onClose) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_cancel_close_icon),
                    contentDescription = "close",
                    tint = Color.Unspecified
                )
            }
    }

    Spacer(modifier = Modifier.height(5.sdp))

    Divider(thickness = 1.dp, color = Color.darkTextGrayColor.copy(0.10f))

    Spacer(modifier = Modifier.height(10.sdp))
}