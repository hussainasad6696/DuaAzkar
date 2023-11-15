package com.mera.islam.duaazkar.presentation.categories_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mera.islam.duaazkar.NavControllerRoutes
import com.mera.islam.duaazkar.core.presentation.CustomLazyList
import com.mera.islam.duaazkar.core.presentation.Loading
import com.mera.islam.duaazkar.core.utils.Resources
import com.mera.islam.duaazkar.presentation.home_screen.components.DuaTypesWithCountView
import com.mera.islam.duaazkar.presentation.landing_screen.LandingScreenViewModel
import ir.kaaveh.sdpcompose.sdp

@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    viewModel: LandingScreenViewModel,
    navController: NavHostController
) {
    val allDuaTypes by viewModel.duaTypeWithCount.collectAsStateWithLifecycle()

    when (allDuaTypes) {
        Resources.Loading -> Loading(modifier = modifier.fillMaxSize())
        is Resources.SuccessList -> {
            val data = (allDuaTypes as Resources.SuccessList).data

            CustomLazyList(
                modifier
                    .padding(horizontal = 10.sdp)
                    .padding(top = 5.sdp),
                content = {
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
                })
        }
    }
}