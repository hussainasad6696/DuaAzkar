package com.batoulapps.adhan.internal

import com.batoulapps.adhan.data.CalendarUtil.add
import com.batoulapps.adhan.data.DateComponents
import com.batoulapps.adhan.data.DateComponents.Companion.fromUTC
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.TimeZone

object TestUtils {
    @JvmStatic
    fun makeDate(year: Int, month: Int, day: Int): Date {
        return makeDate(year, month, day, 0, 0, 0)
    }

    @JvmOverloads
    fun makeDate(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int = 0): Date {
        val calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar[year, month - 1, day, hour, minute] = second
        return calendar.time
    }

    @JvmStatic
    fun getDayOfYear(date: Date?): Int {
        val calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.time = date
        return calendar[Calendar.DAY_OF_YEAR]
    }

    @JvmStatic
    fun getDateComponents(date: String): DateComponents {
        val pieces = date.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val year = pieces[0].toInt()
        val month = pieces[1].toInt()
        val day = pieces[2].toInt()
        return DateComponents(year, month, day)
    }

    @JvmStatic
    fun addSeconds(date: Date?, seconds: Int): Date {
        return add(date, seconds, Calendar.SECOND)
    }

    fun makeDateWithOffset(
        year: Int,
        month: Int,
        day: Int,
        offset: Int,
        unit: Int
    ): DateComponents {
        val calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar[year, month - 1] = day
        calendar.add(unit, offset)
        return fromUTC(calendar.time)
    }
}
