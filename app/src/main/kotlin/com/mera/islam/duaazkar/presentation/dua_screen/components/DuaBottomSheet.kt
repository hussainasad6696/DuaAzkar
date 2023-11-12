package  com.mera.islam.duaazkar.presentation.dua_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import  com.mera.islam.duaazkar.R
import  com.mera.islam.duaazkar.core.TEXT_MAX_SIZE
import  com.mera.islam.duaazkar.core.TEXT_MIN_SIZE
import  com.mera.islam.duaazkar.core.utils.fonts.ArabicFonts
import  com.mera.islam.duaazkar.core.utils.fonts.LeftLangFonts
import  com.mera.islam.duaazkar.core.utils.fonts.RightLangFonts
import  com.mera.islam.duaazkar.presentation.dua_screen.DuaScreenViewModel
import  com.mera.islam.duaazkar.presentation.dua_screen.UserEvent
import ir.kaaveh.sdpcompose.sdp

@Composable
fun DuaBottomSheet(viewModel: DuaScreenViewModel) {
    Column(
        modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Translations(viewModel)

        SizeChange(viewModel)

        ArabicFonts(viewModel)

        TranslationLanguageFonts(viewModel)
    }
}

@Composable
fun TranslationLanguageFonts(viewModel: DuaScreenViewModel) {
    Column {
        Text(text = stringResource(R.string.left_lang_fonts), color = Color.Black)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            LeftLangFonts.values().forEach {
                TextButton(
                    onClick = { viewModel.onUserEvent(UserEvent.SelectedFont(it)) },
                    content = {
                        Text(
                            text = it.name,
                            color = Color.Black
                        )
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(5.sdp))
        Text(text = stringResource(R.string.right_lang_fonts), color = Color.Black)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            RightLangFonts.values().forEach {
                TextButton(
                    onClick = { viewModel.onUserEvent(UserEvent.SelectedFont(it)) },
                    content = {
                        Text(
                            text = it.name,
                            color = Color.Black
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun ArabicFonts(viewModel: DuaScreenViewModel) {
    Column {
        Text(text = stringResource(R.string.arabic_fonts), color = Color.Black)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            ArabicFonts.values().forEach {
                TextButton(
                    onClick = { viewModel.onUserEvent(UserEvent.SelectedFont(it)) },
                    content = {
                        Text(
                            text = it.name,
                            color = Color.Black
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun SizeChange(viewModel: DuaScreenViewModel) {
    val textSize by viewModel.duaTextSize.collectAsStateWithLifecycle()
    Slider(
        value = textSize.value,
        onValueChange = { viewModel.onUserEvent(UserEvent.TextSizeChanged(it)) },
        valueRange = TEXT_MIN_SIZE.value..TEXT_MAX_SIZE.value,
        modifier = Modifier.padding(horizontal = 10.sdp)
    )
}

@Composable
fun Translations(viewModel: DuaScreenViewModel) {
    val translators by viewModel.translators.collectAsStateWithLifecycle()

    Row(modifier = Modifier.padding(10.sdp)) {
        translators.forEach {
            TextButton(
                onClick = { viewModel.onUserEvent(UserEvent.TranslationsOptionsChanged(it.duaTranslatorModel.id)) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (it.isSelected) Color.Green.copy(0.5f) else
                        Color.Blue.copy(0.5f)
                ),
                content = {
                    Text(
                        text = "${it.duaTranslatorModel.languageCode}-${it.duaTranslatorModel.languageName}",
                        color = Color.Black
                    )
                }
            )

            Spacer(modifier = Modifier.width(5.sdp))
        }
    }
}
