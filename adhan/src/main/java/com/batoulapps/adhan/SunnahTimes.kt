package com.batoulapps.adhan

import com.batoulapps.adhan.data.CalendarUtil
import com.batoulapps.adhan.data.DateComponents
import java.util.Calendar
import java.util.Date

class SunnahTimes(prayerTimes: PrayerTimes) {
    /* The midpoint between Maghrib and Fajr */
    @JvmField
    val middleOfTheNight: Date?

    /* The beginning of the last third of the period between Maghrib and Fajr,
     a recommended time to perform Qiyam */
    @JvmField
    val lastThirdOfTheNight: Date?

    init {
        val currentPrayerTimesDate = CalendarUtil.resolveTime(prayerTimes.dateComponents)
        val tomorrowPrayerTimesDate = CalendarUtil.add(currentPrayerTimesDate, 1, Calendar.DATE)
        val tomorrowPrayerTimes = PrayerTimes(
            prayerTimes.coordinates,
            DateComponents.Companion.fromUTC(tomorrowPrayerTimesDate),
            prayerTimes.calculationParameters
        )
        val nightDurationInSeconds =
            ((tomorrowPrayerTimes.fajr!!.time - prayerTimes.maghrib!!.time) / 1000).toInt()
        middleOfTheNight = CalendarUtil.roundedMinute(
            CalendarUtil.add(
                prayerTimes.maghrib,
                (nightDurationInSeconds / 2.0).toInt(),
                Calendar.SECOND
            )
        )
        lastThirdOfTheNight = CalendarUtil.roundedMinute(
            CalendarUtil.add(
                prayerTimes.maghrib, (nightDurationInSeconds * (2.0 / 3.0)).toInt(),
                Calendar.SECOND
            )
        )
    }
}