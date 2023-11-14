package  com.mera.islam.duaazkar.presentation.dua_bookmark_screen

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mera.islam.duaazkar.NavControllerRoutes
import com.mera.islam.duaazkar.R
import com.mera.islam.duaazkar.core.presentation.CustomLazyList
import com.mera.islam.duaazkar.core.presentation.Loading
import com.mera.islam.duaazkar.core.utils.LoadingResources
import com.mera.islam.duaazkar.presentation.dua_bookmark_screen.components.IndexedItems
import com.mera.islam.duaazkar.presentation.landing_screen.LandingScreenViewModel
import com.mera.islam.duaazkar.ui.theme.RobotoFonts
import com.mera.islam.duaazkar.ui.theme.darkTextGrayColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

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

            if (bookmarkedList.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_no_bookmark_icon),
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
            } else {
                CustomLazyList(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(horizontal = 10.sdp, vertical = 5.sdp)
                ) {
                    items(bookmarkedList.size) {
                        val dua = bookmarkedList[it]

                        IndexedItems(index = it + 1, dua = dua.reasonOrReference(), onItemClick = {
                            val duaNav =
                                NavControllerRoutes.DUA_SCREEN(lastReadId = dua.getDataId())
                            navController.navigate(duaNav.getPathWithNavArgs())
                        })
                    }
                }
            }
        }
    }
}