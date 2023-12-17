package  com.mera.islam.duaazkar.core.presentation.arabic_with_translation

import androidx.compose.ui.unit.sp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mera.islam.duaazkar.core.Settings
import com.mera.islam.duaazkar.core.enums.TasbihType
import com.mera.islam.duaazkar.core.utils.fonts.ArabicFonts
import com.mera.islam.duaazkar.domain.models.TasbihModel
import com.mera.islam.duaazkar.domain.repo.TasbihRepo
import com.mera.islam.duaazkar.presentation.dua_tasbih_screen.DUA_ID
import com.mera.islam.duaazkar.presentation.dua_tasbih_screen.UserEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ArabicWithTranslationStateListener(
    override val coroutineContext: CoroutineContext,
    settings: Settings
) : CoroutineScope {
    val textSize = settings.getDuaTextSize()
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = this,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = 0.sp
        )

    val arabicFont = settings.getArabicFont()
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = this,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = ArabicFonts.AL_QALAM_QURAN.font()
        )
}

class TasbihStateListener(
    override val coroutineContext: CoroutineContext,
    private val savedStateHandle: SavedStateHandle,
    private val tasbihRepo: TasbihRepo,
    private val settings: Settings
) : CoroutineScope {
    private var tasbihId = -1

    @OptIn(ExperimentalCoroutinesApi::class)
    val tasbihCount = savedStateHandle.getStateFlow(TASBIH_ID, -1)
        .flatMapLatest { tasbihId ->
            if (tasbihId != this.tasbihId)
                this.tasbihId = tasbihId
            tasbihRepo.getTasbihCountByTasbihId(
                id = tasbihId
            )
        }
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = this,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = 0
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val tasbihTotalCount = savedStateHandle.getStateFlow(TASBIH_ID, -1)
        .flatMapLatest { tasbihId ->
            if (tasbihId != this.tasbihId)
                this.tasbihId = tasbihId
            tasbihRepo.getTasbihTotalCountByTasbihId(
                id = tasbihId
            )
        }
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = this,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = 33
        )

    val tasbihSoundEnabled = settings.getTasbihSoundEnabled()
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = this,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    fun setDuaId(duaId: Int) {
        savedStateHandle[DUA_ID] = duaId

        this.launch {
            val tasbih = tasbihRepo.getTasbihDetailByTasbihTypeAndKey(
                tasbihType = TasbihType.Dua,
                key = duaId
            )
            savedStateHandle[TASBIH_ID] = tasbih?.id
                ?: tasbihRepo.insert(
                    TasbihModel(
                        count = 0,
                        totalCount = 33,
                        key = duaId,
                        tasbihType = TasbihType.Dua,
                        id = 1
                    )
                )
        }
    }

    fun onSoundOptionChanged(userEvent: UserEvent.OnSoundOptionChanged) {
        this.launch(Dispatchers.IO) {
            settings.setTasbihSoundEnabled(userEvent.options)
        }
    }

    fun tasbihTotalCountOption(userEvent: UserEvent.TasbihTotalCountOption) {
        this.launch(Dispatchers.IO) {
            tasbihRepo.updateTotalCount(id = tasbihId, count = userEvent.totalCount)
        }
    }

    fun resetTasbih() {
        this.launch(Dispatchers.IO) {
            tasbihRepo.updateTotalCount(id = tasbihId, count = 33)
            tasbihRepo.updateCount(
                id = tasbihId,
                count = 0
            )
        }
    }

    fun incrementUserEvent(userEvent: UserEvent.TasbihCount) =
        this.launch(Dispatchers.IO) {
            val count = userEvent.count ?: 0
            tasbihRepo.updateCount(
                id = tasbihId,
                count = count + 1
            )
        }
}

const val TASBIH_ID = "tasbihId"