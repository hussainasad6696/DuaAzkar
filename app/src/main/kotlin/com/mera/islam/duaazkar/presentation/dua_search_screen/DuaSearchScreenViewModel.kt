package com.mera.islam.duaazkar.presentation.dua_search_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mera.islam.duaazkar.core.utils.Resources
import com.mera.islam.duaazkar.data.local.dao.dua.DuaNameAndCount
import com.mera.islam.duaazkar.domain.repo.dua.DuaRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DuaSearchScreenViewModel @Inject constructor(
    private val duaRepo: DuaRepo
) : ViewModel() {

    private val _searchedDua: MutableStateFlow<Resources<DuaNameAndCount>> =
        MutableStateFlow(Resources.Loading)
    val searchedDua = _searchedDua.asStateFlow()

    private var searchJob: Job? = null
    var keywords = emptyList<String>()

    fun search(keyword: String) {
        if (searchedDua.value !is Resources.Loading)
            _searchedDua.value = Resources.Loading

        keywords = keyword.split(",")

        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            (if (keyword.isEmpty()) duaRepo.getAllDuaTypesAndCounts()
            else duaRepo.getAllDuaTypesAndCountsByKeyword(keyword)).collect {
                _searchedDua.value = Resources.SuccessList(it)
            }
        }
    }
}
