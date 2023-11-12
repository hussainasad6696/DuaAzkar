package  com.mera.islam.duaazkar.presentation.landing_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import  com.mera.islam.duaazkar.core.ACTION_DAILY_DUA_REMINDER
import  com.mera.islam.duaazkar.core.ACTION_PRAYER_DUA_REMINDER
import  com.mera.islam.duaazkar.core.Settings
import  com.mera.islam.duaazkar.core.dailyDuaReminderId
import  com.mera.islam.duaazkar.core.dailyPrayerReminderId
import  com.mera.islam.duaazkar.core.extensions.nextDayNoon
import com.mera.islam.duaazkar.core.presentation.arabic_with_translation.ArabicWithTranslationStateListener
import com.mera.islam.duaazkar.core.utils.LoadingResources
import  com.mera.islam.duaazkar.core.utils.alarmManager.AlarmScheduler
import  com.mera.islam.duaazkar.core.utils.prayerTimes.PrayerUtils
import com.mera.islam.duaazkar.domain.usecases.GetBookmarkedDuasWithTranslationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class LandingScreenViewModel @Inject constructor(
    getBookmarkedDuasWithTranslationsUseCase: GetBookmarkedDuasWithTranslationsUseCase,
    private val alarmScheduler: AlarmScheduler,
    private val prayers: PrayerUtils,
    settings: Settings,
) : ViewModel() {


    val allBookmarkedDuasWithTranslations = getBookmarkedDuasWithTranslationsUseCase()
        .map {
            LoadingResources.SuccessList(it)
        }
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = LoadingResources.Loading
        )

    val settingsDuaLastRead = settings.getDuaLastReadId()
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = -1
        )

    val duaTypeWithCount = getBookmarkedDuasWithTranslationsUseCase.duaRepo.getAllDuaTypesAndCounts()
        .map {
            LoadingResources.SuccessList(it)
        }
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = LoadingResources.Loading
        )

    init {
        prayers.setPrayerTimeListener { localDateTime, prayer ->
            if (!alarmScheduler.isAlarmAlreadySchedule(
                    dailyPrayerReminderId,
                    ACTION_PRAYER_DUA_REMINDER
                )
            ) {
                alarmScheduler.scheduleAlarm(
                    localDateTime = localDateTime,
                    action = ACTION_PRAYER_DUA_REMINDER,
                    alarmId = dailyPrayerReminderId,
                    Pair("prayerName", prayer.name)
                )
            }
        }

        viewModelScope.launch { prayers.calculatePrayer() }
    }

    fun setAlarmForTomorrow() {
        if (!alarmScheduler.isAlarmAlreadySchedule(dailyDuaReminderId, ACTION_DAILY_DUA_REMINDER)) {
            alarmScheduler.scheduleAlarm(
                localDateTime = LocalDateTime.now().nextDayNoon(),
                action = ACTION_DAILY_DUA_REMINDER,
                alarmId = dailyDuaReminderId
            )
        }
    }

}