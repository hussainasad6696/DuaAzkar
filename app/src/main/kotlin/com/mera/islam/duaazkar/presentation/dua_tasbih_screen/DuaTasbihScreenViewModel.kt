package com.mera.islam.duaazkar.presentation.dua_tasbih_screen

import android.media.MediaPlayer
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
import com.mera.islam.duaazkar.core.presentation.arabic_with_translation.TASBIH_ID
import com.mera.islam.duaazkar.core.presentation.arabic_with_translation.TasbihStateListener
import com.mera.islam.duaazkar.core.utils.EventResources
import com.mera.islam.duaazkar.domain.models.TasbihModel
import com.mera.islam.duaazkar.domain.repo.TasbihRepo
import com.mera.islam.duaazkar.domain.usecases.GetDuaByIdWithTranslationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
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
    savedStateHandle: SavedStateHandle,
    tasbihRepo: TasbihRepo,
    settings: Settings
) : ViewModel() {

    val arabicWithTranslationStateListener =
        ArabicWithTranslationStateListener(
            coroutineContext = viewModelScope.coroutineContext,
            settings = settings
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


    fun onUserEvent(userEvent: UserEvent) {
        when (userEvent) {
            is UserEvent.TasbihCount -> tasbihStateListener.incrementUserEvent(userEvent)
            is UserEvent.TasbihTotalCountOption -> tasbihStateListener.tasbihTotalCountOption(userEvent)
            is UserEvent.OnSoundOptionChanged -> tasbihStateListener.onSoundOptionChanged(userEvent)
            UserEvent.ResetTasbih -> tasbihStateListener.resetTasbih()
        }
    }


}

sealed interface UserEvent {
    data class TasbihCount(val count: Int?, val duaId: Int) : UserEvent
    data object ResetTasbih : UserEvent
    data class TasbihTotalCountOption(val totalCount: Int, val duaId: Int) : UserEvent
    data class OnSoundOptionChanged(val options: Boolean) : UserEvent
}

const val DUA_ID = "duaId"