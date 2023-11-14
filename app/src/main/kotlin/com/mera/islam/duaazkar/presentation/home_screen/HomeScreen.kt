package com.mera.islam.duaazkar.presentation.home_screen

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mera.islam.duaazkar.NavControllerRoutes
import com.mera.islam.duaazkar.R
import com.mera.islam.duaazkar.core.presentation.Loading
import com.mera.islam.duaazkar.core.utils.LoadingResources
import com.mera.islam.duaazkar.domain.models.DuaType
import com.mera.islam.duaazkar.presentation.home_screen.components.DuaTypesWithCountView
import com.mera.islam.duaazkar.presentation.landing_screen.LandingScreenViewModel
import com.mera.islam.duaazkar.presentation.home_screen.components.LandingScreenTopSelection
import com.mera.islam.duaazkar.ui.theme.RobotoFonts
import com.mera.islam.duaazkar.ui.theme.lightTextGrayColor
import com.mera.islam.duaazkar.ui.theme.primary
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: LandingScreenViewModel,
    onViewAllClick: () -> Unit,
    navController: NavHostController
) {
    val allDuaTypes by viewModel.duaTypeWithCount.collectAsStateWithLifecycle()

    when (allDuaTypes) {
        LoadingResources.Loading -> Loading(modifier = modifier.fillMaxSize())
        is LoadingResources.SuccessList -> {
            val data = (allDuaTypes as LoadingResources.SuccessList).data

            Column(modifier = modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.height(10.sdp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.sdp)
                        .horizontalScroll(rememberScrollState())
                ) {
                    LandingScreenTopSelection(
                        modifier = Modifier.padding(end = 5.sdp),
                        name = R.string.morning_azkar,
                        resource = R.drawable.ic_morning_azkar,
                        noOfItems = data.firstOrNull { it.getDuaType() == DuaType.Morning_Evening_Night }?.count
                            ?: 0,
                        onItemClick = {
                            navController.navigate(
                                NavControllerRoutes.DUA_SCREEN(duaType = DuaType.Morning_Evening_Night)
                                    .getPathWithNavArgs()
                            )
                        }
                    )

                    LandingScreenTopSelection(
                        modifier = Modifier.padding(end = 5.sdp),
                        name = R.string.ibadat_azkar,
                        resource = R.drawable.ic_evening_azkar,
                        noOfItems = data.firstOrNull { it.getDuaType() == DuaType.Ibadah }?.count ?: 0,
                        onItemClick = {
                            navController.navigate(
                                NavControllerRoutes.DUA_SCREEN(duaType = DuaType.Ibadah)
                                    .getPathWithNavArgs()
                            )
                        }
                    )
                }

                Spacer(modifier = Modifier.height(10.sdp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.sdp, end = 5.sdp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.duas),
                        color = lightTextGrayColor,
                        fontSize = 11.ssp,
                        fontFamily = RobotoFonts.ROBOTO_REGULAR.getFont()
                    )

                    TextButton(onClick = onViewAllClick) {
                        Text(
                            text = stringResource(R.string.view_all),
                            color = primary,
                            fontSize = 11.ssp,
                            fontFamily = RobotoFonts.ROBOTO_MEDIUM.getFont()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.sdp))

                runCatching { data.subList(0, 3) }.getOrNull()?.let {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.sdp),
                        verticalArrangement = Arrangement.spacedBy(10.sdp)
                    ) {
                        List(it.size) { index ->
                            val dua = it[index]

                            DuaTypesWithCountView(
                                duaType = dua.getDuaType(),
                                noOfDua = dua.count,
                                onNextClick = {
                                    navController.navigate(
                                        NavControllerRoutes.DUA_LISTING_SCREEN(
                                            duaListArray = dua.getIdList().toIntArray()
                                        ).getPathWithNavArgs()
                                    )
                                })
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun Buttons() {
    TextButton(onClick = { /*TODO*/ }) {
        Text(
            text = stringResource(R.string.view_all),
            color = primary,
            fontSize = 13.ssp,
            fontFamily = RobotoFonts.ROBOTO_MEDIUM.getFont()
        )
    }
}