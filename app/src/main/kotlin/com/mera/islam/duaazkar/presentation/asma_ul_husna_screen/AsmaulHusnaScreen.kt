package com.mera.islam.duaazkar.presentation.asma_ul_husna_screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.mera.islam.duaazkar.R
import com.mera.islam.duaazkar.core.presentation.AudioPlayer
import com.mera.islam.duaazkar.core.presentation.CustomLazyList
import com.mera.islam.duaazkar.core.presentation.DefaultTopAppBar
import com.mera.islam.duaazkar.core.presentation.DuaAzkarWithBackground
import com.mera.islam.duaazkar.core.presentation.Loading
import com.mera.islam.duaazkar.core.utils.EventResources
import com.mera.islam.duaazkar.presentation.asma_ul_husna_screen.components.AsmaBottomSheetStyleChange
import com.mera.islam.duaazkar.presentation.asma_ul_husna_screen.components.AsmaGridItem
import com.mera.islam.duaazkar.presentation.asma_ul_husna_screen.components.AsmaListItem
import com.mera.islam.duaazkar.presentation.dua_tasbih_screen.TasbihView
import com.mera.islam.duaazkar.ui.theme.RobotoFonts
import com.mera.islam.duaazkar.ui.theme.applicationBackgroundColor
import com.mera.islam.duaazkar.ui.theme.darkTextGrayColor
import com.mera.islam.duaazkar.ui.theme.gray9fColor
import com.mera.islam.duaazkar.ui.theme.green
import com.mera.islam.duaazkar.ui.theme.lightTextGrayColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
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
                        (asmaulHusna as EventResources.Success).template

                    val asmaPreview by viewModel.asmaPreview.collectAsStateWithLifecycle()

                    AnimatedContent(
                        targetState = asmaPreview,
                        label = "asmaAnimation"
                    ) { preview ->
                        when (preview) {
                            R.drawable.frame_73, R.drawable.frame_72 -> AsmaListAndGridView(
                                paddingValues = it,
                                asmaPreview = asmaPreview,
                                list = list
                            )

                            R.drawable.frame_74 -> AsmaPagerView(
                                paddingValues = it,
                                list = list,
                                viewModel = viewModel,
                                onEditTasbihOptions = {},
                                onResetTasbihClick = {},
                                onTasbihClick = {}
                            )
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
    list: List<AsmaWithAssets>,
    viewModel: AsmaulHusnaScreenViewModel,
    onEditTasbihOptions: () -> Unit,
    onResetTasbihClick: () -> Unit,
    onTasbihClick: () -> Unit,
) {
    val width = LocalConfiguration.current.screenWidthDp.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        val coroutineScope = rememberCoroutineScope()
        val pagerState = rememberPagerState { list.size }

        var asmaulHusna: AsmaWithAssets? by remember {
            mutableStateOf(null)
        }

        LaunchedEffect(key1 = Unit) {
            snapshotFlow { pagerState.currentPage }.collect {
                coroutineScope.launch { asmaulHusna = list[it] }
            }
        }

        HorizontalPager(
            modifier = Modifier
                .then(
                    if (asmaulHusna != null) Modifier.background(
                        brush = Brush.verticalGradient(
                            colors = asmaulHusna!!.colorPalette
                        )
                    ) else Modifier
                )
                .fillMaxWidth()
                .fillMaxHeight(0.5f),
            state = pagerState,
            pageSpacing = 5.sdp,
            pageSize = PageSize.Fixed((width.value * 0.85).dp),
            contentPadding = PaddingValues(
                start = (width.value * 0.075).dp,
                end = (width.value * 0.075).dp
            )
        ) { page ->
            Box(
                modifier = Modifier
                    .padding(horizontal = 5.sdp)
                    .fillMaxWidth()
                    .fillMaxHeight(if (pagerState.currentPage == page) 0.9f else 0.8f)
                    .animateContentSize()
                    .graphicsLayer {
                        // Calculate the absolute offset for the current page from the
                        // scroll position. We use the absolute value which allows us to mirror
                        // any effects for both directions
                        val pageOffset = (
                                (pagerState.currentPage - page) + pagerState
                                    .currentPageOffsetFraction
                                ).absoluteValue

                        // We animate the alpha, between 50% and 100%
                        alpha = lerp(
                            start = 0.5f.dp,
                            stop = 1f.dp,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).value
                    },
                contentAlignment = Alignment.Center
            ) {
                val asma = list[page]

                AsyncImage(
                    model = asma.randomBgImage,
                    contentDescription = "Asma",
                    modifier = Modifier
                        .matchParentSize()
                        .clip(RoundedCornerShape(15.dp)),
                    contentScale = ContentScale.Crop,
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    AsyncImage(
                        model = R.drawable.asma_00,
                        contentDescription = "AsmaImage",
                        contentScale = ContentScale.Inside,
                        modifier = Modifier
                            .width(80.sdp)
                            .height(77.sdp),
                        colorFilter = ColorFilter.tint(color = Color.White)
                    )

                    Spacer(modifier = Modifier.height(15.sdp))

                    Text(
                        text = asma.asma.transliteration,
                        color = Color.applicationBackgroundColor,
                        fontSize = 16.ssp,
                        fontFamily = RobotoFonts.ROBOTO_REGULAR.getFont(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(horizontal = 2.sdp)
                    )
                }
            }
        }

        val count by viewModel.tasbihCount.collectAsStateWithLifecycle()
        val tasbihSoundEnabled by viewModel.tasbihSoundEnabled.collectAsStateWithLifecycle()
        val totalCount by viewModel.tasbihTotalCount.collectAsStateWithLifecycle()

        TasbihView(
            modifier = Modifier.background(
                color = Color.White,
                shape = RoundedCornerShape(topStartPercent = 5, topEndPercent = 5)
            ),
            count = count,
            tasbihSoundEnabled = tasbihSoundEnabled,
            totalCount = totalCount,
            onEditTasbihOptions = onEditTasbihOptions,
            onResetTasbihClick = onResetTasbihClick,
            onTasbihClick = onTasbihClick,
            topContent = {
                Column(modifier = Modifier
                    .padding(10.sdp)
                    .fillMaxWidth()) {

                    AnimatedContent(targetState = asmaulHusna?.asma?.enMeaning ?: ""){
                        androidx.compose.material3.Text(
                            text = it,
                            color = Color.darkTextGrayColor,
                            fontSize = 13.ssp,
                            fontFamily = RobotoFonts.ROBOTO_MEDIUM.getFont()
                        )
                    }

                    Spacer(modifier = Modifier.height(2.sdp))

                    AnimatedContent(targetState = asmaulHusna?.asma?.enDesc ?: "") {
                        androidx.compose.material3.Text(
                            text = it,
                            color = Color.lightTextGrayColor,
                            fontSize = 11.ssp,
                            fontFamily = RobotoFonts.ROBOTO_REGULAR.getFont(),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Spacer(modifier = Modifier.height(2.sdp))

                    AnimatedContent(targetState = asmaulHusna?.asma?.found ?: "") {
                        androidx.compose.material3.Text(
                            text = it,
                            color = Color.green,
                            fontSize = 9.ssp,
                            fontFamily = RobotoFonts.ROBOTO_REGULAR.getFont()
                        )
                    }
                }
            }
        )
    }
}

@Composable
private fun AsmaListAndGridView(
    paddingValues: PaddingValues,
    asmaPreview: Int,
    list: List<AsmaWithAssets>
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
                    title = item.asma.transliteration,
                    subTitle = item.asma.enMeaning,
                    imageTint = item.color
                ) {

                }

                R.drawable.frame_73 -> AsmaGridItem(
                    title = item.asma.transliteration,
                    imageTint = item.color
                )
            }

            if (index == list.lastIndex)
                Spacer(modifier = Modifier.height(15.sdp))
        }
    }
}
