package  com.mera.islam.duaazkar.presentation.landing_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import  com.mera.islam.duaazkar.core.ACTION_DAILY_DUA_REMINDER
import  com.mera.islam.duaazkar.core.ACTION_PRAYER_DUA_REMINDER
import  com.mera.islam.duaazkar.core.Settings
import  com.mera.islam.duaazkar.core.dailyDuaReminderId
import  com.mera.islam.duaazkar.core.dailyPrayerReminderId
import com.mera.islam.duaazkar.core.extensions.log
import  com.mera.islam.duaazkar.core.extensions.nextDayNoon
import com.mera.islam.duaazkar.core.utils.Resources
import  com.mera.islam.duaazkar.core.utils.alarmManager.AlarmScheduler
import  com.mera.islam.duaazkar.core.utils.prayerTimes.PrayerUtils
import com.mera.islam.duaazkar.data.local.dao.DuaNameAndCount
import com.mera.islam.duaazkar.domain.models.DuaType
import com.mera.islam.duaazkar.domain.usecases.GetBookmarkedDuasWithTranslationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
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
            Resources.SuccessList(it)
        }
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Resources.Loading
        )

    val duaTypeWithCount =
        getBookmarkedDuasWithTranslationsUseCase.duaRepo.getAllDuaTypesAndCounts()
            .map {
                Resources.SuccessList(it)
            }
            .flowOn(Dispatchers.IO)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = Resources.Loading
            )

    val settingsDuaLastRead = settings.getDuaLastReadId()
        .map {
            if (it != -1)
                Pair(it,
                    getBookmarkedDuasWithTranslationsUseCase.duaRepo.getDuaById(it)
                        .first().duaType.getName()
                )
            else Pair(1, DuaType.ALL.getName())
        }
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Pair(1, DuaType.ALL.getName())
        )

    init {
        viewModelScope.launch {
            prayers.calculatePrayer { localDateTime, prayer ->
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
        }
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