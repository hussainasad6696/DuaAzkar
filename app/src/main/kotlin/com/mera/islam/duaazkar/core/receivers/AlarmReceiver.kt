package  com.mera.islam.duaazkar.core.receivers

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import  com.mera.islam.duaazkar.R
import  com.mera.islam.duaazkar.core.ACTION_DAILY_DUA_REMINDER
import  com.mera.islam.duaazkar.core.ACTION_PRAYER_DUA_REMINDER
import  com.mera.islam.duaazkar.core.DAILY_DUA_NOTIFICATION_CHANNEL_ID
import  com.mera.islam.duaazkar.core.Settings
import  com.mera.islam.duaazkar.core.dailyDuaReminderId
import  com.mera.islam.duaazkar.core.dailyPrayerReminderId
import  com.mera.islam.duaazkar.core.extensions.nextDayNoon
import  com.mera.islam.duaazkar.core.notificationManager
import  com.mera.islam.duaazkar.core.utils.SdkHelper
import  com.mera.islam.duaazkar.core.utils.alarmManager.AlarmScheduler
import  com.mera.islam.duaazkar.core.utils.prayerTimes.PrayerUtils
import  com.mera.islam.duaazkar.domain.repo.DuaTranslationRepo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var alarmScheduler: AlarmScheduler

    @Inject
    lateinit var settings: Settings

    @Inject
    lateinit var notificationChannel: NotificationChannel

    @Inject
    lateinit var duaTranslationRepo: DuaTranslationRepo

    @Inject
    lateinit var prayerUtils: PrayerUtils

    override fun onReceive(context: Context?, intent: Intent?) {
        CoroutineScope(Dispatchers.IO).launch {
            intent?.action?.let {
                when (it) {
                    ACTION_DAILY_DUA_REMINDER -> {
                        context?.let { dailyDuaNotification(it) }
                        alarmScheduler.scheduleAlarm(
                            LocalDateTime.now().nextDayNoon(),
                            ACTION_DAILY_DUA_REMINDER,
                            dailyDuaReminderId
                        )
                    }
                    ACTION_PRAYER_DUA_REMINDER -> {
                        prayerUtils.setPrayerTimeListener { localDateTime, prayer ->
                            alarmScheduler.scheduleAlarm(
                                localDateTime = localDateTime,
                                action = ACTION_PRAYER_DUA_REMINDER,
                                alarmId = dailyPrayerReminderId,
                                Pair("prayerName",prayer.name)
                            )
                        }

                        intent.getStringExtra("prayerName")?.let { prayer ->
                            context?.let { prayerDuaNotification(prayer = prayer, context = it) }

                            prayerUtils.calculatePrayer()
                        }
                    }
                }
            }
        }
    }

    private fun prayerDuaNotification(context: Context, prayer: String) {

    }

    @SuppressLint("MissingPermission")
    private suspend fun dailyDuaNotification(context: Context) {
        val item = duaTranslationRepo.getRandomDuaWithTranslation(
            settings.getDuaSelectedTranslationIds().first().first()
        )

        if (SdkHelper.isO()) notificationManager(context).createNotificationChannel(
            notificationChannel
        )

        createLargeNotification(
            context = context,
            title = item.duaModel.reason,
            contentTitle = item.duaModel.duaType.getName(),
            bigText =  com.mera.islam.duaazkar.core.extensions.build {
                append(item.duaModel.arabic)
                append("\n")
                append(item.duaModel.translitration)
                append("\n")
                append(item.duaTranslationModel.translation)
                append("\n")
                append(item.duaModel.reason)
                append("\n")
                append("${item.duaModel.referenceType}-${item.duaModel.referenceFrom}")
            },
            notificationId = item.hashCode()
        )
    }

    @SuppressLint("MissingPermission")
    private fun createLargeNotification(context: Context,title: String,contentTitle: String,bigText: String,notificationId: Int) {
        if (SdkHelper.isO()) notificationManager(context).createNotificationChannel(
            notificationChannel
        )

        val builder = NotificationCompat.Builder(context, DAILY_DUA_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_menu_book_24)
            .setContentTitle(title)
            .setContentTitle(contentTitle)
            .setStyle(NotificationCompat.BigTextStyle().bigText(bigText))
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, builder.build())
        }
    }
}