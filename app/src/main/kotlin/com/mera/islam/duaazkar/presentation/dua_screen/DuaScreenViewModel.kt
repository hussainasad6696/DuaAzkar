package  com.mera.islam.duaazkar.presentation.dua_screen

import androidx.compose.ui.unit.sp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mera.islam.duaazkar.NavControllerRoutes
import com.mera.islam.duaazkar.R
import  com.mera.islam.duaazkar.core.Settings
import com.mera.islam.duaazkar.core.enums.LanguageDirection
import  com.mera.islam.duaazkar.core.presentation.arabic_with_translation.CustomTextStateListener
import  com.mera.islam.duaazkar.core.utils.UiStates
import com.mera.islam.duaazkar.core.utils.SystemBrightnessSettings
import  com.mera.islam.duaazkar.core.utils.fonts.FontsType
import  com.mera.islam.duaazkar.core.utils.fonts.LanguageFonts
import com.mera.islam.duaazkar.core.utils.fonts.LeftLangFonts
import com.mera.islam.duaazkar.core.utils.fonts.RightLangFonts
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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class DuaScreenViewModel @Inject constructor(
    private val getAllDuaWithTranslationsUseCase: GetAllDuaWithTranslationsUseCase,
    private val translatorRepo: DuaTranslatorRepo,
    private val duaLastReadUseCase: DuaLastReadUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val systemBrightnessSettings: SystemBrightnessSettings,
    private val settings: Settings
) : ViewModel() {

    private val _uiEvent: MutableSharedFlow<UiEvent> = MutableSharedFlow()
    val uiEvent = _uiEvent.asSharedFlow()

    val customTextStateListener =
        CustomTextStateListener(
            coroutineContext = viewModelScope.coroutineContext,
            settings = settings
        )

    val duaTypeWithCount =
        getAllDuaWithTranslationsUseCase.duaRepo.getAllDuaTypesAndCounts()
            .map {
                UiStates.Success(it)
            }
            .flowOn(Dispatchers.IO)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = UiStates.Loading
            )

    private val _translators: MutableStateFlow<List<DuaTranslatorModelWithSelection>> =
        MutableStateFlow(emptyList())
    val translators = _translators.asStateFlow()

    private val _title: MutableStateFlow<String> = MutableStateFlow("Duas")
    val title = _title.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val allDuaWithTranslations = savedStateHandle.getStateFlow(DUA_ARGS, "")
        .flatMapLatest {
            val duaType = Json.decodeFromString<NavControllerRoutes.DuaScreen.DuaScreenArgs>(it)
            getAllDuaWithTranslationsUseCase(duaType.duaType)
        }
        .onEach {
            _title.value =
                it.map { it.getDataType() as DuaType }.distinctBy { it.type }
                    .joinToString(" / ") { it.getName() }
        }
        .map { UiStates.Success(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiStates.Loading
        )

    val selectedTheme = settings.getDuaTheme()
        .distinctUntilChanged()
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = R.drawable.ic_white_theme
        )

    val leftFont = settings.getLeftFont()
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = LeftLangFonts.ROBOTO.font
        )

    val rightFont = settings.getRightFont()
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = RightLangFonts.JAMEEL_NOORI_URDU.font
        )

    val screenBrightness = settings.getDuaScreenBrightness()
        .onEach { brightness ->
            if (hasWriteSettingsPermission())
                systemBrightnessSettings.changeScreenBrightness(brightness)
        }
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = 0.5f
        )

    val keepScreenOn = settings.getDuaKeepScreenOn()
        .distinctUntilChanged()
        .onEach {
            _uiEvent.emit(UiEvent.KeepScreenOn(it))
        }
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    init {
        viewModelScope.launch(Dispatchers.IO) {
            translatorRepo.getAllTranslators().collect {
                _translators.value =
                    it.map { DuaTranslatorModelWithSelection(duaTranslatorModel = it) }
            }
        }
    }

    fun loadDuasByDuaType(args: NavControllerRoutes.DuaScreen.DuaScreenArgs) {
        savedStateHandle[DUA_ARGS] = Json.encodeToString(args)
    }

    fun onUserEvent(userEvent: UserEvent) {
        when (userEvent) {
            is UserEvent.TextSizeChanged -> textSizeChange(userEvent)
            is UserEvent.TranslationsOptionsChanged -> translationsOptionsChanged(userEvent)
            is UserEvent.SelectedFont -> selectedFont(userEvent)
            is UserEvent.IsBookmarked -> isBookmarked(userEvent)
            is UserEvent.SelectedTheme -> setSelectedTheme(userEvent)
            is UserEvent.ChangeSystemBrightness -> changeSystemBrightness(userEvent)
            is UserEvent.KeepScreenOn -> keepScreenOn(userEvent)
        }
    }

    private fun keepScreenOn(userEvent: UserEvent.KeepScreenOn) {
        viewModelScope.launch {
            settings.setDuaKeepScreenOn(userEvent.on)
        }
    }

    private fun changeSystemBrightness(userEvent: UserEvent.ChangeSystemBrightness) {
        viewModelScope.launch {
            settings.setDuaScreenBrightness(userEvent.brightness)
        }
    }

    private fun setSelectedTheme(userEvent: UserEvent.SelectedTheme) {
        viewModelScope.launch {
            settings.setDuaTheme(userEvent.theme)
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
                    isSelected = userEvent.isSelected,
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

    fun hasWriteSettingsPermission(): Boolean = systemBrightnessSettings.hasWriteSettingsPermission()

    fun saveLastRead(firstVisibleItemIndex: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            kotlin.runCatching {
                if (allDuaWithTranslations.value is UiStates.Success)
                    duaLastReadUseCase((allDuaWithTranslations.value as UiStates.Success).template[firstVisibleItemIndex].getDataId())
            }
        }
    }

    fun requestWriteSettingsPermission() {
        systemBrightnessSettings.changeWriteSettingsPermission()
    }

}

sealed interface UserEvent {
    data class TextSizeChanged(val size: Float) : UserEvent
    data class TranslationsOptionsChanged(val translatorId: Int, val isSelected: Boolean) : UserEvent
    data class SelectedFont(val fonts: LanguageFonts) : UserEvent
    data class IsBookmarked(val bookmarked: Boolean, val duaId: Int) : UserEvent
    data class SelectedTheme(val theme: Int) : UserEvent
    data class ChangeSystemBrightness(val brightness: Float) : UserEvent
    data class KeepScreenOn(val on: Boolean) : UserEvent
}

sealed interface UiEvent {
    data class KeepScreenOn(val on: Boolean) : UiEvent
}

data class DuaTranslatorModelWithSelection(
    val isSelected: Boolean = true,
    val duaTranslatorModel: DuaTranslatorModel
) {
    fun getLanguageFont(languageDirection: LanguageDirection,fontId: Int): LanguageFonts {
        return when(languageDirection) {
            LanguageDirection.RIGHT -> RightLangFonts.getLanguageFont(fontId)
            LanguageDirection.LEFT -> LeftLangFonts.getLanguageFont(fontId)
        }
    }
}

private const val DUA_ARGS = "args"