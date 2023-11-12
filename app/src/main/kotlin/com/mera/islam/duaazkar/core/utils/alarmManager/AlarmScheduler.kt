package  com.mera.islam.duaazkar.core.utils.alarmManager

import org.threeten.bp.LocalDateTime


interface AlarmScheduler {
    fun scheduleAlarm(localDateTime: LocalDateTime, action: String,alarmId: Int,vararg inputStrings: Pair<String,String>)
    fun isAlarmAlreadySchedule(alarmId: Int,action: String): Boolean
    fun cancelAlarmById(alarmId: Int)
}