package com.mera.islam.duaazkar.presentation.dua_tasboh_screen

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mera.islam.duaazkar.R
import com.mera.islam.duaazkar.core.presentation.DefaultTopAppBar
import com.mera.islam.duaazkar.core.presentation.DuaAzkarWithBackground
import com.mera.islam.duaazkar.core.presentation.Loading
import com.mera.islam.duaazkar.core.presentation.arabic_with_translation.CustomText
import com.mera.islam.duaazkar.core.presentation.bounceClickable
import com.mera.islam.duaazkar.core.substitution.ArabicModelWithTranslationModel
import com.mera.islam.duaazkar.core.utils.EventResources
import com.mera.islam.duaazkar.core.utils.fonts.ArabicFonts
import com.mera.islam.duaazkar.ui.theme.darkTextGrayColor
import com.mera.islam.duaazkar.ui.theme.transliterationBlurColor
import ir.kaaveh.sdpcompose.sdp

@Composable
fun DuaTasbihScreen(
    navHostController: NavHostController,
    viewModel: DuaTasbihScreenViewModel = hiltViewModel(),
    duaId: Int = -1
) {

    val localScreenConfig = LocalConfiguration.current.screenHeightDp.dp.value

    viewModel.setDuaId(duaId)

    DuaAzkarWithBackground(
        addScaffolding = true,
        topBar = {
            DefaultTopAppBar(
                title = "Tasbih",
                navHostController = navHostController,
                hasSearch = false
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 5.sdp)
                .fillMaxSize()
                .padding(it)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 12.sdp, topEnd = 12.sdp)
                )
        ) {
            val duaById by viewModel.duaWithTranslation.collectAsStateWithLifecycle()

            when (duaById) {
                EventResources.Loading -> Loading(
                    modifier = Modifier.fillMaxSize(),
                    isLoading = true
                )

                is EventResources.Success<ArabicModelWithTranslationModel> -> {
                    val dua = (duaById as EventResources.Success).template

                    val textSize by viewModel.arabicWithTranslationStateListener.textSize.collectAsStateWithLifecycle()
                    val arabicFonts by viewModel.arabicWithTranslationStateListener.arabicFont.collectAsStateWithLifecycle()

                    CustomText(
                        modifier = Modifier
                            .padding(10.sdp)
                            .fillMaxWidth()
                            .heightIn(min = 100.sdp, max = (0.4 * localScreenConfig).dp)
                            .verticalScroll(rememberScrollState()),
                        arabicModelWithTranslationModel = dua,
                        arabicColor = darkTextGrayColor,
                        translationColor = darkTextGrayColor,
                        transliterationColor = transliterationBlurColor,
                        textSize = textSize,
                        arabicFont = ArabicFonts.getLanguageFont(
                            arabicFonts
                        ).getFont()
                    )

                    Divider(
                        thickness = 1.dp,
                        color = darkTextGrayColor.copy(0.10f),
                        modifier = Modifier.padding(10.sdp)
                    )

                    Spacer(modifier = Modifier.height(10.sdp))

                    Box(modifier = Modifier.fillMaxSize()) {

                        Image(
                            painter = painterResource(id = R.drawable.ic_tasbih_bg),
                            contentDescription = "Background",
                            modifier = Modifier.align(Alignment.Center)
                        )

                        Image(
                            painter = painterResource(id = R.drawable.ic_tasbih_button),
                            contentDescription = "button",
                            modifier = Modifier.align(Alignment.Center).bounceClickable {  }
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_reset_tasbih),
                                    contentDescription = "reset tasbih",
                                    tint = Color.Unspecified
                                )
                            }

                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_edit_tasbih),
                                    contentDescription = "reset tasbih",
                                    tint = Color.Unspecified
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}