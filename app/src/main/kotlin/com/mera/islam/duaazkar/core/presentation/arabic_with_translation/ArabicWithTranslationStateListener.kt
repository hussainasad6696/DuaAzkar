package  com.mera.islam.duaazkar.core.presentation.arabic_with_translation

import androidx.compose.ui.unit.sp
import androidx.lifecycle.SavedStateHandle
import com.mera.islam.duaazkar.core.Settings
import com.mera.islam.duaazkar.core.enums.TasbihType
import com.mera.islam.duaazkar.core.utils.fonts.ArabicFonts
import com.mera.islam.duaazkar.domain.models.TasbihModel
import com.mera.islam.duaazkar.domain.repo.TasbihRepo
import com.mera.islam.duaazkar.presentation.dua_tasbih_screen.DUA_ID
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

const val TASBIH_ID = "tasbihId"