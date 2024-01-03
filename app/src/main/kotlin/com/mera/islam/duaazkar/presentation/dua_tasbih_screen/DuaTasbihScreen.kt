package com.mera.islam.duaazkar.presentation.dua_tasbih_screen

import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mera.islam.duaazkar.R
import com.mera.islam.duaazkar.core.enums.TasbihType
import com.mera.islam.duaazkar.core.presentation.Loading
import com.mera.islam.duaazkar.core.presentation.arabic_with_translation.CustomText
import com.mera.islam.duaazkar.core.presentation.bounceClickable
import com.mera.islam.duaazkar.core.presentation.tasbih_bottom_sheets.TasbihWithBottomSheets
import com.mera.islam.duaazkar.core.substitution.CustomTextModel
import com.mera.islam.duaazkar.core.utils.UiStates
import com.mera.islam.duaazkar.core.utils.fonts.ArabicFonts
import com.mera.islam.duaazkar.ui.theme.RobotoFonts
import com.mera.islam.duaazkar.ui.theme.darkTextGrayColor
import com.mera.islam.duaazkar.ui.theme.transliterationBlurColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DuaTasbihScreen(
    navHostController: NavHostController,
    viewModel: DuaTasbihScreenViewModel = hiltViewModel(),
    duaId: Int = -1
) {

    val localScreenConfig = LocalConfiguration.current.screenHeightDp.dp.value

    TasbihWithBottomSheets(
        savedStateHandle = viewModel.savedStateHandle,
        navHostController = navHostController,
        tasbihType = TasbihType.Dua,
        modifier = Modifier
            .padding(horizontal = 5.sdp)
            .fillMaxSize()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(topStart = 12.sdp, topEnd = 12.sdp)
            ),
        content = {
            val duaById by viewModel.duaWithTranslation.collectAsStateWithLifecycle()

            when (duaById) {
                UiStates.Loading -> Loading(
                    modifier = Modifier.fillMaxSize(),
                    isLoading = true
                )

                is UiStates.Success<CustomTextModel> -> {
                    val dua = (duaById as UiStates.Success).template

                    val textSize by viewModel.customTextStateListener.textSize.collectAsStateWithLifecycle()
                    val arabicFonts by viewModel.customTextStateListener.arabicFont.collectAsStateWithLifecycle()

                    CustomText(
                        modifier = Modifier
                            .padding(10.sdp)
                            .fillMaxWidth()
                            .heightIn(min = 100.sdp, max = (0.4 * localScreenConfig).dp)
                            .verticalScroll(rememberScrollState()),
                        customTextModel = dua,
                        arabicColor = Color.darkTextGrayColor,
                        translationColor = Color.darkTextGrayColor,
                        transliterationColor = Color.transliterationBlurColor,
                        textSize = if (textSize == 0.sp) dua.fontSize() else textSize,
                        arabicFont = ArabicFonts.getLanguageFont(
                            arabicFonts
                        ).getFont()
                    )

                    Divider(
                        thickness = 1.dp,
                        color = Color.darkTextGrayColor.copy(0.10f),
                        modifier = Modifier.padding(10.sdp)
                    )
                }
            }
        }
    )
}

@Composable
fun TasbihView(
    modifier: Modifier = Modifier,
    count: Int?,
    tasbihSoundEnabled: Boolean,
    totalCount: Int?,
    onEditTasbihOptions: () -> Unit,
    onResetTasbihClick: () -> Unit,
    onTasbihClick: () -> Unit,
    topContent: @Composable BoxScope.() -> Unit = {}
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        topContent()

        val player = remember {
            MediaPlayer.create(context, R.raw.click_sound)
        }

        DisposableEffect(key1 = Unit) {
            onDispose { player.release() }
        }

        Box(modifier = Modifier.align(Alignment.Center), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.ic_tasbih_bg),
                contentDescription = "Background"
            )

            Image(
                painter = painterResource(id = R.drawable.ic_tasbih_button),
                contentDescription = "button",
                modifier = Modifier
                    .bounceClickable {
                        if (tasbihSoundEnabled)
                            player.start()
                        onTasbihClick()
                    }
            )

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = count?.toString() ?: "0",
                    fontFamily = RobotoFonts.ROBOTO_BOLD.getFont(),
                    fontSize = 30.ssp,
                    color = Color.White
                )


                Text(
                    text = stringResource(
                        R.string.total_count,
                        totalCount?.toString() ?: "33"
                    ),
                    fontFamily = RobotoFonts.ROBOTO_REGULAR.getFont(),
                    fontSize = 15.ssp,
                    color = Color.White.copy(0.5f)
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(18.sdp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            IconButton(
                onClick = onResetTasbihClick
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_reset_tasbih),
                    contentDescription = "reset tasbih",
                    tint = Color.Unspecified
                )
            }

            IconButton(onClick = onEditTasbihOptions) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_edit_tasbih),
                    contentDescription = "reset tasbih",
                    tint = Color.Unspecified
                )
            }
        }
    }
}
