package com.mera.islam.duaazkar.presentation.dua_listing_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mera.islam.duaazkar.NavControllerRoutes
import com.mera.islam.duaazkar.core.presentation.CustomLazyList
import com.mera.islam.duaazkar.core.presentation.DefaultTopAppBar
import com.mera.islam.duaazkar.core.presentation.DuaAzkarWithBackground
import com.mera.islam.duaazkar.core.presentation.Loading
import com.mera.islam.duaazkar.core.utils.UiStates
import com.mera.islam.duaazkar.domain.models.dua.DuaType
import com.mera.islam.duaazkar.presentation.dua_bookmark_screen.components.IndexedItems
import ir.kaaveh.sdpcompose.sdp

@Composable
fun DuaListingScreen(
    navHostController: NavHostController,
    viewModel: DuaListingScreenViewModel = hiltViewModel(),
    duaIds: List<Int>,
    matchTextList: List<String> = emptyList()
) {
    val context = LocalContext.current

    DuaAzkarWithBackground(
        addScaffolding = true,
        topBar = {
            val title by viewModel.title.collectAsStateWithLifecycle()

            DefaultTopAppBar(
                title = title,
                navHostController = navHostController,
                hasSearch = false
            )
        }
    ) { paddingValues ->
        if (duaIds.isEmpty())
            Toast.makeText(context, "Empty list", Toast.LENGTH_SHORT).show()

        LaunchedEffect(key1 = Unit) {
            viewModel.loadDuaByIds(duaIds)
        }

        val duaByIds by viewModel.duaHeadingList.collectAsStateWithLifecycle()

        when (duaByIds) {
            UiStates.Loading -> Loading(modifier = Modifier.padding(paddingValues))
            is UiStates.Success -> {
                val list = (duaByIds as UiStates.Success).template

                CustomLazyList(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                        .padding(horizontal = 10.sdp, vertical = 5.sdp)
                ) {
                    items(list.size) {
                        val dua = list[it]

                        IndexedItems(
                            index = it + 1,
                            dua = dua.reasonOrReference(),
                            onItemClick = {
                                val duaNav =
                                    NavControllerRoutes.DUA_SCREEN(
                                        args = NavControllerRoutes.DUA_SCREEN.DuaScreenArgs(
                                            lastReadId = dua.getDataId(),
                                            duaType = dua.getDataType() as DuaType,
                                            matchTextList = matchTextList
                                        )
                                    )
                                navHostController.navigate(duaNav.getPathWithNavArgs())
                            })

                        if (it == list.lastIndex)
                            Spacer(modifier = Modifier.height(15.sdp))
                    }
                }
            }
        }
    }
}