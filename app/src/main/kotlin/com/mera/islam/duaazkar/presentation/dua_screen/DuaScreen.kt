package  com.mera.islam.duaazkar.presentation.dua_screen

import android.view.WindowManager
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mera.islam.duaazkar.MainActivity
import com.mera.islam.duaazkar.NavControllerRoutes
import com.mera.islam.duaazkar.R
import com.mera.islam.duaazkar.core.extensions.copy
import com.mera.islam.duaazkar.core.extensions.share
import com.mera.islam.duaazkar.core.presentation.DefaultTopAppBar
import com.mera.islam.duaazkar.core.presentation.DuaAzkarWithBackground
import com.mera.islam.duaazkar.core.presentation.Loading
import com.mera.islam.duaazkar.core.presentation.arabic_with_translation.CustomTextCell
import com.mera.islam.duaazkar.core.substitution.ArabicModelWithTranslationModel
import com.mera.islam.duaazkar.core.utils.UiStates
import com.mera.islam.duaazkar.core.utils.fonts.ArabicFonts
import com.mera.islam.duaazkar.domain.models.dua.DuaType
import com.mera.islam.duaazkar.presentation.dua_screen.components.DuaBottomBar
import com.mera.islam.duaazkar.presentation.dua_screen.components.DuaBottomNavItems
import com.mera.islam.duaazkar.presentation.dua_screen.components.DuaBottomSheetAudio
import com.mera.islam.duaazkar.presentation.dua_screen.components.DuaBottomSheetDisplay
import com.mera.islam.duaazkar.presentation.dua_screen.components.DuaBottomSheetSettings
import com.mera.islam.duaazkar.presentation.dua_screen.components.DuaCategoriesDrawer
import com.mera.islam.duaazkar.ui.theme.darkTextGrayColor
import com.mera.islam.duaazkar.ui.theme.transliterationBlurColor
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


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

        BackHandler(sheetState.isVisible) {
            coroutineScope.launch { sheetState.hide() }
        }

        LaunchedEffect(key1 = Unit, block = {
            viewModel.uiEvent.collect { uiEvent ->
                when (uiEvent) {
                    is UiEvent.KeepScreenOn -> {
                        (context as MainActivity).window.addFlags(
                            if (uiEvent.on) WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON else 0
                        )
                    }
                }
            }
        })

        var selectedOption by remember {
            mutableStateOf(DuaBottomNavItems.None)
        }

        LaunchedEffect(key1 = selectedOption) {
            when (selectedOption) {
                DuaBottomNavItems.Categories -> drawerState.open()
                DuaBottomNavItems.Display -> sheetState.show()
                DuaBottomNavItems.Audio -> sheetState.show()
                DuaBottomNavItems.Settings -> sheetState.show()
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

        LaunchedEffect(key1 = Unit) {
            snapshotFlow {
                sheetState.isVisible
            }.collect {
                if (!it) selectedOption = DuaBottomNavItems.None
            }
        }

        ModalNavigationDrawer(
            drawerState = drawerState,
            scrimColor = Color.Transparent,
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
            })
        {

            var requestWriteSystemSettingsDialog by remember {
                mutableStateOf(false)
            }

            if (requestWriteSystemSettingsDialog)
                AlertDialog(
                    onDismissRequest = {
                        requestWriteSystemSettingsDialog = !requestWriteSystemSettingsDialog
                    },
                    title = {
                        Text(text = stringResource(id = R.string.write_settings))
                    },
                    text = {
                        Text(text = stringResource(id = R.string.write_settings_exp))
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            requestWriteSystemSettingsDialog = !requestWriteSystemSettingsDialog

                            viewModel.requestWriteSettingsPermission()
                        }) {
                            Text(text = stringResource(id = R.string.grant))
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            requestWriteSystemSettingsDialog = !requestWriteSystemSettingsDialog
                        }) {
                            Text(text = stringResource(id = R.string.deny))
                        }
                    }
                )

            val selectedTheme by viewModel.selectedTheme.collectAsStateWithLifecycle()

            ModalBottomSheetLayout(
                sheetContent = {
                    when (selectedOption) {
                        DuaBottomNavItems.Display -> BottomSheetDisplay(
                            selectedTheme = selectedTheme,
                            viewModel = viewModel,
                            onCloseBottomSheet = {
                                selectedOption = DuaBottomNavItems.None
                                coroutineScope.launch {
                                    sheetState.hide()
                                }
                            }, onBrightnessChangeRequest = {
                                if (!viewModel.hasWriteSettingsPermission()) requestWriteSystemSettingsDialog =
                                    true
                                else viewModel.onUserEvent(UserEvent.ChangeSystemBrightness(it))
                            })

                        DuaBottomNavItems.Settings -> BottomSheetSettings(
                            viewModel = viewModel,
                            onCloseBottomSheet = {
                                selectedOption = DuaBottomNavItems.None
                                coroutineScope.launch {
                                    sheetState.hide()
                                }
                            })

                        DuaBottomNavItems.Audio -> DuaBottomSheetAudio()

                        else -> {}
                    }
                },
                sheetBackgroundColor = Color.White,
                sheetState = sheetState,
                scrimColor = Color.Transparent,
                sheetShape = RoundedCornerShape(topEnd = 16.sdp, topStart = 16.sdp),
                content = {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
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
                        val allDuas by viewModel.allDuaWithTranslations.collectAsStateWithLifecycle()

                        when (allDuas) {
                            is UiStates.Loading -> Loading(
                                isLoading = true,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(paddingValues)
                                    .background(color = Color.Transparent)
                            )

                            is UiStates.Success<List<ArabicModelWithTranslationModel>> -> {

                                val listState = rememberLazyListState()
                                val duas =
                                    (allDuas as UiStates.Success).template

                                val textSize by viewModel.arabicWithTranslationStateListener.textSize.collectAsStateWithLifecycle()
                                val arabicFonts by viewModel.arabicWithTranslationStateListener.arabicFont.collectAsStateWithLifecycle()

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
                                                arabicFont = ArabicFonts.getLanguageFont(
                                                    arabicFonts
                                                ).getFont(),
                                                textSize = if (textSize == 0.sp) duaItem.fontSize() else textSize,
                                                matchTextList = matchTextList,
                                                isPlaying = isPlaying,
                                                cardBackgroundColor = when (selectedTheme) {
                                                    R.drawable.ic_white_theme -> Color.White
                                                    R.drawable.ic_gray_theme -> Color(0xffe8f6f4)
                                                    R.drawable.ic_skin_theme -> Color(0xfffff9eb)
                                                    R.drawable.ic_dark_theme -> Color(0xff343434)
                                                    else -> Color.White
                                                },
                                                arabicColor = when (selectedTheme) {
                                                    R.drawable.ic_dark_theme -> Color.White
                                                    else -> Color.darkTextGrayColor
                                                },
                                                translationColor = when (selectedTheme) {
                                                    R.drawable.ic_dark_theme -> Color.White
                                                    else -> Color.darkTextGrayColor
                                                },
                                                transliterationColor = when (selectedTheme) {
                                                    R.drawable.ic_dark_theme -> Color(0xff24c2cc)
                                                    else -> Color.transliterationBlurColor
                                                },
                                                isDarkTheme = when (selectedTheme) {
                                                    R.drawable.ic_dark_theme -> true
                                                    else -> false
                                                },
                                                onBookmarkedClick = {
                                                    viewModel.onUserEvent(
                                                        UserEvent.IsBookmarked(
                                                            !duaItem.isFav(),
                                                            duaItem.getDataId()
                                                        )
                                                    )
                                                },
                                                onItemClick = {},
                                                onOpenTashib = {
                                                    navHostController.navigate(
                                                        NavControllerRoutes.DUA_TASBIH_SCREEN(
                                                            duaId = duaItem.getDataId()
                                                        ).getPathWithNavArgs()
                                                    )
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
                                        .fillMaxSize()
                                        .padding(paddingValues)
                                        .background(color = Color.Transparent)
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
            )
        }
    }
}

@Composable
private fun BottomSheetSettings(
    viewModel: DuaScreenViewModel,
    onCloseBottomSheet: () -> Unit = {}
) {
    val duaTextSize by viewModel.arabicWithTranslationStateListener.textSize.collectAsStateWithLifecycle()
    val arabicFont by viewModel.arabicWithTranslationStateListener.arabicFont.collectAsStateWithLifecycle()
    val translationOptions by viewModel.translators.collectAsStateWithLifecycle()

    DuaBottomSheetSettings(
        viewModel = viewModel,
        fontSize = duaTextSize,
        selectedArabicFont = ArabicFonts.getLanguageFont(arabicFont),
        onFontSizeChange = {
            viewModel.onUserEvent(UserEvent.TextSizeChanged(it))
        },
        onCloseBottomSheet = onCloseBottomSheet,
        onArabicFontChange = {
            viewModel.onUserEvent(UserEvent.SelectedFont(it))
        },
        duaTranslatorOptions = translationOptions,
        onTranslationFontChanged = { languageFonts ->
            viewModel.onUserEvent(UserEvent.SelectedFont(languageFonts))
        },
        onTranslationToggle = { translatorId, isEnabled ->
            viewModel.onUserEvent(
                UserEvent.TranslationsOptionsChanged(translatorId, isEnabled)
            )
        }
    )
}

@Composable
private fun BottomSheetDisplay(
    selectedTheme: Int,
    viewModel: DuaScreenViewModel,
    onCloseBottomSheet: () -> Unit,
    onBrightnessChangeRequest: (Float) -> Unit
) {
    val brightness by viewModel.screenBrightness.collectAsStateWithLifecycle()
    val keepScreenOn by viewModel.keepScreenOn.collectAsStateWithLifecycle()

    DuaBottomSheetDisplay(
        selectedTheme = selectedTheme,
        currentBrightness = brightness,
        isScreenCheckOn = keepScreenOn,
        onThemeSelected = {
            viewModel.onUserEvent(UserEvent.SelectedTheme(it))
        },
        onCloseBottomSheet = onCloseBottomSheet,
        onBrightnessChangeRequest = onBrightnessChangeRequest,
        keepScreenOn = {
            viewModel.onUserEvent(UserEvent.KeepScreenOn(it))
        }
    )
}

fun main() {
    runBlocking {
        val outPath =
            File("./hisnulMuslim/ia601201.us.archive.org/0/items/HisnulMuslimAudio_201510")
        for (index in (1..500)) {
            try {
                val url =
                    URL("https://ia601201.us.archive.org/0/items/HisnulMuslimAudio_201510/n$index.mp3")
                val httpConnection = url.openConnection() as HttpURLConnection

                val inputStream: InputStream = httpConnection.getInputStream()
                val saveFilePath: String = outPath.absolutePath + File.separator + "n$index.mp3"

                println("Downloading $saveFilePath")

                if (!outPath.exists()) outPath.mkdirs()

                // opens an output stream to save into file

                // opens an output stream to save into file
                val outputStream = FileOutputStream(saveFilePath)

                var bytesRead: Int
                val buffer = ByteArray(1024 * 4)
                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                }

                outputStream.close()
                inputStream.close()
            } catch (e: Exception) {

            }
        }
    }
}