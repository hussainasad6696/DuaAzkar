package com.batoulapps.adhan.internal

import com.batoulapps.adhan.data.CalendarUtil.roundedMinute
import com.batoulapps.adhan.data.TimeComponents.Companion.fromDouble
import com.batoulapps.adhan.internal.DoubleUtil.closestAngle
import com.batoulapps.adhan.internal.DoubleUtil.normalizeWithBound
import com.batoulapps.adhan.internal.DoubleUtil.unwindAngle
import com.google.common.truth.Truth
import org.junit.Test
import java.util.Calendar
import java.util.TimeZone

class MathTest {
    @Test
    fun testAngleConversion() {
        Truth.assertThat(Math.toDegrees(Math.PI)).isWithin(0.00001).of(180.0)
        Truth.assertThat(Math.toDegrees(Math.PI / 2)).isWithin(0.00001).of(90.0)
    }

    @Test
    fun testNormalizing() {
        Truth.assertThat(normalizeWithBound(2.0, -5.0)).isWithin(0.00001).of(-3.0)
        Truth.assertThat(normalizeWithBound(-4.0, -5.0)).isWithin(0.00001).of(-4.0)
        Truth.assertThat(normalizeWithBound(-6.0, -5.0)).isWithin(0.00001).of(-1.0)
        Truth.assertThat(normalizeWithBound(-1.0, 24.0)).isWithin(0.00001).of(23.0)
        Truth.assertThat(normalizeWithBound(1.0, 24.0)).isWithin(0.00001).of(1.0)
        Truth.assertThat(normalizeWithBound(49.0, 24.0)).isWithin(0.00001).of(1.0)
        Truth.assertThat(normalizeWithBound(361.0, 360.0)).isWithin(0.00001).of(1.0)
        Truth.assertThat(normalizeWithBound(360.0, 360.0)).isWithin(0.00001).of(0.0)
        Truth.assertThat(normalizeWithBound(259.0, 360.0)).isWithin(0.00001).of(259.0)
        Truth.assertThat(normalizeWithBound(2592.0, 360.0)).isWithin(0.00001).of(72.0)
        Truth.assertThat(unwindAngle(-45.0)).isWithin(0.00001).of(315.0)
        Truth.assertThat(unwindAngle(361.0)).isWithin(0.00001).of(1.0)
        Truth.assertThat(unwindAngle(360.0)).isWithin(0.00001).of(0.0)
        Truth.assertThat(unwindAngle(259.0)).isWithin(0.00001).of(259.0)
        Truth.assertThat(unwindAngle(2592.0)).isWithin(0.00001).of(72.0)
        Truth.assertThat(normalizeWithBound(360.1, 360.0)).isWithin(0.01).of(0.1)
    }

    @Test
    fun testClosestAngle() {
        Truth.assertThat(closestAngle(360.0)).isWithin(0.000001).of(0.0)
        Truth.assertThat(closestAngle(361.0)).isWithin(0.000001).of(1.0)
        Truth.assertThat(closestAngle(1.0)).isWithin(0.000001).of(1.0)
        Truth.assertThat(closestAngle(-1.0)).isWithin(0.000001).of(-1.0)
        Truth.assertThat(closestAngle(-181.0)).isWithin(0.000001).of(179.0)
        Truth.assertThat(closestAngle(180.0)).isWithin(0.000001).of(180.0)
        Truth.assertThat(closestAngle(359.0)).isWithin(0.000001).of(-1.0)
        Truth.assertThat(closestAngle(-359.0)).isWithin(0.000001).of(1.0)
        Truth.assertThat(closestAngle(1261.0)).isWithin(0.000001).of(-179.0)
        Truth.assertThat(closestAngle(-360.1)).isWithin(0.01).of(-0.1)
    }

    @Test
    fun testTimeComponents() {
        val comps1 = fromDouble(15.199)
        Truth.assertThat(comps1).isNotNull()
        Truth.assertThat(comps1!!.hours).isEqualTo(15)
        Truth.assertThat(comps1.minutes).isEqualTo(11)
        Truth.assertThat(comps1.seconds).isEqualTo(56)
        val comps2 = fromDouble(1.0084)
        Truth.assertThat(comps2).isNotNull()
        Truth.assertThat(comps2!!.hours).isEqualTo(1)
        Truth.assertThat(comps2.minutes).isEqualTo(0)
        Truth.assertThat(comps2.seconds).isEqualTo(30)
        val comps3 = fromDouble(1.0083)
        Truth.assertThat(comps3).isNotNull()
        Truth.assertThat(comps3!!.hours).isEqualTo(1)
        Truth.assertThat(comps3.minutes).isEqualTo(0)
        val comps4 = fromDouble(2.1)
        Truth.assertThat(comps4).isNotNull()
        Truth.assertThat(comps4!!.hours).isEqualTo(2)
        Truth.assertThat(comps4.minutes).isEqualTo(6)
        val comps5 = fromDouble(3.5)
        Truth.assertThat(comps5).isNotNull()
        Truth.assertThat(comps5!!.hours).isEqualTo(3)
        Truth.assertThat(comps5.minutes).isEqualTo(30)
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
