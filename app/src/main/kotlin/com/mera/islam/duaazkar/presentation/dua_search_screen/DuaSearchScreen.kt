package com.mera.islam.duaazkar.presentation.dua_search_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import com.mera.islam.duaazkar.core.utils.UiStates
import com.mera.islam.duaazkar.presentation.home_screen.components.DuaTypesWithCountView
import com.mera.islam.duaazkar.ui.theme.RobotoFonts
import com.mera.islam.duaazkar.ui.theme.darkTextGrayColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.launch

@Composable
fun DuaSearchScreen(
    navController: NavHostController,
    viewModel: DuaSearchScreenViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    DuaAzkarWithBackground(addScaffolding = true,
        topBar = {
            val searchedText by viewModel.searchedText.collectAsStateWithLifecycle()

            DefaultTopAppBar(
                navHostController = navController,
                isSearchPressed = true,
                onSearchText = viewModel::search,
                searchedText = searchedText
            )
        }) { paddingValues ->

        LaunchedEffect(key1 = Unit) {
            viewModel.search("")
        }

        val allDuas by viewModel.searchedDua.collectAsStateWithLifecycle()

        when (allDuas) {
            UiStates.Loading -> Loading(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            )

            is UiStates.Success -> {
                val list = (allDuas as UiStates.Success).template

                if (list.isEmpty()) {
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
                                text = stringResource(R.string.no_search_found),
                                color = Color.darkTextGrayColor,
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
                        items(list.size) { index ->
                            val dua = list[index]

                            DuaTypesWithCountView(
                                duaType = dua.getDuaType(),
                                noOfDua = dua.count,
                                onNextClick = {
                                    coroutineScope.launch {
                                        val keywords = viewModel.getKeywords()
                                        navController.navigate(
                                            NavControllerRoutes.DuaListingScreen(
                                                duaListArray = dua.getIdList().toIntArray(),
                                                matchTextList = keywords
                                            ).getPathWithNavArgs()
                                        )
                                    }
                                })

                            if (index == list.lastIndex)
                                Spacer(modifier = Modifier.height(15.sdp))
                        }
                    }

            }
        }
    }
}