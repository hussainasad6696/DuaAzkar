package  com.mera.islam.duaazkar.core.presentation.arabic_with_translation

import androidx.compose.ui.unit.sp
import com.mera.islam.duaazkar.core.Settings
import com.mera.islam.duaazkar.core.utils.fonts.ArabicFonts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
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