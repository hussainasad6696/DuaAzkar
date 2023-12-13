package com.mera.islam.duaazkar.presentation.dua_screen.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.mera.islam.duaazkar.R
import com.mera.islam.duaazkar.ui.theme.RobotoFonts
import com.mera.islam.duaazkar.ui.theme.lightTextGrayColor
import com.mera.islam.duaazkar.ui.theme.primary
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun DuaBottomBar(selectedScreen: DuaBottomNavItems, onItemClick: (DuaBottomNavItems) -> Unit) {
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
        DuaBottomNavItems.entries.filterNot { it == DuaBottomNavItems.None }.forEach {
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
                    painter = painterResource(id = it.icon),
                    contentDescription = null,
                    tint = if (selectedScreen == it) Color.primary else Color.Unspecified,
                    modifier = Modifier.size(18.sdp)
                )

                Spacer(modifier = Modifier.height(4.sdp))

                Text(
                    text = stringResource(it.navName),
                    color = if (selectedScreen == it) Color.primary else Color.lightTextGrayColor,
                    fontFamily = if (selectedScreen == it) RobotoFonts.ROBOTO_BOLD.getFont() else RobotoFonts.ROBOTO_REGULAR.getFont(),
                    fontSize = 10.ssp
                )
            }
        }
    }
}

enum class DuaBottomNavItems(
    @DrawableRes val icon: Int,
    @StringRes val navName: Int
) {
    Categories(
        R.drawable.ic_categories_unselected_icon,
        R.string.categories
    ),
    Display(
        R.drawable.ic_display_icon,
        R.string.display
    ),
    Audio(
        R.drawable.ic_audio_icon,
        R.string.audio
    ),
    Settings(
        R.drawable.ic_settings_icon,
        R.string.settings
    ),
    None(
        R.drawable.ic_bookmark_selected_icon,
        R.string.bookmarks
    )
}