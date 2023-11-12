package  com.mera.islam.duaazkar.core

import android.app.NotificationManager
import android.content.Context
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import  com.mera.islam.duaazkar.BuildConfig
import dagger.hilt.android.qualifiers.ApplicationContext


//ui constants
val TEXT_MIN_SIZE: TextUnit = 16.sp
val TEXT_MAX_SIZE: TextUnit = 32.sp


//util constants
val app = "${BuildConfig.APPLICATION_ID}.action."
val ACTION_DAILY_DUA_REMINDER = app + "DAILY_DUA_REMINDER"
val ACTION_PRAYER_DUA_REMINDER = app + "AFTER_PRAYER_DUA_REMINDER"

//alarmIds
const val dailyDuaReminderId = 1001
const val dailyPrayerReminderId = 1002


//notification constants
const val DAILY_DUA_NOTIFICATION_CHANNEL_ID = "dailyDuaChannelId"
const val DAILY_DUA_NOTIFICATION_CHANNEL = "dailyDuaChannel"


fun notificationManager(context: Context) =
    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager