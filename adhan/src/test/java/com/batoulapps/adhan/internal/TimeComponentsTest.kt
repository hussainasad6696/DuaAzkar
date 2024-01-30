package com.batoulapps.adhan.internal

import com.batoulapps.adhan.data.CalendarUtil.roundedMinute
import com.batoulapps.adhan.data.TimeComponents.Companion.fromDouble
import com.google.common.truth.Truth
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.TimeZone

class TimeComponentsTest {
    @Test
    fun testTimeComponents() {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        val comps1 = fromDouble(15.199)
        Truth.assertThat(comps1).isNotNull()
        val date1 = comps1!!.dateComponents(TestUtils.getDateComponents("2023-04-03"))
        Truth.assertThat(comps1.hours).isEqualTo(15)
        Truth.assertThat(comps1.minutes).isEqualTo(11)
        Truth.assertThat(comps1.seconds).isEqualTo(56)
        Truth.assertThat(formatter.format(date1)).isEqualTo("2023-04-03 15:11:56.000")
        val comps2 = fromDouble(1.0084)
        Truth.assertThat(comps2).isNotNull()
        val date2 = comps2!!.dateComponents(TestUtils.getDateComponents("2020-11-19"))
        Truth.assertThat(comps2.hours).isEqualTo(1)
        Truth.assertThat(comps2.minutes).isEqualTo(0)
        Truth.assertThat(comps2.seconds).isEqualTo(30)
        Truth.assertThat(formatter.format(date2)).isEqualTo("2020-11-19 01:00:30.000")
        val comps3 = fromDouble(1.0083)
        Truth.assertThat(comps3).isNotNull()
        val date3 = comps3!!.dateComponents(TestUtils.getDateComponents("2023-07-08"))
        Truth.assertThat(comps3.hours).isEqualTo(1)
        Truth.assertThat(comps3.minutes).isEqualTo(0)
        Truth.assertThat(formatter.format(date3)).isEqualTo("2023-07-08 01:00:29.000")
        val comps4 = fromDouble(2.1)
        Truth.assertThat(comps4).isNotNull()
        val date4 = comps4!!.dateComponents(TestUtils.getDateComponents("2023-01-02"))
        Truth.assertThat(comps4.hours).isEqualTo(2)
        Truth.assertThat(comps4.minutes).isEqualTo(6)
        Truth.assertThat(formatter.format(date4)).isEqualTo("2023-01-02 02:06:00.000")
        val comps5 = fromDouble(3.5)
        Truth.assertThat(comps5).isNotNull()
        val date5 = comps5!!.dateComponents(TestUtils.getDateComponents("2019-06-23"))
        Truth.assertThat(comps5.hours).isEqualTo(3)
        Truth.assertThat(comps5.minutes).isEqualTo(30)
        Truth.assertThat(formatter.format(date5)).isEqualTo("2019-06-23 03:30:00.000")
    }

    @Test
    fun testMinuteRounding() {
        val comps1 = TestUtils.makeDate(2015, 1, 1, 10, 2, 29)
        val rounded1 = roundedMinute(comps1)
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.time = rounded1
        Truth.assertThat(calendar[Calendar.MINUTE]).isEqualTo(2)
        Truth.assertThat(calendar[Calendar.SECOND]).isEqualTo(0)
        val comps2 = TestUtils.makeDate(2015, 1, 1, 10, 2, 31)
        val rounded2 = roundedMinute(comps2)
        calendar.time = rounded2
        Truth.assertThat(calendar[Calendar.MINUTE]).isEqualTo(3)
        Truth.assertThat(calendar[Calendar.SECOND]).isEqualTo(0)
    }
}
