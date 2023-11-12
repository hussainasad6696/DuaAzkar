package  com.mera.islam.duaazkar.presentation.dua_bookmark_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import  com.mera.islam.duaazkar.core.Settings
import  com.mera.islam.duaazkar.core.presentation.arabic_with_translation.ArabicWithTranslationStateListener
import  com.mera.islam.duaazkar.core.utils.LoadingResources
import  com.mera.islam.duaazkar.domain.usecases.GetBookmarkedDuasWithTranslations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DuaBookmarkScreenViewModel @Inject constructor(
    getBookmarkedDuasWithTranslations: GetBookmarkedDuasWithTranslations,
    settings: Settings
) : ViewModel() {

    val arabicWithTranslationStateListener =
        ArabicWithTranslationStateListener(coroutineContext = viewModelScope.coroutineContext, settings = settings)

    val allBookmarkedDuasWithTranslations = getBookmarkedDuasWithTranslations()
    .map {
        LoadingResources.SuccessList(it)
    }
    .flowOn(Dispatchers.IO)
    .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = LoadingResources.Loading
    )

}