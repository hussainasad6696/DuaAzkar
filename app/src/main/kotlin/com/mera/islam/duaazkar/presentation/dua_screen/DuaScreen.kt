package  com.mera.islam.duaazkar.presentation.dua_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import  com.mera.islam.duaazkar.R
import  com.mera.islam.duaazkar.core.extensions.share
import  com.mera.islam.duaazkar.core.presentation.DefaultTopAppBar
import  com.mera.islam.duaazkar.core.presentation.Loading
import  com.mera.islam.duaazkar.core.presentation.arabic_with_translation.ArabicWithTranslationTextCell
import  com.mera.islam.duaazkar.core.substitution.ArabicWithTranslation
import  com.mera.islam.duaazkar.core.utils.Resources
import  com.mera.islam.duaazkar.domain.models.DuaType
import  com.mera.islam.duaazkar.presentation.dua_screen.components.DuaBottomSheet
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DuaScreen(
    navHostController: NavHostController,
    viewModel: DuaScreenViewModel = hiltViewModel(),
    lastRead: Int = -1,
    duaType: DuaType
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(
        sheetContent = {
            DuaBottomSheet(viewModel = viewModel)
        },
        sheetBackgroundColor = Color.White,
        sheetState = sheetState,
        content = {
            Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                DefaultTopAppBar(
                    title = stringResource(id = R.string.duas),
                    navHostController = navHostController,
                    actions = {
                        IconButton(onClick = { coroutineScope.launch { if (sheetState.isVisible) sheetState.hide() else sheetState.show() } }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Open bottom sheet"
                            )
                        }
                    }
                )
            })
            { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(color = Color.White)
                ) {

                    LaunchedEffect(key1 = Unit, block = {
                        viewModel.loadDuasByDuaType(duaType)
                    })

                    val allDuas by viewModel.allDuaWithTranslations.collectAsStateWithLifecycle()

                    when (allDuas) {
                        is Resources.Loading -> Loading(
                            isLoading = true,
                            modifier = Modifier.fillMaxSize()
                        )

                        is Resources.SuccessList<ArabicWithTranslation> -> {
                            Box(modifier = Modifier.fillMaxSize()) {

                                val listState = rememberLazyListState()
                                val duas =
                                    (allDuas as Resources.SuccessList<ArabicWithTranslation>).data

                                val textSize by viewModel.arabicWithTranslationStateListener.textSize.collectAsStateWithLifecycle()
                                val arabicFonts by viewModel.arabicWithTranslationStateListener.arabicTextSize.collectAsStateWithLifecycle()

                                LazyColumn(
                                    content = {
                                        items(duas.size) { index ->
                                            val duaItem = duas[index]
                                            ArabicWithTranslationTextCell(
                                                arabicWithTranslation = duaItem,
                                                index = index,
                                                arabicFont = arabicFonts,
                                                textSize = textSize,
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
                                                }
                                            )

                                            if (index != duas.lastIndex)
                                                Divider()
                                        }
                                    },
                                    contentPadding = PaddingValues(5.sdp),
                                    verticalArrangement = Arrangement.spacedBy(5.sdp),
                                    state = listState,
                                    modifier = Modifier
                                )

                                if (duas.isNotEmpty() && lastRead != -1)
                                    LaunchedEffect(key1 = Unit, block = {
                                        val index = duas.indexOfFirst { it.getDataId() == lastRead }
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
        }
    )
}