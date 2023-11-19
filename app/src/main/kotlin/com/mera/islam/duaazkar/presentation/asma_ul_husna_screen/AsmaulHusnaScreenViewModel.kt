package com.mera.islam.duaazkar.presentation.asma_ul_husna_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mera.islam.duaazkar.core.utils.Resources
import com.mera.islam.duaazkar.domain.repo.asmaUlHusna.AsmaulHusnaRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AsmaulHusnaScreenViewModel @Inject constructor(
    private val asmaulHusnaRepo: AsmaulHusnaRepo
): ViewModel() {

    val asma = asmaulHusnaRepo.getAllAsmaulHusna()
        .map {
            Resources.SuccessList(it)
        }
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Resources.Loading
        )
}