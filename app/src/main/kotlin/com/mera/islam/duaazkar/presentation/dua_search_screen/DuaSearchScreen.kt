package com.mera.islam.duaazkar.presentation.dua_search_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mera.islam.duaazkar.NavControllerRoutes
import com.mera.islam.duaazkar.R
import com.mera.islam.duaazkar.core.presentation.CustomLazyList
import com.mera.islam.duaazkar.core.presentation.DefaultTopAppBar
import com.mera.islam.duaazkar.core.presentation.DuaAzkarWithBackground
import com.mera.islam.duaazkar.core.presentation.Loading
import com.mera.islam.duaazkar.core.utils.LoadingResources
import com.mera.islam.duaazkar.presentation.home_screen.components.DuaTypesWithCountView
import com.mera.islam.duaazkar.ui.theme.RobotoFonts
import com.mera.islam.duaazkar.ui.theme.darkTextGrayColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DuaSearchScreen(
    navController: NavHostController,
    viewModel: DuaSearchScreenViewModel = hiltViewModel()
) {
    DuaAzkarWithBackground {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            topBar = {
                DefaultTopAppBar(
                    navHostController = navController,
                    isSearchPressed = true,
                    onSearchText = viewModel::search
                )
            }
        ) { paddingValues ->
            LaunchedEffect(key1 = Unit) {
                viewModel.search("")
            }

            val allDuas by viewModel.searchedDua.collectAsStateWithLifecycle()

            when (allDuas) {
                LoadingResources.Loading -> Loading(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                )

                is LoadingResources.SuccessList -> {
                    val data = (allDuas as LoadingResources.SuccessList).data

                    if (data.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_no_search_icon),
                                    contentDescription = "No bookmark",
                                    tint = Color.Unspecified,
                                    modifier = Modifier.size(90.sdp)
                                )
                                Spacer(modifier = Modifier.height(5.sdp))
                                Text(
                                    text = stringResource(R.string.no_bookmarks_yet),
                                    color = darkTextGrayColor,
                                    fontFamily = RobotoFonts.ROBOTO_MEDIUM.getFont(),
                                    fontSize = 16.ssp
                                )
                            }
                        }
                    } else
                        CustomLazyList(
                            modifier = Modifier
                                .padding(paddingValues)
                                .padding(horizontal = 10.sdp)
                                .padding(top = 5.sdp)
                        ) {
                            items(data.size) { index ->
                                val dua = data[index]

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