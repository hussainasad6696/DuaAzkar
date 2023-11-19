package com.mera.islam.duaazkar.core.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mera.islam.duaazkar.core.ACTION_DAILY_DUA_REMINDER
import com.mera.islam.duaazkar.core.ACTION_PRAYER_DUA_REMINDER
import com.mera.islam.duaazkar.core.dailyDuaReminderId
import com.mera.islam.duaazkar.core.dailyPrayerReminderId
import com.mera.islam.duaazkar.core.extensions.nextDayNoon
import com.mera.islam.duaazkar.core.utils.alarmManager.AlarmScheduler
import com.mera.islam.duaazkar.core.utils.prayerTimes.PrayerUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

@AndroidEntryPoint
class BootReceiver: BroadcastReceiver() {

    @Inject
    lateinit var alarmScheduler: AlarmScheduler

    @Inject
    lateinit var prayerUtils: PrayerUtils
    override fun onReceive(context: Context?, intent: Intent?) {
        CoroutineScope(Dispatchers.IO).launch {
            alarmScheduler.scheduleAlarm(
                LocalDateTime.now().nextDayNoon(),
                ACTION_DAILY_DUA_REMINDER,
                dailyDuaReminderId
            )

            prayerUtils.calculatePrayer { localDateTime, prayer ->
                alarmScheduler.scheduleAlarm(
                    localDateTime = localDateTime,
                    action = ACTION_PRAYER_DUA_REMINDER,
                    alarmId = dailyPrayerReminderId,
                    Pair("prayerName",prayer.name)
                )
            }
        }
    }
}