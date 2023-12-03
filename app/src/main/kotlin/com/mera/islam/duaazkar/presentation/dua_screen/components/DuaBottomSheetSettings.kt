package com.mera.islam.duaazkar.presentation.dua_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.mera.islam.duaazkar.R
import com.mera.islam.duaazkar.core.TEXT_MAX_SIZE
import com.mera.islam.duaazkar.core.TEXT_MIN_SIZE
import com.mera.islam.duaazkar.core.presentation.TitleAndSwitch
import com.mera.islam.duaazkar.core.utils.fonts.ArabicFonts
import com.mera.islam.duaazkar.core.utils.fonts.LeftLangFonts
import com.mera.islam.duaazkar.core.utils.fonts.RightLangFonts
import com.mera.islam.duaazkar.ui.theme.RobotoFonts
import com.mera.islam.duaazkar.ui.theme.darkTextGrayColor
import com.mera.islam.duaazkar.ui.theme.green
import com.mera.islam.duaazkar.ui.theme.lightGrayColor
import com.mera.islam.duaazkar.ui.theme.lightTextGrayColor
import com.smarttoolfactory.slider.MaterialSliderDefaults
import com.smarttoolfactory.slider.SliderBrushColor
import com.smarttoolfactory.slider.SliderWithLabel
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun DuaBottomSheetSettings(
    fontSize: TextUnit = TEXT_MIN_SIZE,
    selectedArabicFont: ArabicFonts = ArabicFonts.AL_QALAM_QURAN,
    isEnglishEnabled: Boolean = true,
    selectedLeftFont: LeftLangFonts = LeftLangFonts.ROBOTO,
    isUrduEnabled: Boolean = false,
    selectedFont: RightLangFonts = RightLangFonts.JAMEEL_NOORI_URDU,
    onUrduToggle: (Boolean) -> Unit = {},
    onUrduFontChanged: (RightLangFonts) -> Unit = {},
    onEnglishToggle: (Boolean) -> Unit = {},
    onEnglishFontChanged: (LeftLangFonts) -> Unit = {},
    onFontSizeChange: (Float) -> Unit = {},
    onCloseBottomSheet: () -> Unit = {},
    onArabicFontChange: (ArabicFonts) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxHeight(0.7f)
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
            title = stringResource(id = R.string.settings),
            onClose = onCloseBottomSheet
        )

        FontSizeSetting(fontSize = fontSize, onFontSizeChange = onFontSizeChange)

        Spacer(modifier = Modifier.height(5.sdp))

        ArabicFontsSetting(
            selectedFont = selectedArabicFont,
            onArabicFontChange = onArabicFontChange
        )

        Spacer(modifier = Modifier.height(5.sdp))

        EnglishTranslationsSetting(
            isEnglishEnabled = isEnglishEnabled,
            selectedFont = selectedLeftFont,
            onEnglishToggle = onEnglishToggle,
            onEnglishFontChanged = onEnglishFontChanged
        )

        Spacer(modifier = Modifier.height(5.sdp))

        UrduTranslationsSetting(
            isUrduEnabled = isUrduEnabled,
            selectedFont = selectedFont,
            onUrduToggle = onUrduToggle,
            onUrduFontChanged = onUrduFontChanged
        )
    }
}

@Composable
fun UrduTranslationsSetting(
    isUrduEnabled: Boolean,
    selectedFont: RightLangFonts,
    onUrduToggle: (Boolean) -> Unit,
    onUrduFontChanged: (RightLangFonts) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.sdp)
    ) {
        TitleAndSwitch(
            title = stringResource(id = R.string.urdu_translation),
            isChecked = isUrduEnabled,
            onCheckedChange = onUrduToggle
        )

        Spacer(modifier = Modifier.height(10.sdp))

        AnimatedVisibility(visible = isUrduEnabled) {
            ItemsAndSelection(
                selected = selectedFont,
                list = RightLangFonts.entries,
                onItemSelected = onUrduFontChanged,
                name = { it.name.replace("_", " ").lowercase() }
            )

            Spacer(modifier = Modifier.height(10.sdp))
        }
    }
}

