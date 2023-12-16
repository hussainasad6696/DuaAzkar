package com.mera.islam.duaazkar.presentation.asma_ul_husna_screen

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mera.islam.duaazkar.R
import com.mera.islam.duaazkar.core.Settings
import com.mera.islam.duaazkar.core.utils.EventResources
import com.mera.islam.duaazkar.domain.repo.asmaUlHusna.AsmaulHusnaRepo
import com.mera.islam.duaazkar.ui.theme.orangeColor
import com.mera.islam.duaazkar.ui.theme.pinkColor
import com.mera.islam.duaazkar.ui.theme.purpleColor
import com.mera.islam.duaazkar.ui.theme.seaGreenColor
import com.mera.islam.duaazkar.ui.theme.skyBlueColor
import com.mera.islam.duaazkar.ui.theme.yellowColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AsmaulHusnaScreenViewModel @Inject constructor(
    private val settings: Settings,
    asmaulHusnaRepo: AsmaulHusnaRepo
): ViewModel() {

    val asma = asmaulHusnaRepo.getAllAsmaulHusna()
        .map {
            EventResources.Success(it)
        }
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = EventResources.Loading
        )

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
    fun randomColor(): Color {
        val colors = arrayOf(
            Color.yellowColor,
            Color.purpleColor,
            Color.seaGreenColor,
            Color.pinkColor,
            Color.orangeColor,
            Color.skyBlueColor
        )

        lastColorIndex = (lastColorIndex + 1) % colors.size
        return colors[lastColorIndex]
    }
}