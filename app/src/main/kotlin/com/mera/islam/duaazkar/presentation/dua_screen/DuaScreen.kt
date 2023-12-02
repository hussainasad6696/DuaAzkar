package  com.mera.islam.duaazkar.presentation.dua_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mera.islam.duaazkar.core.extensions.copy
import com.mera.islam.duaazkar.core.extensions.log
import  com.mera.islam.duaazkar.core.extensions.share
import  com.mera.islam.duaazkar.core.presentation.DefaultTopAppBar
import com.mera.islam.duaazkar.core.presentation.DuaAzkarWithBackground
import  com.mera.islam.duaazkar.core.presentation.Loading
import  com.mera.islam.duaazkar.core.presentation.arabic_with_translation.CustomTextCell
import  com.mera.islam.duaazkar.core.substitution.ArabicModelWithTranslationModel
import  com.mera.islam.duaazkar.core.utils.EventResources
import  com.mera.islam.duaazkar.domain.models.dua.DuaType
import com.mera.islam.duaazkar.presentation.dua_screen.components.DuaBottomBar
import com.mera.islam.duaazkar.presentation.dua_screen.components.DuaBottomNavItems
import  com.mera.islam.duaazkar.presentation.dua_screen.components.DuaBottomSheet
import com.mera.islam.duaazkar.presentation.dua_screen.components.DuaBottomSheetDisplay
import com.mera.islam.duaazkar.presentation.dua_screen.components.DuaCategoriesDrawer
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DuaScreen(
    navHostController: NavHostController,
    viewModel: DuaScreenViewModel = hiltViewModel(),
    lastRead: Int = -1,
    duaType: DuaType,
    matchTextList: List<String> = emptyList()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    DuaAzkarWithBackground {
        val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

        val allDuas by viewModel.allDuaWithTranslations.collectAsStateWithLifecycle()

        LaunchedEffect(key1 = Unit, block = {
            viewModel.loadDuasByDuaType(duaType)
        })

        var selectedOption by remember {
            mutableStateOf(DuaBottomNavItems.None)
        }

        LaunchedEffect(key1 = selectedOption) {
            when(selectedOption) {
                DuaBottomNavItems.Categories -> drawerState.open()
                DuaBottomNavItems.Display -> sheetState.show()
//                DuaBottomNavItems.Audio -> TODO()
//                DuaBottomNavItems.Settings -> TODO()
//                DuaBottomNavItems.None -> TODO()
                else -> {}
            }
        }

        LaunchedEffect(key1 = Unit) {
            snapshotFlow {
                drawerState.isClosed
            }.collect {
                selectedOption = if (it) DuaBottomNavItems.None
                else DuaBottomNavItems.Categories
            }
        }

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                DuaCategoriesDrawer(viewModel = viewModel,
                    onCategorySelected = {
                        viewModel.loadDuasByDuaType(it)
                        coroutineScope.launch {
                            drawerState.close()
                        }
                    },
                    onCloseDrawerClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                    })
            }) {
            ModalBottomSheetLayout(
                sheetContent = {
                    DuaBottomSheetDisplay()
                },
                sheetBackgroundColor = Color.White,
                sheetState = sheetState,
                sheetShape = RoundedCornerShape(topEnd = 16.sdp, topStart = 16.sdp),
                content = {
                    Scaffold(modifier = Modifier.fillMaxSize(),
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.onBackground,
                        topBar = {
                            val title by viewModel.title.collectAsStateWithLifecycle()

                            DefaultTopAppBar(
                                title = title,
                                navHostController = navHostController,
                                hasSearch = false
                            )
                        }, bottomBar = {
                            DuaBottomBar(
                                selectedScreen = selectedOption,
                                onItemClick = {
                                    selectedOption = it
                                }
                            )
                        })
                    { paddingValues ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                                .background(color = Color.Transparent)
                        ) {

                            when (allDuas) {
                                is EventResources.Loading -> Loading(
                                    isLoading = true,
                                    modifier = Modifier.fillMaxSize()
                                )

                                is EventResources.SuccessList<ArabicModelWithTranslationModel> -> {

                                    val listState = rememberLazyListState()
                                    val duas =
                                        (allDuas as EventResources.SuccessList<ArabicModelWithTranslationModel>).list

                                    val textSize by viewModel.arabicWithTranslationStateListener.textSize.collectAsStateWithLifecycle()
                                    val arabicFonts by viewModel.arabicWithTranslationStateListener.arabicTextSize.collectAsStateWithLifecycle()

                                    LazyColumn(
                                        content = {
                                            items(duas.size) { index ->
                                                val duaItem = duas[index]

                                                /**
                                                 * demo play icon
                                                 * */
                                                var isPlaying by remember {
                                                    mutableStateOf(false)
                                                }

                                                CustomTextCell(
                                                    arabicModelWithTranslationModel = duaItem,
                                                    arabicFont = arabicFonts,
                                                    textSize = textSize,
                                                    matchTextList = matchTextList,
                                                    isPlaying = isPlaying,
                                                    onBookmarkedClick = {
                                                        viewModel.onUserEvent(
                                                            UserEvent.IsBookmarked(
                                                                !duaItem.isFav(),
                                                                duaItem.getDataId()
                                                            )
                                                        )
                                                    },
                                                    onItemClick = {
                                                    },
                                                    onShareClick = {
                                                        context share duaItem.getShareableString()
                                                    },
                                                    onCopyClick = {
                                                        context copy duaItem.getShareableString()
                                                        Toast.makeText(
                                                            context,
                                                            "Copied",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    },
                                                    onPlayAudio = {
                                                        isPlaying = !isPlaying
                                                    }
                                                )

                                                if (index == duas.lastIndex)
                                                    Spacer(modifier = Modifier.height(15.sdp))
                                            }
                                        },
                                        contentPadding = PaddingValues(5.sdp),
                                        verticalArrangement = Arrangement.spacedBy(5.sdp),
                                        state = listState,
                                        modifier = Modifier
                                    )

                                    if (duas.isNotEmpty() && lastRead != -1)
                                        LaunchedEffect(key1 = Unit, block = {
                                            val index =
                                                duas.indexOfFirst { it.getDataId() == lastRead }
                                            if (index == -1) return@LaunchedEffect

                                            listState.scrollToItem(index)
                                        })

                                    DisposableEffect(key1 = Unit, effect = {
                                        onDispose {
                                            viewModel.saveLastRead(listState.firstVisibleItemIndex)
                                        }
                                    })

                                }
                            }
                        }
                    }
                }
            )
        }
    }
}