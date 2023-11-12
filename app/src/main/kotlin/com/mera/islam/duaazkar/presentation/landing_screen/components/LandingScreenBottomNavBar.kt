package com.mera.islam.duaazkar.presentation.landing_screen.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.mera.islam.duaazkar.ui.theme.RobotoFonts
import com.mera.islam.duaazkar.ui.theme.lightTextGrayColor
import com.mera.islam.duaazkar.ui.theme.primary
import ir.kaaveh.sdpcompose.ssp

@Composable
fun LandingScreenBottomNavBar(
    selectedScreen: BottomNavItems,
    onItemClick: (BottomNavItems) -> Unit
) {
    BottomNavigation(backgroundColor = Color.White) {
        BottomNavItems.values().forEach {
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = if (selectedScreen == it) it.selectedIcon else it.unSelectedIcon),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                },
                label = {
                    Text(
                        text = stringResource(it.navName),
                        color = if (selectedScreen == it) primary else lightTextGrayColor,
                        fontFamily = if (selectedScreen == it) RobotoFonts.ROBOTO_BOLD.getFont() else RobotoFonts.ROBOTO_REGULAR.getFont(),
                        fontSize = 16.ssp
                    )
                },
                selected = selectedScreen == it,
                onClick = { onItemClick(it) }
            )
        }
    }
}