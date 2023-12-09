package com.mera.islam.duaazkar.presentation.dua_tasboh_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mera.islam.duaazkar.core.Settings
import com.mera.islam.duaazkar.core.presentation.arabic_with_translation.ArabicWithTranslationStateListener
import com.mera.islam.duaazkar.core.utils.EventResources
import com.mera.islam.duaazkar.domain.usecases.GetDuaByIdWithTranslationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DuaTasbihScreenViewModel @Inject constructor(
    private val getDuaByIdWithTranslationUseCase: GetDuaByIdWithTranslationUseCase,
    private val savedStateHandle: SavedStateHandle,
    settings: Settings
): ViewModel() {

    val arabicWithTranslationStateListener =
        ArabicWithTranslationStateListener(
            coroutineContext = viewModelScope.coroutineContext,
            settings = settings
        )


    val duaWithTranslation = savedStateHandle.getStateFlow(DUA_ID,-1)
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

    fun setDuaId(duaId: Int) {
        savedStateHandle[DUA_ID] = duaId
    }
}

private const val DUA_ID = "duaId"