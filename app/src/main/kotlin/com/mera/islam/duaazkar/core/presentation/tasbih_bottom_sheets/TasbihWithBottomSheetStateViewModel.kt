package com.mera.islam.duaazkar.core.presentation.tasbih_bottom_sheets

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mera.islam.duaazkar.R
import com.mera.islam.duaazkar.core.Settings
import com.mera.islam.duaazkar.core.enums.TasbihType
import com.mera.islam.duaazkar.core.presentation.arabic_with_translation.TASBIH_ID
import com.mera.islam.duaazkar.domain.models.TasbihModel
import com.mera.islam.duaazkar.domain.repo.TasbihRepo
import com.mera.islam.duaazkar.presentation.dua_tasbih_screen.DUA_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasbihBottomSheetStateViewModel @Inject constructor(
    private val tasbihRepo: TasbihRepo,
    private val settings: Settings
) : ViewModel() {

    private var savedStateHandle: SavedStateHandle? = null
    private var tasbihId = -1

    private val _tasbihCount: MutableStateFlow<Int> = MutableStateFlow(0)
    val tasbihCount = _tasbihCount.asStateFlow()

    private val _tasbihTotalCount: MutableStateFlow<Int> = MutableStateFlow(33)
    val tasbihTotalCount = _tasbihTotalCount.asStateFlow()

    val tasbihSoundEnabled = settings.getTasbihSoundEnabled()
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    val asmaPreview = settings.getSelectedAsmaPreview()
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = R.drawable.frame_72
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    fun initializeTasbihItem(savedStateHandle: SavedStateHandle,tasbihType: TasbihType) {
        this.savedStateHandle = savedStateHandle

        viewModelScope.launch(Dispatchers.IO) {
            launch {
                savedStateHandle.getStateFlow(DUA_ID, -1).filterNot { it == -1 }.collect { duaId ->
                    val tasbih = tasbihRepo.getTasbihDetailByTasbihTypeAndKey(
                        tasbihType = tasbihType,
                        key = duaId
                    )
                    savedStateHandle[TASBIH_ID] = tasbih?.id
                        ?: tasbihRepo.insert(
                            TasbihModel(
                                count = 0,
                                totalCount = 33,
                                key = duaId,
                                tasbihType = tasbihType,
                                id = 1
                            )
                        )
                }
            }

            launch {
                savedStateHandle.getStateFlow(TASBIH_ID, -1).flatMapLatest { tasbihId ->
                    if (tasbihId != this@TasbihBottomSheetStateViewModel.tasbihId)
                        this@TasbihBottomSheetStateViewModel.tasbihId = tasbihId
                    tasbihRepo.getTasbihCountByTasbihId(
                        id = tasbihId
                    )
                }
                    .filterNotNull()
                    .collect {
                        _tasbihCount.value = it
                    }
            }

            launch {
                savedStateHandle.getStateFlow(TASBIH_ID, -1)
                    .flatMapLatest { tasbihId ->
                        if (tasbihId != this@TasbihBottomSheetStateViewModel.tasbihId)
                            this@TasbihBottomSheetStateViewModel.tasbihId = tasbihId
                        tasbihRepo.getTasbihTotalCountByTasbihId(
                            id = tasbihId
                        )
                    }
                    .filterNotNull()
                    .collect {
                        _tasbihTotalCount.value = it
                    }
            }
        }
    }

    fun onUserEvent(userEvent: UserEvent) {
        when (userEvent) {
            is UserEvent.TasbihCount -> userEvent.count?.let { incrementUserEvent(it) }
            is UserEvent.TasbihTotalCountOption -> tasbihTotalCountOption(userEvent.totalCount)
            is UserEvent.OnSoundOptionChanged -> onSoundOptionChanged(userEvent.options)
            UserEvent.ResetTasbih -> resetTasbih()
        }
    }

    private fun onSoundOptionChanged(isEnabled: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            settings.setTasbihSoundEnabled(isEnabled)
        }
    }

    private fun tasbihTotalCountOption(totalCount: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            tasbihRepo.updateTotalCount(id = tasbihId, count = totalCount)
        }
    }

    private fun resetTasbih() {
        viewModelScope.launch(Dispatchers.IO) {
            tasbihRepo.updateTotalCount(id = tasbihId, count = 33)
            tasbihRepo.updateCount(
                id = tasbihId,
                count = 0
            )
        }
    }

    private fun incrementUserEvent(count: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            tasbihRepo.updateCount(
                id = tasbihId,
                count = count + 1
            )
        }

    fun setAsmaPreview(asmaPreview: Int) {
        viewModelScope.launch { settings.setSelectedAsmaPreview(asmaPreview) }
    }
}

sealed interface UserEvent {
    data class TasbihTotalCountOption(val totalCount: Int) : UserEvent
    data class OnSoundOptionChanged(val options: Boolean) : UserEvent
    data object ResetTasbih : UserEvent
    data class TasbihCount(val count: Int?) : UserEvent
}