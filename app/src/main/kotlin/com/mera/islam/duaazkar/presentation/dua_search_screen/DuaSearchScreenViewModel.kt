package com.mera.islam.duaazkar.presentation.dua_search_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mera.islam.duaazkar.core.utils.EventResources
import com.mera.islam.duaazkar.data.local.dao.dua.DuaNameAndCount
import com.mera.islam.duaazkar.domain.repo.dua.DuaRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DuaSearchScreenViewModel @Inject constructor(
    private val duaRepo: DuaRepo,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    @OptIn(FlowPreview::class)
    val searchedDua = savedStateHandle.getStateFlow(DUA_SEARCH, "")
        .map { keyword ->
            EventResources.SuccessList(
                if (keyword.isEmpty()) duaRepo.getAllDuaTypesAndCounts().first()
                else duaRepo.getAllDuaTypesAndCountsByKeyword(keyword).first()
            )
        }
        .distinctUntilChanged()
        .debounce(500L)
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = EventResources.Loading
        )

    val searchedText = savedStateHandle.getStateFlow(DUA_SEARCH,"")

    fun search(keyword: String) {
        savedStateHandle[DUA_SEARCH] = keyword
    }

    suspend fun getKeywords() = savedStateHandle.getStateFlow(DUA_SEARCH, "").first().split(",")
}

private const val DUA_SEARCH = "duaSearch"