@Composable
fun EnglishTranslationsSetting(
    isEnglishEnabled: Boolean,
    selectedFont: LeftLangFonts,
    onEnglishToggle: (Boolean) -> Unit,
    onEnglishFontChanged: (LeftLangFonts) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.sdp)
    ) {
        TitleAndSwitch(
            title = stringResource(id = R.string.english_translation),
            isChecked = isEnglishEnabled,
            onCheckedChange = onEnglishToggle
        )

        Spacer(modifier = Modifier.height(10.sdp))

        AnimatedVisibility(visible = isEnglishEnabled) {
            ItemsAndSelection(
                selected = selectedFont,
                list = LeftLangFonts.entries,
                onItemSelected = onEnglishFontChanged,
                name = { it.name.replace("_", " ").lowercase() }
            )

            Spacer(modifier = Modifier.height(10.sdp))
        }

        Divider(thickness = 1.dp, color = darkTextGrayColor.copy(0.10f))
    }
}

@Composable
fun ArabicFontsSetting(selectedFont: ArabicFonts, onArabicFontChange: (ArabicFonts) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.sdp)
    ) {
        Text(
            text = stringResource(id = R.string.arabic_fonts),
            color = Color(0xff9f9f9f),
            fontSize = 13.ssp,
            fontFamily = RobotoFonts.ROBOTO_REGULAR.getFont()
        )

        Spacer(modifier = Modifier.height(10.sdp))

        ItemsAndSelection(
            selected = selectedFont,
            list = ArabicFonts.entries,
            onItemSelected = onArabicFontChange,
            name = { it.name.replace("_", " ").lowercase() }
        )

        Spacer(modifier = Modifier.height(10.sdp))

        Divider(thickness = 1.dp, color = darkTextGrayColor.copy(0.10f))
    }
}

@Composable
private fun <T> ItemsAndSelection(
    selected: T,
    list: List<T>,
    onItemSelected: (T) -> Unit,
    name: (T) -> String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
    ) {
        list.forEach {
            TextButton(
                onClick = {
                    onItemSelected(it)
                },
                shape = RoundedCornerShape(10.sdp),
                border = BorderStroke(
                    width = 1.dp,
                    color = if (selected == it) green else lightGrayColor
                ),
                content = {
                    Text(
                        text = name(it),
                        color = if (selected == it) green else Color(0xff5c5c5c),
                        fontFamily = RobotoFonts.ROBOTO_REGULAR.getFont(),
                        fontSize = 13.ssp
                    )
                },
                modifier = Modifier.padding(horizontal = 3.sdp)
            )
        }
    }
}

@Composable
fun FontSizeSetting(fontSize: TextUnit, onFontSizeChange: (Float) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.sdp)
    ) {
        Text(
            text = stringResource(id = R.string.font_size),
            color = Color(0xff9f9f9f),
            fontSize = 13.ssp,
            fontFamily = RobotoFonts.ROBOTO_REGULAR.getFont()
        )

        Spacer(modifier = Modifier.height(10.sdp))

        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.padding(bottom = 10.sdp)
        ) {
            Text(
                text = "-Aa",
                fontFamily = RobotoFonts.ROBOTO_REGULAR.getFont(),
                color = lightTextGrayColor,
                fontSize = 12.ssp,
                modifier = Modifier.padding(bottom = 10.sdp)
            )

            Spacer(modifier = Modifier.width(10.sdp))

            SliderWithLabel(
                value = fontSize.value,
                onValueChange = onFontSizeChange,
                trackHeight = 34.sdp,
                colors = MaterialSliderDefaults.materialColors(
                    activeTrackColor = SliderBrushColor(color = green),
                    inactiveTrackColor = SliderBrushColor(color = lightGrayColor)
                ),
                coerceThumbInTrack = false,
                label = {
                    Text(
                        text = "${fontSize.value.toInt()}",
                        modifier = Modifier
                            .shadow(1.dp, shape = RoundedCornerShape(7.sdp))
                            .background(green, shape = RoundedCornerShape(7.sdp))
                            .padding(vertical = 3.sdp, horizontal = 10.sdp),
                        color = Color.White
                    )
                },
                modifier = Modifier.fillMaxWidth(0.85f),
                valueRange = TEXT_MIN_SIZE.value..TEXT_MAX_SIZE.value
            )

            Spacer(modifier = Modifier.width(10.sdp))

            Text(
                text = "+Aa",
                fontFamily = RobotoFonts.ROBOTO_MEDIUM.getFont(),
                color = lightTextGrayColor,
                fontSize = 14.ssp,
                modifier = Modifier.padding(bottom = 10.sdp)
            )
        }

        Divider(thickness = 1.dp, color = darkTextGrayColor.copy(0.10f))
    }
}
