package com.batoulapps.adhan.data

import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.TimeZone
import kotlin.math.roundToInt

object CalendarUtil {
    /**
     * Whether or not a year is a leap year (has 366 days)
     * @param year the year
     * @return whether or not its a leap year
     */
    @JvmStatic
    fun isLeapYear(year: Int): Boolean {
        return year % 4 == 0 && !(year % 100 == 0 && year % 400 != 0)
    }

    /**
     * Date and time with a rounded minute
     * This returns a date with the seconds rounded and added to the minute
     * @param when the date and time
     * @return the date and time with 0 seconds and minutes including rounded seconds
     */
    @JvmStatic
    fun roundedMinute(`when`: Date?): Date {
        val calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.time = `when`
        val minute = calendar[Calendar.MINUTE].toDouble()
        val second = calendar[Calendar.SECOND].toDouble()
        calendar[Calendar.MINUTE] = (minute + (second / 60).roundToInt()).toInt()
        calendar[Calendar.SECOND] = 0
        return calendar.time
    }

    /**
     * Gets a date for the particular date
     * @param components the date components
     * @return the date with a time set to 00:00:00 at utc
     */
    fun resolveTime(components: DateComponents?): Date {
        return resolveTime(components!!.year, components.month, components.day)
    }

    /**
     * Add the specified amount of a unit of time to a particular date
     * @param when the original date
     * @param amount the amount to add
     * @param field the field to add it to (from [java.util.Calendar]'s fields).
     * @return the date with the offset added
     */
    @JvmStatic
    fun add(`when`: Date?, amount: Int, field: Int): Date {
        val calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.time = `when`
        calendar.add(field, amount)
        return calendar.time
    }

    /**
     * Gets a date for the particular date
     * @param year the year
     * @param month the month
     * @param day the day
     * @return the date with a time set to 00:00:00 at utc
     */
    private fun resolveTime(year: Int, month: Int, day: Int): Date {
        val calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar[year, month - 1, day, 0, 0] = 0
        return calendar.time
    }
}