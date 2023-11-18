package com.mera.islam.duaazkar.presentation.dua_listing_screen

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mera.islam.duaazkar.NavControllerRoutes
import com.mera.islam.duaazkar.R
import com.mera.islam.duaazkar.core.extensions.log
import com.mera.islam.duaazkar.core.presentation.CustomLazyList
import com.mera.islam.duaazkar.core.presentation.DefaultTopAppBar
import com.mera.islam.duaazkar.core.presentation.DuaAzkarWithBackground
import com.mera.islam.duaazkar.core.presentation.Loading
import com.mera.islam.duaazkar.core.substitution.ArabicWithTranslation
import com.mera.islam.duaazkar.core.utils.Resources
import com.mera.islam.duaazkar.domain.models.DuaType
import com.mera.islam.duaazkar.presentation.dua_bookmark_screen.components.IndexedItems
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch

@Composable
fun DuaListingScreen(
    navHostController: NavHostController,
    viewModel: DuaListingScreenViewModel = hiltViewModel(),
    duaIds: List<Int>,
    matchTextList: List<String> = emptyList()
) {
    val context = LocalContext.current

    DuaAzkarWithBackground(addScaffolding = true,
        topBar = {
            val title by viewModel.title.collectAsStateWithLifecycle()

            DefaultTopAppBar(
                title = title,
                navHostController = navHostController,
                hasSearch = false
            )
        }) { paddingValues ->
        if (duaIds.isEmpty())
            Toast.makeText(context, "Empty list", Toast.LENGTH_SHORT).show()

        LaunchedEffect(key1 = Unit) {
            viewModel.loadDuaByIds(duaIds)
        }

        val duaByIds by viewModel.duaHeadingList.collectAsStateWithLifecycle()

        when (duaByIds) {
            Resources.Loading -> Loading(modifier = Modifier.padding(paddingValues))
            is Resources.SuccessList<ArabicWithTranslation> -> {
                val data = (duaByIds as Resources.SuccessList).data

                CustomLazyList(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                        .padding(horizontal = 10.sdp, vertical = 5.sdp)
                ) {
                    items(data.size) {
                        val dua = data[it]

                        IndexedItems(
                            index = it + 1,
                            dua = dua.reasonOrReference(),
                            onItemClick = {
                                val duaNav =
                                    NavControllerRoutes.DUA_SCREEN(
                                        lastReadId = dua.getDataId(),
                                        duaType = dua.getDataType() as DuaType,
                                        matchTextList = matchTextList
                                    )
                                navHostController.navigate(duaNav.getPathWithNavArgs())
                            })
                    }
                }
            }
        }
    }
}