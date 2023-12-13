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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mera.islam.duaazkar.R
import com.mera.islam.duaazkar.core.TEXT_MAX_SIZE
import com.mera.islam.duaazkar.core.TEXT_MIN_SIZE
import com.mera.islam.duaazkar.core.enums.LanguageDirection
import com.mera.islam.duaazkar.core.presentation.TitleAndSwitch
import com.mera.islam.duaazkar.core.presentation.custom_bottom_sheet.BottomSheetSettingsNavigationTitle
import com.mera.islam.duaazkar.core.presentation.custom_bottom_sheet.CustomBottomSheet
import com.mera.islam.duaazkar.core.utils.fonts.ArabicFonts
import com.mera.islam.duaazkar.core.utils.fonts.LanguageFonts
import com.mera.islam.duaazkar.presentation.dua_screen.DuaScreenViewModel
import com.mera.islam.duaazkar.presentation.dua_screen.DuaTranslatorModelWithSelection
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
    viewModel: DuaScreenViewModel,
    fontSize: TextUnit = TEXT_MIN_SIZE,
    selectedArabicFont: ArabicFonts = ArabicFonts.AL_QALAM_QURAN,
    duaTranslatorOptions: List<DuaTranslatorModelWithSelection> = emptyList(),
    onTranslationToggle: (Int, Boolean) -> Unit = { _, _ -> },
    onTranslationFontChanged: (LanguageFonts) -> Unit = {},
    onFontSizeChange: (Float) -> Unit = {},
    onCloseBottomSheet: () -> Unit = {},
    onArabicFontChange: (ArabicFonts) -> Unit = {}
) {
    CustomBottomSheet(
        title = stringResource(id = R.string.settings),
        onCloseBottomSheet = onCloseBottomSheet
    ) {
        FontSizeSetting(fontSize = fontSize, onFontSizeChange = onFontSizeChange)

        Spacer(modifier = Modifier.height(10.sdp))

        ArabicFontsSetting(
            selectedFont = selectedArabicFont,
            onArabicFontChange = onArabicFontChange
        )

        Spacer(modifier = Modifier.height(10.sdp))

        duaTranslatorOptions.forEach { duaTranslator ->
            val font by when (duaTranslator.duaTranslatorModel.languageDirection) {
                LanguageDirection.LEFT -> viewModel.leftFont
                LanguageDirection.RIGHT -> viewModel.rightFont
            }.collectAsStateWithLifecycle()

            TranslationsSetting(
                title = stringResource(
                    id = R.string.translation,
                    when (duaTranslator.duaTranslatorModel.languageDirection) {
                        LanguageDirection.LEFT -> stringResource(R.string.left)
                        LanguageDirection.RIGHT -> stringResource(R.string.right)
                    }
                ),
                isTranslationEnabled = duaTranslator.isSelected,
                selectedFont = duaTranslator.getLanguageFont(
                    languageDirection = duaTranslator.duaTranslatorModel.languageDirection,
                    fontId = font
                ),
                onTranslationToggle = {
                    onTranslationToggle(
                        duaTranslator.duaTranslatorModel.id,
                        it
                    )
                },
                onTranslationFontChanged = onTranslationFontChanged
            )

            Spacer(modifier = Modifier.height(10.sdp))
        }
    }
}

@Composable
fun TranslationsSetting(
    title: String,
    isTranslationEnabled: Boolean,
    selectedFont: LanguageFonts,
    onTranslationToggle: (Boolean) -> Unit,
    onTranslationFontChanged: (LanguageFonts) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.sdp)
    ) {
        TitleAndSwitch(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.sdp),
            title = title,
            isChecked = isTranslationEnabled,
            onCheckedChange = onTranslationToggle
        )

        Spacer(modifier = Modifier.height(10.sdp))

        AnimatedVisibility(visible = isTranslationEnabled) {
            ItemsAndSelection(
                selected = selectedFont,
                list = selectedFont.getFontsList(),
                onItemSelected = onTranslationFontChanged,
                name = { it.getName() }
            )

            Spacer(modifier = Modifier.height(10.sdp))
        }

        Spacer(modifier = Modifier.height(10.sdp))

        Divider(thickness = 1.dp, color = Color.darkTextGrayColor.copy(0.10f))
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

        Divider(thickness = 1.dp, color = Color.darkTextGrayColor.copy(0.10f))
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
                    color = if (selected == it) Color.green else Color.lightGrayColor
                ),
                content = {
                    Text(
                        text = name(it),
                        color = if (selected == it) Color.green else Color(0xff5c5c5c),
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
                color = Color.lightTextGrayColor,
                fontSize = 12.ssp,
                modifier = Modifier.padding(bottom = 10.sdp)
            )

            Spacer(modifier = Modifier.width(10.sdp))

            SliderWithLabel(
                value = fontSize.value,
                onValueChange = onFontSizeChange,
                trackHeight = 34.sdp,
                colors = MaterialSliderDefaults.materialColors(
                    activeTrackColor = SliderBrushColor(color = Color.green),
                    inactiveTrackColor = SliderBrushColor(color = Color.lightGrayColor)
                ),
                coerceThumbInTrack = false,
                label = {
                    Text(
                        text = "${fontSize.value.toInt()}",
                        modifier = Modifier
                            .shadow(1.dp, shape = RoundedCornerShape(7.sdp))
                            .background(Color.green, shape = RoundedCornerShape(7.sdp))
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
                color = Color.lightTextGrayColor,
                fontSize = 14.ssp,
                modifier = Modifier.padding(bottom = 10.sdp)
            )
        }

        Divider(thickness = 1.dp, color = Color.darkTextGrayColor.copy(0.10f))
    }
}
