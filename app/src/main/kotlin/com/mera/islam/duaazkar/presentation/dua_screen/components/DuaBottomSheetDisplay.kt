package com.mera.islam.duaazkar.presentation.dua_screen.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.mera.islam.duaazkar.R
import com.mera.islam.duaazkar.ui.theme.RobotoFonts
import com.smarttoolfactory.slider.ColorfulIconSlider
import com.smarttoolfactory.slider.MaterialSliderDefaults
import com.smarttoolfactory.slider.SliderBrushColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun DuaBottomSheetDisplay(
    onCloseBottomSheet: () -> Unit = {},
    onBrightnessChangeRequest: () -> Unit = {},
    onThemeSelected: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxHeight(0.49f)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.sdp))

        Box(
            modifier = Modifier
                .width(32.sdp)
                .height(4.sdp)
                .background(color = Color(0xffe3e3e3), shape = RoundedCornerShape(3.sdp))
        )

        DuaSettingsNavigationTitle(
            title = stringResource(id = R.string.display),
            onClose = onCloseBottomSheet
        )

        BrightnessSettings(onBrightnessChangeRequest = onBrightnessChangeRequest)

        Spacer(modifier = Modifier.height(10.sdp))

        ThemeSettings(onThemeSelected = onThemeSelected)
    }
}

@Composable
fun ThemeSettings(onThemeSelected: () -> Unit) {
    val themesList = arrayOf(
        Pair(true, R.drawable.ic_white_theme),
        Pair(false, R.drawable.ic_gray_theme),
        Pair(false, R.drawable.ic_skin_theme),
        Pair(false, R.drawable.ic_dark_theme),
    )

    Column(modifier = Modifier.padding(horizontal = 15.sdp)) {
        Text(
            text = stringResource(id = R.string.brightness),
            color = Color(0xff9f9f9f),
            fontSize = 13.ssp,
            fontFamily = RobotoFonts.ROBOTO_REGULAR.getFont()
        )

        Spacer(modifier = Modifier.height(8.sdp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            themesList.forEach {
                Image(
                    painter = painterResource(id = it.second), contentDescription = "Themes",
                    modifier = Modifier
                        .height(81.sdp)
                        .width(62.sdp)
                        .clip(RoundedCornerShape(10.sdp))
                        .padding(horizontal = 3.sdp)
                )
            }
        }
    }
}

@Composable
fun BrightnessSettings(onBrightnessChangeRequest: () -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 15.sdp)) {
        Text(
            text = stringResource(id = R.string.brightness),
            color = Color(0xff9f9f9f),
            fontSize = 13.ssp,
            fontFamily = RobotoFonts.ROBOTO_REGULAR.getFont()
        )

        Spacer(modifier = Modifier.height(8.sdp))

        var progress by remember {
            mutableFloatStateOf(0f)
        }

        ColorfulIconSlider(
            value = progress,
            onValueChange = { value, _ ->
                progress = value
                onBrightnessChangeRequest()
            },
            trackHeight = 34.sdp,
            colors = MaterialSliderDefaults.materialColors(
                activeTrackColor = SliderBrushColor(color = Color(0xff01ad8e)),
                inactiveTrackColor = SliderBrushColor(color = Color(0xfff2f2f2))
            ),
            thumb = {
                val rotationState by animateFloatAsState(
                    targetValue = progress * 360f,
                    animationSpec = tween(easing = LinearEasing),
                    label = ""
                )

                Icon(
                    painter = painterResource(
                        id =
                        when {
                            progress < 0.3 -> R.drawable.ic_brightness_zero
                            progress < 0.7 -> R.drawable.ic_brightness_fifty
                            else -> R.drawable.ic_brightness_hundred
                        }
                    ),
                    contentDescription = "thumb",
                    tint = Color.Unspecified,
                    modifier = Modifier.graphicsLayer {
                        rotationZ = rotationState
                        transformOrigin = TransformOrigin.Center
                    }
                )
            },
            coerceThumbInTrack = false
        )
    }
}
