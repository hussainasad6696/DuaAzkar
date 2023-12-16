package  com.mera.islam.duaazkar.core

import android.content.Context
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import com.mera.islam.duaazkar.R
import  com.mera.islam.duaazkar.core.extensions.listToString
import  com.mera.islam.duaazkar.core.utils.fonts.ArabicFonts
import  com.mera.islam.duaazkar.core.utils.fonts.LeftLangFonts
import  com.mera.islam.duaazkar.core.utils.fonts.RightLangFonts
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class Settings @Inject constructor(private val context: Context) {

    private val dataStore: DataStore<Preferences> = PreferenceDataStoreFactory.create {
        context.preferencesDataStoreFile("settings")
    }

    private val keyIsOnBoarding = booleanPreferencesKey("IS_ON_BOARDING")

    suspend fun setIsOnBoarding(isOnBoarding: Boolean) {
        dataStore.edit { setting ->
            setting[keyIsOnBoarding] = isOnBoarding
        }
    }

    fun getIsOnBoarding(): Flow<Boolean> = dataStore.data.map { settings ->
        settings[keyIsOnBoarding] ?: false
    }

    //**************************************************************//

    private val keyDuaSelectedTranslationIds = stringPreferencesKey("DUA_SELECTED_TRANSLATION_IDS")

    suspend fun setDuaSelectedTranslationIds(translatorIds: List<Int>) {
        dataStore.edit { setting ->
            setting[keyDuaSelectedTranslationIds] =
                translatorIds.map { it.toString() }.listToString()
        }
    }

    fun getDuaSelectedTranslationIds(): Flow<List<Int>> = dataStore.data.map { settings ->
        settings[keyDuaSelectedTranslationIds]?.split(",")?.map { it.toInt() } ?: listOf(1, 2)
    }

    //**************************************************************//

    private val keyDuaTextSize = floatPreferencesKey("DUA_TEXT_SIZE")

    suspend fun setDuaTextSize(duaTextSize: TextUnit) {
        if (duaTextSize.value in TEXT_MIN_SIZE.value..TEXT_MAX_SIZE.value)
            dataStore.edit { setting ->
                setting[keyDuaTextSize] = duaTextSize.value
            }
    }

    fun getDuaTextSize(): Flow<TextUnit> = dataStore.data.map { settings ->
        settings[keyDuaTextSize]?.sp ?: TEXT_MIN_SIZE
    }

    //**************************************************************//

    private val keyQuranTextSize = floatPreferencesKey("QURAN_TEXT_SIZE")

    suspend fun setQuranTextSize(quranTextSize: TextUnit) {
        if (quranTextSize.value in TEXT_MIN_SIZE.value..TEXT_MAX_SIZE.value)
            dataStore.edit { setting ->
                setting[keyQuranTextSize] = quranTextSize.value
            }
    }

    fun getQuranTextSize(): Flow<TextUnit> = dataStore.data.map { settings ->
        settings[keyQuranTextSize]?.sp ?: TEXT_MIN_SIZE
    }

    //**************************************************************//

    private val keyHadithTextSize = floatPreferencesKey("HADITH_TEXT_SIZE")

    suspend fun setHadithTextSize(hadithTextSize: TextUnit) {
        if (hadithTextSize.value in TEXT_MIN_SIZE.value..TEXT_MAX_SIZE.value)
            dataStore.edit { setting ->
                setting[keyHadithTextSize] = hadithTextSize.value
            }
    }

    fun getHadithTextSize(): Flow<TextUnit> = dataStore.data.map { settings ->
        settings[keyHadithTextSize]?.sp ?: TEXT_MIN_SIZE
    }

    //**************************************************************//

    private val keyArabicFont = intPreferencesKey("ARABIC_FONT")

    suspend fun setArabicFont(font: Int) {
        dataStore.edit { setting ->
            setting[keyArabicFont] = font
        }
    }

    fun getArabicFont(): Flow<Int> = dataStore.data.map { settings ->
        settings[keyArabicFont] ?: ArabicFonts.AL_QALAM_QURAN.font
    }

    //**************************************************************//

    private val keyLeftFont = intPreferencesKey("LEFT_FONT")

    suspend fun setLeftFont(font: Int) {
        dataStore.edit { setting ->
            setting[keyLeftFont] = font
        }
    }

    fun getLeftFont(): Flow<Int> = dataStore.data.map { settings ->
        settings[keyLeftFont] ?: LeftLangFonts.ROBOTO.font
    }

    //**************************************************************//

    private val keyRightFont = intPreferencesKey("RIGHT_FONT")

    suspend fun setRightFont(font: Int) {
        dataStore.edit { setting ->
            setting[keyRightFont] = font
        }
    }

    fun getRightFont(): Flow<Int> = dataStore.data.map { settings ->
        settings[keyRightFont] ?: RightLangFonts.JAMEEL_NOORI_URDU.font
    }

    //**************************************************************//

    private val keyDuaLastRead = intPreferencesKey("DUA_LAST_READ")

    suspend fun setDuaLastReadId(duaId: Int) {
        dataStore.edit { setting ->
            setting[keyDuaLastRead] = duaId
        }
    }

    fun getDuaLastReadId(): Flow<Int> = dataStore.data.map { settings ->
        settings[keyDuaLastRead] ?: -1
    }

    //**************************************************************//

    private val keyDuaTheme = intPreferencesKey("DUA_THEME")

    suspend fun setDuaTheme(theme: Int) {
        dataStore.edit { setting ->
            setting[keyDuaTheme] = theme
        }
    }

    fun getDuaTheme(): Flow<Int> = dataStore.data.map { settings ->
        settings[keyDuaTheme] ?: R.drawable.ic_white_theme
    }

    //**************************************************************//

    private val keyDuaScreenBrightness = floatPreferencesKey("DUA_SCREEN_BRIGHTNESS")

    suspend fun setDuaScreenBrightness(brightness: Float) {
        dataStore.edit { setting ->
            setting[keyDuaScreenBrightness] = brightness
        }
    }

    fun getDuaScreenBrightness(): Flow<Float> = dataStore.data.map { settings ->
        settings[keyDuaScreenBrightness] ?: 0.5f
    }

    //**************************************************************//

    private val keyDuaKeepScreenOn = booleanPreferencesKey("DUA_KEEP_SCREEN_ON")

    suspend fun setDuaKeepScreenOn(keepScreenOn: Boolean) {
        dataStore.edit { setting ->
            setting[keyDuaKeepScreenOn] = keepScreenOn
        }
    }

    fun getDuaKeepScreenOn(): Flow<Boolean> = dataStore.data.map { settings ->
        settings[keyDuaKeepScreenOn] ?: false
    }

    //**************************************************************//

    private val keyTasbihSoundEnabled = booleanPreferencesKey("TASBIH_SOUND_ENABLED")

    suspend fun setTasbihSoundEnabled(tasbihSoundEnabled: Boolean) {
        dataStore.edit { setting ->
            setting[keyTasbihSoundEnabled] = tasbihSoundEnabled
        }
    }

    fun getTasbihSoundEnabled(): Flow<Boolean> = dataStore.data.map { settings ->
        settings[keyTasbihSoundEnabled] ?: false
    }

    //**************************************************************//

    private val keySelectedAsmaPreview = intPreferencesKey("SELECTED_ASMA_PREVIEW")

    suspend fun setSelectedAsmaPreview(selectedAsmaPreview: Int) {
        dataStore.edit { setting ->
            setting[keySelectedAsmaPreview] = selectedAsmaPreview
        }
    }

    fun getSelectedAsmaPreview(): Flow<Int> = dataStore.data.map { settings ->
        settings[keySelectedAsmaPreview] ?: R.drawable.frame_72
    }
}