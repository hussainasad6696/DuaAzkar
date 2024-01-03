package com.mera.islam.duaazkar.presentation.dua_tasbih_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mera.islam.duaazkar.core.Settings
import com.mera.islam.duaazkar.core.presentation.arabic_with_translation.CustomTextStateListener
import com.mera.islam.duaazkar.core.utils.UiStates
import com.mera.islam.duaazkar.domain.usecases.GetDuaByIdWithTranslationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DuaTasbihScreenViewModel @Inject constructor(
    private val getDuaByIdWithTranslationUseCase: GetDuaByIdWithTranslationUseCase,
    val savedStateHandle: SavedStateHandle,
    settings: Settings
) : ViewModel() {

    val customTextStateListener =
        CustomTextStateListener(
            coroutineContext = viewModelScope.coroutineContext,
            settings = settings
        )


    @OptIn(ExperimentalCoroutinesApi::class)
    val duaWithTranslation = savedStateHandle.getStateFlow(DUA_ID, -1)
        .flatMapLatest { duaId ->
            getDuaByIdWithTranslationUseCase(duaId)
        }
        .map { UiStates.Success(it) }
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiStates.Loading
        )

}

const val DUA_ID = "duaId"