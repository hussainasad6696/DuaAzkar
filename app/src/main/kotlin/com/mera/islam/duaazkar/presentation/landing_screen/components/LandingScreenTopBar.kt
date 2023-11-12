package  com.mera.islam.duaazkar.presentation.landing_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import  com.mera.islam.duaazkar.R
import  com.mera.islam.duaazkar.ui.theme.RobotoFonts
import  com.mera.islam.duaazkar.ui.theme.darkTextGrayColor
import  com.mera.islam.duaazkar.ui.theme.lightTextGrayColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun LandingScreenTopBar(modifier: Modifier = Modifier,onSettingsClick: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 5.sdp, horizontal = 10.sdp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(R.string.as_salamu_alaykum),
                color = lightTextGrayColor,
                fontSize = 10.ssp,
                fontFamily = RobotoFonts.ROBOTO_REGULAR.getFont()
            )
            Text(
                text = stringResource(R.string.dua_azkar),
                color = darkTextGrayColor,
                fontSize = 24.ssp,
                fontFamily = RobotoFonts.ROBOTO_BOLD.getFont()
            )
        }

        IconButton(onClick = onSettingsClick) {
            Icon(
                painter = painterResource(id = R.drawable.ic_menu_icon),
                tint = Color.Unspecified,
                modifier = Modifier.size(40.sdp),
                contentDescription = "Settings icon"
            )
        }
    }
}