package  com.mera.islam.duaazkar.presentation.dua_screen

import androidx.compose.ui.unit.sp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import  com.mera.islam.duaazkar.core.Settings
import  com.mera.islam.duaazkar.core.TEXT_MIN_SIZE
import  com.mera.islam.duaazkar.core.presentation.arabic_with_translation.ArabicWithTranslationStateListener
import  com.mera.islam.duaazkar.core.substitution.ArabicModelWithTranslationModel
import  com.mera.islam.duaazkar.core.utils.EventResources
import  com.mera.islam.duaazkar.core.utils.fonts.FontsType
import  com.mera.islam.duaazkar.core.utils.fonts.LanguageFonts
import  com.mera.islam.duaazkar.domain.models.dua.DuaTranslatorModel
import  com.mera.islam.duaazkar.domain.models.dua.DuaType
import  com.mera.islam.duaazkar.domain.repo.dua.DuaTranslatorRepo
import  com.mera.islam.duaazkar.domain.usecases.DuaLastReadUseCase
import  com.mera.islam.duaazkar.domain.usecases.GetAllDuaWithTranslationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flattenConcat
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DuaScreenViewModel @Inject constructor(
    private val getAllDuaWithTranslationsUseCase: GetAllDuaWithTranslationsUseCase,
    private val translatorRepo: DuaTranslatorRepo,
    private val duaLastReadUseCase: DuaLastReadUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val settings: Settings
) : ViewModel() {

    val arabicWithTranslationStateListener =
        ArabicWithTranslationStateListener(
            coroutineContext = viewModelScope.coroutineContext,
            settings = settings
        )

    val duaTextSize = settings.getDuaTextSize()
    .flowOn(Dispatchers.IO)
    .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = TEXT_MIN_SIZE
    )

    private val _translators: MutableStateFlow<List<DuaTranslatorModelWithSelection>> =
        MutableStateFlow(emptyList())
    val translators = _translators.asStateFlow()

    private val _title: MutableStateFlow<String> = MutableStateFlow("Duas")
    val title = _title.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val allDuaWithTranslations = savedStateHandle.getStateFlow(DUA_TYPE, DuaType.ALL.type)
        .map { getAllDuaWithTranslationsUseCase(DuaType.toDuaType(it)) }
        .flattenConcat()
        .onEach {
            _title.value = it.map { it.getDataType() as DuaType }.distinctBy { it.type }.map { it.getName() }.joinToString(" / ")
        }
        .map { EventResources.SuccessList(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = EventResources.Loading
        )

    init {
        viewModelScope.launch(Dispatchers.IO) {
            translatorRepo.getAllTranslators().collect {
                _translators.value =
                    it.map { DuaTranslatorModelWithSelection(duaTranslatorModel = it) }
            }
        }
    }

    fun loadDuasByDuaType(duaType: DuaType) {
        savedStateHandle[DUA_TYPE] = duaType.type
    }

    fun onUserEvent(userEvent: UserEvent) {
        when (userEvent) {
            is UserEvent.TextSizeChanged -> textSizeChange(userEvent)
            is UserEvent.TranslationsOptionsChanged -> translationsOptionsChanged(userEvent)
            is UserEvent.SelectedFont -> selectedFont(userEvent)
            is UserEvent.IsBookmarked -> isBookmarked(userEvent)
        }
    }

    private fun isBookmarked(userEvent: UserEvent.IsBookmarked) {
        viewModelScope.launch {
            getAllDuaWithTranslationsUseCase.duaRepo.isBookmarked(
                userEvent.bookmarked,
                userEvent.duaId
            )
        }
    }

    private fun selectedFont(userEvent: UserEvent.SelectedFont) {
        viewModelScope.launch {
            when (userEvent.fonts.fontType()) {
                FontsType.ARABIC_FONTS -> settings.setArabicFont(userEvent.fonts.font())
                FontsType.LEFT_FONTS -> settings.setLeftFont(userEvent.fonts.font())
                FontsType.RIGHT_FONTS -> settings.setRightFont(userEvent.fonts.font())
            }
        }
    }

    private fun translationsOptionsChanged(userEvent: UserEvent.TranslationsOptionsChanged) {
        viewModelScope.launch {
            val index =
                translators.value.indexOfFirst { it.duaTranslatorModel.id == userEvent.translatorId }

            if (index != -1) {
                val list = translators.value.toMutableList()
                val indexItem = list[index]
                list[index] = DuaTranslatorModelWithSelection(
                    isSelected = !indexItem.isSelected,
                    duaTranslatorModel = indexItem.duaTranslatorModel
                )

                _translators.value = list

                val translatorIds = list.filter { it.isSelected }
                    .map { it.duaTranslatorModel.id }

                settings.setDuaSelectedTranslationIds(translatorIds.takeIf { it.isNotEmpty() }
                    ?: listOf(-1))
            } else settings.setDuaSelectedTranslationIds(listOf(userEvent.translatorId))
        }
    }

    private var textSizeJob: Job? = null
    private fun textSizeChange(userEvent: UserEvent.TextSizeChanged) {
        textSizeJob?.cancel()
        textSizeJob = viewModelScope.launch {
            settings.setDuaTextSize(userEvent.size.sp)
        }
    }

    fun saveLastRead(firstVisibleItemIndex: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            kotlin.runCatching {
                if (allDuaWithTranslations.value is EventResources.SuccessList)
                    duaLastReadUseCase((allDuaWithTranslations.value as EventResources.SuccessList<ArabicModelWithTranslationModel>).list[firstVisibleItemIndex].getDataId())
            }
        }
    }
}

sealed interface UserEvent {
    data class TextSizeChanged(val size: Float) : UserEvent
    data class TranslationsOptionsChanged(val translatorId: Int) : UserEvent
    data class SelectedFont(val fonts: LanguageFonts) : UserEvent
    data class IsBookmarked(val bookmarked: Boolean, val duaId: Int) : UserEvent
}

data class DuaTranslatorModelWithSelection(
    val isSelected: Boolean = true,
    val duaTranslatorModel: DuaTranslatorModel
)

private const val DUA_TYPE = "duaType"