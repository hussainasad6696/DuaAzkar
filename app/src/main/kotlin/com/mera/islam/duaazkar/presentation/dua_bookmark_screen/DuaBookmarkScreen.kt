package  com.mera.islam.duaazkar.presentation.dua_bookmark_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import  com.mera.islam.duaazkar.NavControllerRoutes
import  com.mera.islam.duaazkar.R
import  com.mera.islam.duaazkar.core.presentation.DefaultTopAppBar
import  com.mera.islam.duaazkar.core.presentation.Loading
import  com.mera.islam.duaazkar.core.presentation.arabic_with_translation.ArabicWithTranslationTextCell
import  com.mera.islam.duaazkar.core.utils.LoadingResources
import ir.kaaveh.sdpcompose.sdp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DuaBookmarkScreen(
    navHostController: NavHostController,
    viewModel: DuaBookmarkScreenViewModel = hiltViewModel()
) {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            DefaultTopAppBar(
                title = stringResource(id = R.string.bookmarked_duas),
                navHostController = navHostController
            )
        }) { paddingValues ->
        val bookmarked by viewModel.allBookmarkedDuasWithTranslations.collectAsStateWithLifecycle()

        when (bookmarked) {
            is LoadingResources.Loading -> Loading(isLoading = true, modifier = Modifier.fillMaxSize())
            is LoadingResources.SuccessList -> {
                val arabicFont by viewModel.arabicWithTranslationStateListener.arabicTextSize.collectAsStateWithLifecycle()
                val textSize by viewModel.arabicWithTranslationStateListener.textSize.collectAsStateWithLifecycle()
                val bookmarkedList = (bookmarked as LoadingResources.SuccessList).data

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(color = Color.White)
                        .padding(horizontal = 5.sdp)
                ) {
                    items(bookmarkedList.size) {
                        val dua = bookmarkedList[it]

                        ArabicWithTranslationTextCell(
                            arabicWithTranslation = dua,
                            arabicFont = arabicFont,
                            textSize = textSize,
                            index = it,
                            onItemClick = {
                                val duaNav = NavControllerRoutes.DUA_SCREEN(lastReadId = dua.getDataId())
                                navHostController.navigate(duaNav.getPathWithNavArgs())
                            }
                        )
                    }
                }
            }

            else -> {}
        }

    }
}