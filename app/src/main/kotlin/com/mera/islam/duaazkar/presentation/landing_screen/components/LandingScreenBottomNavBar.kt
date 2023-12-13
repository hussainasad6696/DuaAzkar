package com.mera.islam.duaazkar.presentation.landing_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
    isVerticalNavBar: Boolean = false,
    onItemClick: (BottomNavItems) -> Unit
) {
    if (isVerticalNavBar) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topEndPercent = 50, bottomEndPercent = 50)
                ),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NavItems(selectedScreen = selectedScreen, onItemClick = onItemClick)
        }
    } else
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStartPercent = 20, topEndPercent = 20)
                ),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavItems(selectedScreen = selectedScreen, onItemClick = onItemClick)
        }
}

@Composable
fun NavItems(
    selectedScreen: BottomNavItems,
    isVerticalNavBar: Boolean = false,
    onItemClick: (BottomNavItems) -> Unit
) {
    BottomNavItems.entries.forEach {
        Column(
            modifier = Modifier
                .padding(10.sdp)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple()
                ) { onItemClick(it) },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = if (selectedScreen == it) it.selectedIcon else it.unSelectedIcon),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(if (!isVerticalNavBar) 18.sdp else 15.sdp)
            )

            Spacer(modifier = Modifier.height(4.sdp))

            Text(
                text = stringResource(it.navName),
                color = if (selectedScreen == it) Color.primary else Color.lightTextGrayColor,
                fontFamily = if (selectedScreen == it) RobotoFonts.ROBOTO_BOLD.getFont() else RobotoFonts.ROBOTO_REGULAR.getFont(),
                fontSize = if (!isVerticalNavBar) 10.ssp else 8.ssp
            )
        }
    }
}