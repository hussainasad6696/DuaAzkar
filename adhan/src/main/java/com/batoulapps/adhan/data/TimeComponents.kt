package com.batoulapps.adhan.data

import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.TimeZone
import kotlin.math.floor

class TimeComponents private constructor(@JvmField val hours: Int, @JvmField val minutes: Int, @JvmField val seconds: Int) {
    fun dateComponents(date: DateComponents): Date {
        val calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar[Calendar.YEAR] = date.year
        calendar[Calendar.MONTH] = date.month - 1
        calendar[Calendar.DAY_OF_MONTH] = date.day
        calendar[Calendar.HOUR_OF_DAY] = 0
        calendar[Calendar.MINUTE] = minutes
        calendar[Calendar.SECOND] = seconds
        calendar.add(Calendar.HOUR_OF_DAY, hours)
        calendar[Calendar.MILLISECOND] = 0
        return calendar.time
    }

    companion object {
        @JvmStatic
        fun fromDouble(value: Double): TimeComponents? {
            if (java.lang.Double.isInfinite(value) || java.lang.Double.isNaN(value)) {
                return null
            }
            val hours = floor(value)
            val minutes = floor((value - hours) * 60.0)
            val seconds = floor((value - (hours + minutes / 60.0)) * 60 * 60)
            return TimeComponents(hours.toInt(), minutes.toInt(), seconds.toInt())
        }
    }
}