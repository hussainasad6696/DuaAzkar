package com.mera.islam.duaazkar.presentation.dua_listing_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mera.islam.duaazkar.core.substitution.ArabicWithTranslation
import com.mera.islam.duaazkar.core.utils.Resources
import com.mera.islam.duaazkar.domain.models.DuaType
import com.mera.islam.duaazkar.domain.usecases.DuaByIdsWithTranslationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DuaListingScreenViewModel @Inject constructor(
    private val duaWithTranslationByIds: DuaByIdsWithTranslationUseCase
) : ViewModel() {

    private val _duasHeadingList: MutableStateFlow<Resources<ArabicWithTranslation>> =
        MutableStateFlow(Resources.Loading)
    val duaHeadingList = _duasHeadingList.asStateFlow()

    private val _title: MutableStateFlow<String> = MutableStateFlow("Duas")
    val title = _title.asStateFlow()

    fun loadDuaByIds(duaIds: List<Int>) = viewModelScope.launch(Dispatchers.IO) {
        duaWithTranslationByIds(duaIds.toList()).collect {
            _duasHeadingList.value = Resources.SuccessList(it)
            _title.value = it.map { it.getDataType() as DuaType }.distinctBy { it.type }.map { it.getName() }.joinToString(" / ")
        }
    }

}