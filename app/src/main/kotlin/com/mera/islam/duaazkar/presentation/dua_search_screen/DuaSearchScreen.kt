package com.mera.islam.duaazkar.presentation.dua_search_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mera.islam.duaazkar.NavControllerRoutes
import com.mera.islam.duaazkar.core.presentation.CustomLazyList
import com.mera.islam.duaazkar.core.presentation.DefaultTopAppBar
import com.mera.islam.duaazkar.core.presentation.DuaAzkarWithBackground
import com.mera.islam.duaazkar.core.presentation.Loading
import com.mera.islam.duaazkar.core.utils.LoadingResources
import com.mera.islam.duaazkar.presentation.home_screen.components.DuaTypesWithCountView
import ir.kaaveh.sdpcompose.sdp

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
                                            duaListArray = dua.getIdList().toTypedArray()
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