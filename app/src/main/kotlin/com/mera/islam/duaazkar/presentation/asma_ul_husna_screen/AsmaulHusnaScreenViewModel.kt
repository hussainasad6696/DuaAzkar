package com.mera.islam.duaazkar.presentation.asma_ul_husna_screen

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mera.islam.duaazkar.R
import com.mera.islam.duaazkar.core.Settings
import com.mera.islam.duaazkar.core.presentation.arabic_with_translation.TasbihStateListener
import com.mera.islam.duaazkar.core.utils.EventResources
import com.mera.islam.duaazkar.core.utils.PaletteGenerator
import com.mera.islam.duaazkar.domain.models.asmaUlHusna.AsmaulHusnaModel
import com.mera.islam.duaazkar.domain.repo.TasbihRepo
import com.mera.islam.duaazkar.domain.repo.asmaUlHusna.AsmaulHusnaRepo
import com.mera.islam.duaazkar.ui.theme.blueColor
import com.mera.islam.duaazkar.ui.theme.green
import com.mera.islam.duaazkar.ui.theme.greyColor
import com.mera.islam.duaazkar.ui.theme.lightBlueColor
import com.mera.islam.duaazkar.ui.theme.lightGreenColor
import com.mera.islam.duaazkar.ui.theme.lightOrangeColor
import com.mera.islam.duaazkar.ui.theme.orangeColor
import com.mera.islam.duaazkar.ui.theme.pinkColor
import com.mera.islam.duaazkar.ui.theme.purpleColor
import com.mera.islam.duaazkar.ui.theme.redColor
import com.mera.islam.duaazkar.ui.theme.skyBlueColor
import com.mera.islam.duaazkar.ui.theme.yellowColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AsmaWithAssets(
    val color: Color,
    val asma: AsmaulHusnaModel,
    val randomBgImage: Int,
    val colorPalette: List<Color>
)

@HiltViewModel
class AsmaulHusnaScreenViewModel @Inject constructor(
    private val settings: Settings,
    asmaulHusnaRepo: AsmaulHusnaRepo,
    private val paletteGenerator: PaletteGenerator,
    savedStateHandle: SavedStateHandle,
    tasbihRepo: TasbihRepo
) : ViewModel() {

    val asma = asmaulHusnaRepo.getAllAsmaulHusna()
        .map {
            EventResources.Success(it.map { asmaulHusnaModel ->
                AsmaWithAssets(
                    color = randomColor(),
                    asma = asmaulHusnaModel,
                    randomBgImage = paletteGenerator.randomImage(),
                    colorPalette = paletteGenerator.getPaletteList()
                )
            })
        }
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = EventResources.Loading
        )

    private val tasbihStateListener = TasbihStateListener(
        coroutineContext = viewModelScope.coroutineContext,
        savedStateHandle = savedStateHandle,
        tasbihRepo = tasbihRepo,
        settings = settings
    )

    val tasbihCount: StateFlow<Int?>
        get() = tasbihStateListener.tasbihCount

    val tasbihTotalCount: StateFlow<Int?>
        get() = tasbihStateListener.tasbihTotalCount

    val tasbihSoundEnabled: StateFlow<Boolean>
        get() = tasbihStateListener.tasbihSoundEnabled

    fun setDuaId(duaId: Int) = tasbihStateListener.setDuaId(duaId)

    val asmaPreview = settings.getSelectedAsmaPreview()
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = R.drawable.frame_72
        )

    fun setAsmaPreview(asmaPreview: Int) {
        viewModelScope.launch { settings.setSelectedAsmaPreview(asmaPreview) }
    }

    private var lastColorIndex = 0
    private fun randomColor(): Color {
        val colors = arrayOf(
            Color.yellowColor,
            Color.purpleColor,
            Color.green,
            Color.pinkColor,
            Color.orangeColor,
            Color.skyBlueColor,
            Color.redColor,
            Color.blueColor,
            Color.greyColor,
            Color.lightOrangeColor,
            Color.lightGreenColor,
            Color.lightBlueColor,
        )

        lastColorIndex = (lastColorIndex + 1) % colors.size
        return colors[lastColorIndex]
    }
}