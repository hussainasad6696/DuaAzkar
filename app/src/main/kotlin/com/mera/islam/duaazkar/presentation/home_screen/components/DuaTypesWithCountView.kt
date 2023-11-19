package com.mera.islam.duaazkar.presentation.home_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import com.mera.islam.duaazkar.R
import com.mera.islam.duaazkar.domain.models.dua.DuaType
import com.mera.islam.duaazkar.ui.theme.RobotoFonts
import com.mera.islam.duaazkar.ui.theme.darkTextGrayColor
import com.mera.islam.duaazkar.ui.theme.lightTextGrayColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun DuaTypesWithCountView(
    modifier: Modifier = Modifier,
    duaType: DuaType,
    noOfDua: Int,
    onNextClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(35))
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple()
            ) { onNextClick() }
    ) {
        Spacer(modifier = Modifier.height(12.sdp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(modifier = Modifier.weight(1f)) {
                Spacer(modifier = Modifier.width(10.sdp))
                Icon(
                    painter = painterResource(id = duaType.icon),
                    contentDescription = "DuaType icon",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(40.sdp)
                )
                Spacer(modifier = Modifier.width(10.sdp))
                Column {
                    Text(
                        text = duaType.getName(),
                        color = darkTextGrayColor,
                        fontSize = 13.ssp,
                        fontFamily = RobotoFonts.ROBOTO_MEDIUM.getFont(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(3.sdp))

                    Text(
                        text = stringResource(id = R.string.chapters, noOfDua),
                        color = lightTextGrayColor,
                        fontSize = 11.ssp,
                        fontFamily = RobotoFonts.ROBOTO_REGULAR.getFont()
                    )
                }
            }

            IconButton(onClick = onNextClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_next_icon),
                    contentDescription = "Next",
                    tint = Color.Unspecified
                )
            }
        }
        Spacer(modifier = Modifier.height(12.sdp))
    }
}