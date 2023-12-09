package com.mera.islam.duaazkar.presentation.home_screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mera.islam.duaazkar.NavControllerRoutes
import com.mera.islam.duaazkar.R
import com.mera.islam.duaazkar.core.presentation.Loading
import com.mera.islam.duaazkar.core.presentation.springEffect
import com.mera.islam.duaazkar.core.utils.EventResources
import com.mera.islam.duaazkar.data.local.dao.dua.DuaNameAndCount
import com.mera.islam.duaazkar.domain.models.dua.DuaType
import com.mera.islam.duaazkar.presentation.home_screen.components.DuaTypesWithCountView
import com.mera.islam.duaazkar.presentation.home_screen.components.LandingScreenTopSelection
import com.mera.islam.duaazkar.presentation.landing_screen.LandingScreenViewModel
import com.mera.islam.duaazkar.ui.theme.RobotoFonts
import com.mera.islam.duaazkar.ui.theme.lightTextGrayColor
import com.mera.islam.duaazkar.ui.theme.primary
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: LandingScreenViewModel,
    onViewAllClick: () -> Unit,
    navController: NavHostController,
    isLandscape: Boolean = false
) {
    val allDuaTypes by viewModel.duaTypeWithCount.collectAsStateWithLifecycle()

    when (allDuaTypes) {
        EventResources.Loading -> Loading(modifier = modifier.fillMaxSize())
        is EventResources.Success -> {
            val list = (allDuaTypes as EventResources.Success).template

            if (!isLandscape) {
                val localScreenConfig = LocalConfiguration.current.screenWidthDp.dp.value

                val seventyPercentWidth = (0.6 * localScreenConfig).dp
                val thirtyPercentWidth = (0.4 * localScreenConfig).dp

                LazyColumn(
                    modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(5.sdp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(10.sdp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.sdp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            MorningEveningAzkar(
                                data = list,
                                navController = navController,
                                seventyPercentWidth = seventyPercentWidth
                            )

                            Spacer(modifier = Modifier.width(5.sdp))

                            Tasbih(
                                data = list,
                                navController = navController,
                                thirtyPercentWidth = thirtyPercentWidth
                            )
                        }

                        Spacer(modifier = Modifier.height(5.sdp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.sdp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            LastRead(
                                viewModel = viewModel,
                                navController = navController,
                                thirtyPercentWidth = thirtyPercentWidth
                            )

                            Spacer(modifier = Modifier.width(5.sdp))

                            AsmaulHusna(
                                navController = navController,
                                seventyPercentWidth = seventyPercentWidth
                            )
                        }
                    }

                    item {
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
                    }

                    runCatching {
                        list.filterNot { it.getDuaType() == DuaType.Morning_Evening_Night }
                            .subList(0, 5)
                    }.getOrNull()?.let {
                        items(it.size) { index ->
                            val dua = it[index]

                            DuaTypesWithCountView(
                                modifier = Modifier.padding(horizontal = 10.sdp)
                                    .animateItemPlacement(
                                    animationSpec = tween(durationMillis = 1000)
                                ),
                                duaType = dua.getDuaType(),
                                noOfDua = dua.count,
                                onNextClick = {
                                    navController.navigate(
                                        NavControllerRoutes.DUA_LISTING_SCREEN(
                                            duaListArray = dua.getIdList().toIntArray()
                                        ).getPathWithNavArgs()
                                    )
                                })

                            if (index == list.lastIndex)
                                Spacer(modifier = Modifier.height(15.sdp))
                        }
                    }
                }
            } else {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                    ) {
                        MorningEveningAzkar(
                            data = list,
                            navController = navController
                        )

                        Spacer(modifier = Modifier.height(5.sdp))

                        Tasbih(
                            data = list,
                            navController = navController
                        )

                        Spacer(modifier = Modifier.height(5.sdp))

                        LastRead(
                            viewModel = viewModel,
                            navController = navController
                        )

                        Spacer(modifier = Modifier.height(5.sdp))

                        AsmaulHusna(
                            navController = navController
                        )
                    }

                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(5.sdp)
                    ) {
                        runCatching { list.subList(0, 5) }.getOrNull()?.let {
                            items(it.size) { index ->
                                val dua = it[index]

                                DuaTypesWithCountView(
                                    modifier = Modifier.padding(horizontal = 10.sdp),
                                    duaType = dua.getDuaType(),
                                    noOfDua = dua.count,
                                    onNextClick = {
                                        navController.navigate(
                                            NavControllerRoutes.DUA_LISTING_SCREEN(
                                                duaListArray = dua.getIdList().toIntArray()
                                            ).getPathWithNavArgs()
                                        )
                                    })

                                if (index == list.lastIndex)
                                    Spacer(modifier = Modifier.height(15.sdp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MorningEveningAzkar(
    seventyPercentWidth: Dp? = null,
    data: List<DuaNameAndCount>,
    navController: NavHostController
) {
    LandingScreenTopSelection(
        modifier = Modifier
            .height(120.sdp)
            .then(
                if (seventyPercentWidth == null) Modifier.fillMaxWidth()
                else Modifier.width(seventyPercentWidth)
            ),
        name = R.string.morning_azkar,
        resource = R.drawable.ic_morning_evening_azkar,
        subTitle = stringResource(
            id = R.string.azkar_with_count,
            data.firstOrNull { it.getDuaType() == DuaType.Morning_Evening_Night }?.count
                ?: 0
        ),
        onItemClick = {
            navController.navigate(
                NavControllerRoutes.DUA_SCREEN(duaType = DuaType.Morning_Evening_Night)
                    .getPathWithNavArgs()
            )
        }
    )
}

@Composable
fun Tasbih(
    thirtyPercentWidth: Dp? = null,
    data: List<DuaNameAndCount>,
    navController: NavHostController
) {
    Spacer(modifier = Modifier.width(5.sdp))

    LandingScreenTopSelection(
        modifier = Modifier
            .height(120.sdp)
            .then(
                if (thirtyPercentWidth == null) Modifier.fillMaxWidth()
                else Modifier.width(thirtyPercentWidth)
            ),
        name = R.string.tasbih,
        resource = R.drawable.ic_tasbih,
        subTitle = stringResource(
            id = R.string.azkar_with_count,
            data.firstOrNull { it.getDuaType() == DuaType.Ibadah }?.count
                ?: 0
        ),
        onItemClick = {
            navController.navigate(
                NavControllerRoutes.DUA_SCREEN(duaType = DuaType.Ibadah)
                    .getPathWithNavArgs()
            )
        }
    )
}

@Composable
fun LastRead(
    viewModel: LandingScreenViewModel,
    thirtyPercentWidth: Dp? = null,
    navController: NavHostController
) {
    val lastRead by viewModel.settingsDuaLastRead.collectAsStateWithLifecycle()
    LandingScreenTopSelection(
        modifier = Modifier
            .height(120.sdp)
            .then(
                if (thirtyPercentWidth == null) Modifier.fillMaxWidth()
                else Modifier.width(thirtyPercentWidth)
            ),
        name = R.string.last_read,
        resource = R.drawable.ic_last_read,
        subTitle = lastRead.second,
        onItemClick = {
            val duaNav = NavControllerRoutes.DUA_SCREEN(
                lastReadId = lastRead.first,
                duaType = DuaType.ALL
            )
            navController.navigate(duaNav.getPathWithNavArgs())
        }
    )
}

@Composable
fun AsmaulHusna(
    seventyPercentWidth: Dp? = null,
    navController: NavHostController
) {
    LandingScreenTopSelection(
        modifier = Modifier
            .height(120.sdp)
            .then(
                if (seventyPercentWidth == null) Modifier.fillMaxWidth()
                else Modifier.width(seventyPercentWidth)
            ),
        name = R.string.names_of_allah,
        resource = R.drawable.ic_names_of_allah,
        subTitle = "99",
        onItemClick = {
            navController.navigate(
                NavControllerRoutes.ASMA_UL_HUSNA().route
            )
        }
    )
}