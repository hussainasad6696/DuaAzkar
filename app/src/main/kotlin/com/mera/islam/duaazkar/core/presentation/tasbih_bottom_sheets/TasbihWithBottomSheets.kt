package com.mera.islam.duaazkar.core.presentation.tasbih_bottom_sheets

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
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
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mera.islam.duaazkar.core.enums.TasbihType
import com.mera.islam.duaazkar.core.extensions.log
import com.mera.islam.duaazkar.core.presentation.AudioPlayer
import com.mera.islam.duaazkar.core.presentation.DefaultTopAppBar
import com.mera.islam.duaazkar.core.presentation.DuaAzkarWithBackground
import com.mera.islam.duaazkar.presentation.asma_ul_husna_screen.components.AsmaBottomSheetStyleChange
import com.mera.islam.duaazkar.presentation.dua_tasbih_screen.TasbihView
import com.mera.islam.duaazkar.presentation.dua_tasbih_screen.components.TasbihBottomSheetCustomLimit
import com.mera.islam.duaazkar.presentation.dua_tasbih_screen.components.TasbihBottomSheetReset
import com.mera.islam.duaazkar.presentation.dua_tasbih_screen.components.TasbihBottomSheetSetCustomGoals
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class TasbihBottomSheet {
    CUSTOM_LIMIT,
    SET_GOALS,
    RESET,
    ASMA_SETTINGS
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TasbihWithBottomSheets(
    modifier: Modifier = Modifier,
    viewModel: TasbihBottomSheetStateViewModel = hiltViewModel(),
    tasbihType: TasbihType,
    savedStateHandle: SavedStateHandle,
    navHostController: NavHostController,
    bottomSheetState: ModalBottomSheetState? = null,
    pageTitle: String = "Tasbih",
    defaultOptions: TasbihBottomSheet = TasbihBottomSheet.CUSTOM_LIMIT,
    tasbihViewVisible: Boolean = true,
    topBarActions: @Composable RowScope.() -> Unit = {},
    tasbihTopContent: @Composable BoxScope.() -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val sheetState = bottomSheetState
        ?: rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }

    var currentBottomSheet by remember {
        mutableStateOf(defaultOptions)
    }

    ModalBottomSheetLayout(
        sheetContent = {
            LaunchedEffect(key1 = Unit) {
                viewModel.initializeTasbihItem(savedStateHandle, tasbihType)

                snapshotFlow {
                    sheetState.isVisible
                }.collect {
                    if (it.not())
                        currentBottomSheet = defaultOptions
                }
            }

            AnimatedContent(
                targetState = currentBottomSheet,
                label = "BottomSheetChange"
            ) { bottomSheet ->
                when (bottomSheet) {
                    TasbihBottomSheet.CUSTOM_LIMIT -> {
                        val totalCount by viewModel.tasbihTotalCount.collectAsStateWithLifecycle()
                        val tasbihSoundEnabled by viewModel.tasbihSoundEnabled.collectAsStateWithLifecycle()

                        TasbihBottomSheetCustomLimit(
                            selectedOptions = totalCount,
                            isTasbihSoundOn = tasbihSoundEnabled,
                            onCloseBottomSheet = {
                                coroutineScope.launch { sheetState.hide() }
                            },
                            onRadioOptionSelected = {
                                viewModel.onUserEvent(
                                    UserEvent.TasbihTotalCountOption(totalCount = it)
                                )
                            },
                            onSoundOptionsChanged = {
                                viewModel.onUserEvent(
                                    UserEvent.OnSoundOptionChanged(
                                        it
                                    )
                                )
                            },
                            onCustomOption = {
                                currentBottomSheet = TasbihBottomSheet.SET_GOALS
                            }
                        )
                    }

                    TasbihBottomSheet.SET_GOALS -> TasbihBottomSheetSetCustomGoals(
                        onCloseBottomSheet = {
                            coroutineScope.launch { sheetState.hide() }
                        },
                        onSaveClick = {
                            it.toIntOrNull()?.let { count ->
                                viewModel.onUserEvent(
                                    UserEvent.TasbihTotalCountOption(
                                        totalCount = count
                                    )
                                )
                                coroutineScope.launch { sheetState.hide() }
                            }
                        }
                    )

                    TasbihBottomSheet.RESET -> TasbihBottomSheetReset(
                        onCancelClick = { coroutineScope.launch { sheetState.hide() } },
                        onResetClick = {
                            coroutineScope.launch { sheetState.hide() }
                            viewModel.onUserEvent(UserEvent.ResetTasbih)
                        })

                    TasbihBottomSheet.ASMA_SETTINGS -> {
                        val asmaPreview by viewModel.asmaPreview.collectAsStateWithLifecycle()

                        AsmaBottomSheetStyleChange(
                            selectedPreview = asmaPreview,
                            onItemSelected = viewModel::setAsmaPreview,
                            onCloseBottomSheet = {
                                coroutineScope.launch { sheetState.hide() }
                            }
                        )
                    }
                }
            }
        },
        sheetBackgroundColor = Color.White,
        sheetState = sheetState,
        scrimColor = Color.Transparent,
        sheetShape = RoundedCornerShape(topEnd = 16.sdp, topStart = 16.sdp),
        content = {
            DuaAzkarWithBackground(
                addScaffolding = true,
                topBar = {
                    DefaultTopAppBar(
                        title = pageTitle,
                        navHostController = navHostController,
                        hasSearch = false,
                        actions = topBarActions
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
                },
                content = {
                    Column(
                        modifier = modifier.padding(it)
                    ) {
                        content()

                        Spacer(modifier = Modifier.height(10.sdp))

                        AnimatedVisibility(visible = tasbihViewVisible) {
                            val count by viewModel.tasbihCount.collectAsStateWithLifecycle()
                            val tasbihSoundEnabled by viewModel.tasbihSoundEnabled.collectAsStateWithLifecycle()
                            val totalCount by viewModel.tasbihTotalCount.collectAsStateWithLifecycle()

                            TasbihView(
                                count = count,
                                tasbihSoundEnabled = tasbihSoundEnabled,
                                totalCount = totalCount,
                                onEditTasbihOptions = {
                                    currentBottomSheet = TasbihBottomSheet.CUSTOM_LIMIT
                                    coroutineScope.launch {
                                        delay(100L)
                                        sheetState.show()
                                    }
                                }, onResetTasbihClick = {
                                    currentBottomSheet = TasbihBottomSheet.RESET
                                    coroutineScope.launch {
                                        delay(100L)
                                        sheetState.show()
                                    }
                                }, onTasbihClick = {
                                    viewModel.onUserEvent(
                                        UserEvent.TasbihCount(
                                            count = count
                                        )
                                    )
                                },
                                topContent = tasbihTopContent
                            )

                            Spacer(modifier = Modifier.height(10.sdp))
                        }
                    }
                }
            )
        }
    )
}