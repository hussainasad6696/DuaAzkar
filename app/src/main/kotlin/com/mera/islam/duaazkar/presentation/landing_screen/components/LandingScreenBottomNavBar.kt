package com.mera.islam.duaazkar.presentation.landing_screen.components

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.mera.islam.duaazkar.ui.theme.RobotoFonts
import com.mera.islam.duaazkar.ui.theme.lightTextGrayColor
import com.mera.islam.duaazkar.ui.theme.primary
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun LandingScreenBottomNavBar(
    selectedScreen: BottomNavItems,
    onItemClick: (BottomNavItems) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.sdp, vertical = 5.sdp)
            .background(color = Color.White, shape = RoundedCornerShape(50)),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavItems.entries.forEach {
            Column(
                modifier = Modifier.padding(10.sdp).clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple()
                ) { onItemClick(it) },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = if (selectedScreen == it) it.selectedIcon else it.unSelectedIcon),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(20.sdp)
                )

                Spacer(modifier = Modifier.height(4.sdp))

                Text(
                    text = stringResource(it.navName),
                    color = if (selectedScreen == it) primary else lightTextGrayColor,
                    fontFamily = if (selectedScreen == it) RobotoFonts.ROBOTO_BOLD.getFont() else RobotoFonts.ROBOTO_REGULAR.getFont(),
                    fontSize = 12.ssp
                )
            }
        }
    }
}