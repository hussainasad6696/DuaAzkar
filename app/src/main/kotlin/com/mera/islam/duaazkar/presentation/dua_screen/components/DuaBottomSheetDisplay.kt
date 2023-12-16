package com.mera.islam.duaazkar.presentation.dua_screen.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mera.islam.duaazkar.R
import com.mera.islam.duaazkar.core.presentation.TitleAndSwitch
import com.mera.islam.duaazkar.core.presentation.custom_bottom_sheet.BottomSheetSettingsNavigationTitle
import com.mera.islam.duaazkar.core.presentation.custom_bottom_sheet.CustomBottomSheet
import com.mera.islam.duaazkar.ui.theme.RobotoFonts
import com.mera.islam.duaazkar.ui.theme.darkTextGrayColor
import com.mera.islam.duaazkar.ui.theme.gray9fColor
import com.mera.islam.duaazkar.ui.theme.green
import com.mera.islam.duaazkar.ui.theme.lightGrayColor
import com.smarttoolfactory.slider.ColorfulIconSlider
import com.smarttoolfactory.slider.MaterialSliderDefaults
import com.smarttoolfactory.slider.SliderBrushColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun DuaBottomSheetDisplay(
    currentBrightness: Float = 0f,
    selectedTheme: Int,
    isScreenCheckOn: Boolean = false,
    onCloseBottomSheet: () -> Unit = {},
    onBrightnessChangeRequest: (Float) -> Unit = {},
    onThemeSelected: (Int) -> Unit = {},
    keepScreenOn: (Boolean) -> Unit = {}
) {
    CustomBottomSheet(onCloseBottomSheet = onCloseBottomSheet) {
        BrightnessSettings(
            currentBrightness = currentBrightness,
            onBrightnessChangeRequest = onBrightnessChangeRequest
        )

        Spacer(modifier = Modifier.height(10.sdp))

        ThemeSettings(selectedTheme = selectedTheme, onThemeSelected = onThemeSelected)

        Spacer(modifier = Modifier.height(10.sdp))

        TitleAndSwitch(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.sdp),
            title = stringResource(id = R.string.keep_screen_on),
            isChecked = isScreenCheckOn,
            onCheckedChange = keepScreenOn
        )

        Spacer(modifier = Modifier.height(10.sdp))
    }
}

@Composable
fun ThemeSettings(selectedTheme: Int, onThemeSelected: (Int) -> Unit) {
    val themesList = arrayOf(
        R.drawable.ic_white_theme,
        R.drawable.ic_gray_theme,
        R.drawable.ic_skin_theme,
        R.drawable.ic_dark_theme
    )

    Column(modifier = Modifier.padding(horizontal = 10.sdp)) {
        Text(
            text = stringResource(id = R.string.themes),
            color = Color.gray9fColor,
            fontSize = 13.ssp,
            fontFamily = RobotoFonts.ROBOTO_REGULAR.getFont()
        )

        Spacer(modifier = Modifier.height(8.sdp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            themesList.forEach {
                Image(
                    painter = painterResource(id = it), contentDescription = "Themes",
                    modifier = Modifier
                        .height(81.sdp)
                        .width(62.sdp)
                        .clip(RoundedCornerShape(10.sdp))
                        .border(
                            width = if (selectedTheme == it) 1.dp else 0.5.dp,
                            color = if (selectedTheme == it) Color.green else Color.darkTextGrayColor.copy(0.3f),
                            shape = RoundedCornerShape(10.sdp)
                        )
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = rememberRipple(
                                radius = 16.sdp
                            )
                        ) { onThemeSelected(it) },
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun BrightnessSettings(currentBrightness: Float, onBrightnessChangeRequest: (Float) -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 15.sdp)) {
        Text(
            text = stringResource(id = R.string.brightness),
            color = Color.gray9fColor,
            fontSize = 13.ssp,
            fontFamily = RobotoFonts.ROBOTO_REGULAR.getFont()
        )

        Spacer(modifier = Modifier.height(8.sdp))

        ColorfulIconSlider(
            value = currentBrightness,
            onValueChange = { value, _ ->
                onBrightnessChangeRequest(value)
            },
            trackHeight = 34.sdp,
            colors = MaterialSliderDefaults.materialColors(
                activeTrackColor = SliderBrushColor(color = Color.green),
                inactiveTrackColor = SliderBrushColor(color = Color.lightGrayColor)
            ),
            thumb = {
                val rotationState by animateFloatAsState(
                    targetValue = (currentBrightness / 255f) * 360f,
                    animationSpec = tween(easing = LinearEasing),
                    label = ""
                )

                Icon(
                    painter = painterResource(
                        id =
                        when {
                            currentBrightness < 76.5 -> R.drawable.ic_brightness_zero
                            currentBrightness < 178.5 -> R.drawable.ic_brightness_fifty
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
            valueRange = 0f..255f,
            coerceThumbInTrack = false
        )
    }
}