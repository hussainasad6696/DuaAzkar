package com.mera.islam.duaazkar.core.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.mera.islam.duaazkar.R
import com.mera.islam.duaazkar.ui.theme.RobotoFonts
import com.mera.islam.duaazkar.ui.theme.darkTextGrayColor
import com.mera.islam.duaazkar.ui.theme.green
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun TitleAndSwitch(modifier: Modifier = Modifier,title: String,isChecked: Boolean = false,onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = Color.darkTextGrayColor,
            fontSize = 16.ssp,
            fontFamily = RobotoFonts.ROBOTO_MEDIUM.getFont()
        )

        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedTrackColor = Color.green,
                checkedThumbColor = Color.White,
                uncheckedTrackColor = Color(0xffdedede),
                uncheckedThumbColor = Color(0xff9d9d9d),
                uncheckedBorderColor = Color(0xff9d9d9d)
            )
        )
    }
}