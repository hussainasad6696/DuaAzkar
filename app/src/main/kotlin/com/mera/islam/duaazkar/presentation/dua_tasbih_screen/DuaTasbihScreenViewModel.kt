package com.mera.islam.duaazkar.presentation.dua_tasbih_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mera.islam.duaazkar.core.Settings
import com.mera.islam.duaazkar.core.enums.TasbihType
import com.mera.islam.duaazkar.core.extensions.log
import com.mera.islam.duaazkar.core.presentation.arabic_with_translation.ArabicWithTranslationStateListener
import com.mera.islam.duaazkar.core.utils.EventResources
import com.mera.islam.duaazkar.domain.models.TasbihModel
import com.mera.islam.duaazkar.domain.repo.TasbihRepo
import com.mera.islam.duaazkar.domain.usecases.GetDuaByIdWithTranslationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DuaTasbihScreenViewModel @Inject constructor(
    private val getDuaByIdWithTranslationUseCase: GetDuaByIdWithTranslationUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val tasbihRepo: TasbihRepo,
    private val settings: Settings
) : ViewModel() {

    val arabicWithTranslationStateListener =
        ArabicWithTranslationStateListener(
            coroutineContext = viewModelScope.coroutineContext,
            settings = settings
        )

    private var tasbihId = -1

    @OptIn(ExperimentalCoroutinesApi::class)
    val duaWithTranslation = savedStateHandle.getStateFlow(DUA_ID, -1)
        .flatMapLatest { duaId ->
            getDuaByIdWithTranslationUseCase(duaId)
        }
        .map { EventResources.Success(it) }
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = EventResources.Loading
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val tasbihCount = savedStateHandle.getStateFlow(TASBIH_ID, -1)
        .flatMapLatest { tasbihId ->
            if (tasbihId != this.tasbihId)
                this.tasbihId = tasbihId
            tasbihRepo.getTasbihCountByTasbihId(
                id = tasbihId
            )
        }
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = 0
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val tasbihTotalCount = savedStateHandle.getStateFlow(TASBIH_ID, -1)
        .flatMapLatest { tasbihId ->
            if (tasbihId != this.tasbihId)
                this.tasbihId = tasbihId
            tasbihRepo.getTasbihTotalCountByTasbihId(
                id = tasbihId
            )
        }
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = 33
        )

    val tasbihSoundEnabled = settings.getTasbihSoundEnabled()
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    fun setDuaId(duaId: Int) {
        savedStateHandle[DUA_ID] = duaId

        viewModelScope.launch {
            val tasbih = tasbihRepo.getTasbihDetailByTasbihTypeAndKey(
                tasbihType = TasbihType.Dua,
                key = duaId
            )
            savedStateHandle[TASBIH_ID] = tasbih?.id
                ?: tasbihRepo.insert(
                    TasbihModel(
                        count = 0,
                        totalCount = 33,
                        key = duaId,
                        tasbihType = TasbihType.Dua,
                        id = 1
                    )
                )
        }
    }

    fun onUserEvent(userEvent: UserEvent) {
        when (userEvent) {
            is UserEvent.TasbihCount -> incrementUserEvent(userEvent)
            is UserEvent.TasbihTotalCountOption -> tasbihTotalCountOption(userEvent)
            is UserEvent.OnSoundOptionChanged -> onSoundOptionChanged(userEvent)
            UserEvent.ResetTasbih -> resetTasbih()
        }
    }

    private fun onSoundOptionChanged(userEvent: UserEvent.OnSoundOptionChanged) {
        viewModelScope.launch(Dispatchers.IO) {
            settings.setTasbihSoundEnabled(userEvent.options)
        }
    }

    private fun tasbihTotalCountOption(userEvent: UserEvent.TasbihTotalCountOption) {
        viewModelScope.launch(Dispatchers.IO) {
            tasbihRepo.updateTotalCount(id = tasbihId, count = userEvent.totalCount)
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

    private fun incrementUserEvent(userEvent: UserEvent.TasbihCount) =
        viewModelScope.launch(Dispatchers.IO) {
            val count = userEvent.count ?: 0
            tasbihRepo.updateCount(
                id = tasbihId,
                count = count + 1
            )
        }
}

sealed interface UserEvent {
    data class TasbihCount(val count: Int?, val duaId: Int) : UserEvent
    data object ResetTasbih : UserEvent
    data class TasbihTotalCountOption(val totalCount: Int, val duaId: Int) : UserEvent
    data class OnSoundOptionChanged(val options: Boolean) : UserEvent
}

private const val DUA_ID = "duaId"
private const val TASBIH_ID = "tasbihId"