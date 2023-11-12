package  com.mera.islam.duaazkar.presentation.dua_bookmark_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mera.islam.duaazkar.NavControllerRoutes
import com.mera.islam.duaazkar.core.presentation.CustomLazyList
import com.mera.islam.duaazkar.core.presentation.Loading
import com.mera.islam.duaazkar.core.substitution.ArabicWithTranslation
import com.mera.islam.duaazkar.core.utils.LoadingResources
import com.mera.islam.duaazkar.presentation.dua_bookmark_screen.components.IndexedItems
import com.mera.islam.duaazkar.presentation.landing_screen.LandingScreenViewModel
import ir.kaaveh.sdpcompose.sdp

@Composable
fun DuaBookmarkScreen(
    modifier: Modifier = Modifier,
    viewModel: LandingScreenViewModel,
    navController: NavHostController
) {
    val bookmarked by viewModel.allBookmarkedDuasWithTranslations.collectAsStateWithLifecycle()

    when (bookmarked) {
        is LoadingResources.Loading -> Loading(isLoading = true, modifier = modifier.fillMaxSize())
        is LoadingResources.SuccessList -> {
            val bookmarkedList = (bookmarked as LoadingResources.SuccessList).data

            CustomLazyList(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 5.sdp, vertical = 5.sdp)
            ) {
                items(bookmarkedList.size) {
                    val dua = bookmarkedList[it]

                    IndexedItems(index = it + 1, dua = dua.reasonOrReference(), onItemClick = {
                        val duaNav = NavControllerRoutes.DUA_SCREEN(lastReadId = dua.getDataId())
                        navController.navigate(duaNav.getPathWithNavArgs())
                    })
                }
            }
        }
    }
}