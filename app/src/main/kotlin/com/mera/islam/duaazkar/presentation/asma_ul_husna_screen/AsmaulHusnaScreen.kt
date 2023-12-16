package com.mera.islam.duaazkar.presentation.asma_ul_husna_screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mera.islam.duaazkar.R
import com.mera.islam.duaazkar.core.presentation.AudioPlayer
import com.mera.islam.duaazkar.core.presentation.CustomLazyList
import com.mera.islam.duaazkar.core.presentation.DefaultTopAppBar
import com.mera.islam.duaazkar.core.presentation.DuaAzkarWithBackground
import com.mera.islam.duaazkar.core.presentation.Loading
import com.mera.islam.duaazkar.core.utils.EventResources
import com.mera.islam.duaazkar.domain.models.asmaUlHusna.AsmaulHusnaModel
import com.mera.islam.duaazkar.presentation.asma_ul_husna_screen.components.AsmaBottomSheetStyleChange
import com.mera.islam.duaazkar.presentation.asma_ul_husna_screen.components.AsmaGridItem
import com.mera.islam.duaazkar.presentation.asma_ul_husna_screen.components.AsmaListItem
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AsmaulHusnaScreen(
    navController: NavHostController,
    viewModel: AsmaulHusnaScreenViewModel = hiltViewModel()
) {

    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }

    ModalBottomSheetLayout(
        sheetContent = {
            val asmaPreview by viewModel.asmaPreview.collectAsStateWithLifecycle()

            AsmaBottomSheetStyleChange(
                selectedPreview = asmaPreview,
                onItemSelected = viewModel::setAsmaPreview,
                onCloseBottomSheet = {
                    coroutineScope.launch { sheetState.hide() }
                }
            )
        },
        sheetBackgroundColor = Color.White,
        sheetState = sheetState,
        scrimColor = Color.Transparent,
        sheetShape = RoundedCornerShape(topEnd = 16.sdp, topStart = 16.sdp),
    ) {
        DuaAzkarWithBackground(
            modifier = Modifier.fillMaxSize(),
            addScaffolding = true,
            topBar = {
                DefaultTopAppBar(
                    title = stringResource(id = R.string.asma_ul_husna),
                    navHostController = navController,
                    hasSearch = false,
                    actions = {
                        IconButton(onClick = { coroutineScope.launch { if (sheetState.isVisible) sheetState.hide() else sheetState.show() } }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_asma_style_selection),
                                contentDescription = "Selection",
                                tint = Color.Unspecified
                            )
                        }
                    }
                )
            },
            bottomBar = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.White.copy(0.8f),
                            shape = RoundedCornerShape(topStart = 12.sdp, topEnd = 12.sdp)
                        ),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(10.sdp))
                    AudioPlayer()
                    Spacer(modifier = Modifier.height(10.sdp))
                }
            })
        {
            val asmaulHusna by viewModel.asma.collectAsStateWithLifecycle()

            when (asmaulHusna) {
                EventResources.Loading -> Loading(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                )

                is EventResources.Success -> {
                    val list =
                        (asmaulHusna as EventResources.Success<List<AsmaulHusnaModel>>).template

                    val asmaPreview by viewModel.asmaPreview.collectAsStateWithLifecycle()

                    AnimatedContent(
                        targetState = asmaPreview,
                        label = "asmaAnimation"
                    ) { preview ->
                        when (preview) {
                            R.drawable.frame_73, R.drawable.frame_72 -> AsmaListAndGridView(
                                paddingValues = it,
                                asmaPreview = asmaPreview,
                                list = list,
                                viewModel = viewModel
                            )

                            R.drawable.frame_74 -> AsmaPagerView(paddingValues = it, list = list)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun AsmaPagerView(
    paddingValues: PaddingValues,
    list: List<AsmaulHusnaModel>
) {
    val pagerState = rememberPagerState { list.size }

    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            modifier = Modifier
                .fillMaxSize(0.5f)
                .padding(paddingValues = paddingValues),
            state = pagerState
        ) { page ->

        }
    }
}

@Composable
private fun AsmaListAndGridView(
    paddingValues: PaddingValues,
    asmaPreview: Int,
    list: List<AsmaulHusnaModel>,
    viewModel: AsmaulHusnaScreenViewModel
) {
    CustomLazyList(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        columns = GridCells.Fixed(if (asmaPreview == R.drawable.frame_73) 3 else 1),
        contentPadding = if (asmaPreview == R.drawable.frame_73) PaddingValues(5.sdp) else PaddingValues(
            vertical = 5.sdp
        )
    ) {
        itemsIndexed(list) { index, item ->
            when (asmaPreview) {
                R.drawable.frame_72 -> AsmaListItem(
                    index = index + 1,
                    title = item.transliteration,
                    subTitle = item.enMeaning,
                    imageTint = viewModel.randomColor()
                ) {

                }

                R.drawable.frame_73 -> AsmaGridItem(
                    title = item.transliteration,
                    imageTint = viewModel.randomColor()
                )
            }

            if (index == list.lastIndex)
                Spacer(modifier = Modifier.height(15.sdp))
        }
    }
}
