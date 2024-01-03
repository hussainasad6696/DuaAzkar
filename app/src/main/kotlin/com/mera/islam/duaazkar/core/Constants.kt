package  com.mera.islam.duaazkar.core

import android.app.DownloadManager
import android.app.NotificationManager
import android.content.Context
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getSystemService
import  com.mera.islam.duaazkar.BuildConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File


//ui constants
val TEXT_MIN_SIZE: TextUnit = 16.sp
val TEXT_MAX_SIZE: TextUnit = 32.sp


//util constants
const val app = "${BuildConfig.APPLICATION_ID}.action."
const val ACTION_DAILY_DUA_REMINDER = app + "DAILY_DUA_REMINDER"
const val ACTION_PRAYER_DUA_REMINDER = app + "AFTER_PRAYER_DUA_REMINDER"

//alarmIds
const val dailyDuaReminderId = 1001
const val dailyPrayerReminderId = 1002


//notification constants
const val DAILY_DUA_NOTIFICATION_CHANNEL_ID = "dailyDuaChannelId"
const val DAILY_DUA_NOTIFICATION_CHANNEL = "dailyDuaChannel"

inline val Context.notificationManager: NotificationManager
    get() = getSystemService(NotificationManager::class.java)

inline val Context.downloadService: DownloadManager
    get() = getSystemService(DownloadManager::class.java)

//downloadFileAddress
const val DUA_DIR = "duas"
inline val Context.duaDownloadAddress: File
    get() = getExternalFilesDir(DUA_DIR) ?: File(filesDir, DUA_DIR)