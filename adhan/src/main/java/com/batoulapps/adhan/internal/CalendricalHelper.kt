package com.batoulapps.adhan.internal

import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.TimeZone

internal object CalendricalHelper {
    /**
     * The Julian Day for a given date
     * @param date the date
     * @return the julian day
     */
    @JvmStatic
    fun julianDay(date: Date?): Double {
        val calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.time = date
        return julianDay(
            calendar[Calendar.YEAR],
            calendar[Calendar.MONTH] + 1, calendar[Calendar.DAY_OF_MONTH],
            calendar[Calendar.HOUR_OF_DAY] + calendar[Calendar.MINUTE] / 60.0
        )
    }
    /**
     * The Julian Day for a given Gregorian date
     * @param year the year
     * @param month the month
     * @param day the day
     * @param hours hours
     * @return the julian day
     */
    /**
     * The Julian Day for a given Gregorian date
     * @param year the year
     * @param month the month
     * @param day the day
     * @return the julian day
     */
    @JvmStatic
    @JvmOverloads
    fun julianDay(year: Int, month: Int, day: Int, hours: Double = 0.0): Double {
        /* Equation from Astronomical Algorithms page 60 */

        // NOTE: Integer conversion is done intentionally for the purpose of decimal truncation
        val Y = if (month > 2) year else year - 1
        val M = if (month > 2) month else month + 12
        val D = day + hours / 24
        val A = Y / 100
        val B = 2 - A + A / 4
        val i0 = (365.25 * (Y + 4716)).toInt()
        val i1 = (30.6001 * (M + 1)).toInt()
        return i0 + i1 + D + B - 1524.5
    }

    /**
     * Julian century from the epoch.
     * @param JD the julian day
     * @return the julian century from the epoch
     */
    @JvmStatic
    fun julianCentury(JD: Double): Double {
        /* Equation from Astronomical Algorithms page 163 */
        return (JD - 2451545.0) / 36525
    }
}