package com.batoulapps.adhan.data

import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.TimeZone

class DateComponents(val year: Int, val month: Int, val day: Int) {
    companion object {
        /**
         * Convenience method that returns a DateComponents from a given Date
         * @param date the date
         * @return the DateComponents (according to the default device timezone)
         */
        fun from(date: Date?): DateComponents {
            val calendar = GregorianCalendar.getInstance()
            calendar.time = date
            return DateComponents(
                calendar[Calendar.YEAR],
                calendar[Calendar.MONTH] + 1, calendar[Calendar.DAY_OF_MONTH]
            )
        }

        /**
         * Convenience method that returns a DateComponents from a given
         * Date that was constructed from UTC based components
         * @param date the date
         * @return the DateComponents (according to UTC)
         */
        @JvmStatic
        fun fromUTC(date: Date?): DateComponents {
            val calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.time = date
            return DateComponents(
                calendar[Calendar.YEAR],
                calendar[Calendar.MONTH] + 1, calendar[Calendar.DAY_OF_MONTH]
            )
        }
    }
}