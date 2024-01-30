package com.batoulapps.adhan.internal

import com.batoulapps.adhan.Coordinates
import com.batoulapps.adhan.data.CalendarUtil.isLeapYear
import com.batoulapps.adhan.data.DateComponents
import com.batoulapps.adhan.data.TimeComponents.Companion.fromDouble
import com.batoulapps.adhan.internal.Astronomical.altitudeOfCelestialBody
import com.batoulapps.adhan.internal.Astronomical.apparentObliquityOfTheEcliptic
import com.batoulapps.adhan.internal.Astronomical.apparentSolarLongitude
import com.batoulapps.adhan.internal.Astronomical.approximateTransit
import com.batoulapps.adhan.internal.Astronomical.ascendingLunarNodeLongitude
import com.batoulapps.adhan.internal.Astronomical.correctedHourAngle
import com.batoulapps.adhan.internal.Astronomical.correctedTransit
import com.batoulapps.adhan.internal.Astronomical.interpolate
import com.batoulapps.adhan.internal.Astronomical.interpolateAngles
import com.batoulapps.adhan.internal.Astronomical.meanLunarLongitude
import com.batoulapps.adhan.internal.Astronomical.meanObliquityOfTheEcliptic
import com.batoulapps.adhan.internal.Astronomical.meanSiderealTime
import com.batoulapps.adhan.internal.Astronomical.meanSolarAnomaly
import com.batoulapps.adhan.internal.Astronomical.meanSolarLongitude
import com.batoulapps.adhan.internal.Astronomical.nutationInLongitude
import com.batoulapps.adhan.internal.Astronomical.nutationInObliquity
import com.batoulapps.adhan.internal.Astronomical.solarEquationOfTheCenter
import com.batoulapps.adhan.internal.CalendricalHelper.julianCentury
import com.batoulapps.adhan.internal.CalendricalHelper.julianDay
import com.batoulapps.adhan.internal.DoubleUtil.unwindAngle
import com.batoulapps.adhan.internal.TestUtils.makeDate
import com.google.common.truth.Truth
import org.junit.Test
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AstronomicalTest {
    @Test
    fun testSolarCoordinates() {

        // values from Astronomical Algorithms page 165
        var jd = julianDay( /* year */1992,  /* month */10,  /* day */13)
        var solar = SolarCoordinates( /* julianDay */jd)
        var T = julianCentury( /* julianDay */jd)
        var L0 = meanSolarLongitude( /* julianCentury */T)
        var ε0 = meanObliquityOfTheEcliptic( /* julianCentury */T)
        val εapp = apparentObliquityOfTheEcliptic( /* julianCentury */
            T,  /* meanObliquityOfTheEcliptic */ε0
        )
        val M = meanSolarAnomaly( /* julianCentury */T)
        val C = solarEquationOfTheCenter( /* julianCentury */
            T,  /* meanAnomaly */M
        )
        val λ = apparentSolarLongitude( /* julianCentury */
            T,  /* meanLongitude */L0
        )
        val δ = solar.declination
        val α = unwindAngle(solar.rightAscension)
        Truth.assertThat(T).isWithin(0.00000000001).of(-0.072183436)
        Truth.assertThat(L0).isWithin(0.00001).of(201.80720)
        Truth.assertThat(ε0).isWithin(0.00001).of(23.44023)
        Truth.assertThat(εapp).isWithin(0.00001).of(23.43999)
        Truth.assertThat(M).isWithin(0.00001).of(278.99397)
        Truth.assertThat(C).isWithin(0.00001).of(-1.89732)

        // lower accuracy than desired
        Truth.assertThat(λ).isWithin(0.00002).of(199.90895)
        Truth.assertThat(δ).isWithin(0.00001).of(-7.78507)
        Truth.assertThat(α).isWithin(0.00001).of(198.38083)

        // values from Astronomical Algorithms page 88
        jd = julianDay( /* year */1987,  /* month */4,  /* day */10)
        solar = SolarCoordinates( /* julianDay */jd)
        T = julianCentury( /* julianDay */jd)
        val θ0 = meanSiderealTime( /* julianCentury */T)
        val θapp = solar.apparentSiderealTime
        val Ω = ascendingLunarNodeLongitude( /* julianCentury */T)
        ε0 = meanObliquityOfTheEcliptic( /* julianCentury */T)
        L0 = meanSolarLongitude( /* julianCentury */T)
        val Lp = meanLunarLongitude( /* julianCentury */T)
        val ΔΨ = nutationInLongitude( /* julianCentury */T,  /* solarLongitude */
            L0,  /* lunarLongitude */Lp,  /* ascendingNode */Ω
        )
        val Δε = nutationInObliquity( /* julianCentury */T,  /* solarLongitude */
            L0,  /* lunarLongitude */Lp,  /* ascendingNode */Ω
        )
        val ε = ε0 + Δε
        Truth.assertThat(θ0).isWithin(0.000001).of(197.693195)
        Truth.assertThat(θapp).isWithin(0.0001).of(197.6922295833)

        // values from Astronomical Algorithms page 148
        Truth.assertThat(Ω).isWithin(0.0001).of(11.2531)
        Truth.assertThat(ΔΨ).isWithin(0.0001).of(-0.0010522)
        Truth.assertThat(Δε).isWithin(0.00001).of(0.0026230556)
        Truth.assertThat(ε0).isWithin(0.000001).of(23.4409463889)
        Truth.assertThat(ε).isWithin(0.00001).of(23.4435694444)
    }

    @Test
    fun testRightAscensionEdgeCase() {
        var previousTime: SolarTime? = null
        val coordinates = Coordinates(35 + 47.0 / 60.0, -78 - 39.0 / 60.0)
        for (i in 0..364) {
            val time = SolarTime(
                TestUtils.makeDateWithOffset(2016, 1, 1, i, Calendar.DAY_OF_YEAR), coordinates
            )
            if (i > 0) {
                // transit from one day to another should not differ more than one minute
                Truth.assertThat(Math.abs(time.transit - previousTime!!.transit))
                    .isLessThan(1.0 / 60.0)

                // sunrise and sunset from one day to another should not differ more than two minutes
                Truth.assertThat(Math.abs(time.sunrise - previousTime.sunrise))
                    .isLessThan(2.0 / 60.0)
                Truth.assertThat(Math.abs(time.sunset - previousTime.sunset)).isLessThan(2.0 / 60.0)
            }
            previousTime = time
        }
    }

    @Test
    fun testAltitudeOfCelestialBody() {
        val φ = 38 + 55 / 60.0 + 17.0 / 3600
        val δ = -6 - 43 / 60.0 - 11.61 / 3600
        val H = 64.352133
        val h = altitudeOfCelestialBody( /* observerLatitude */
            φ,  /* declination */δ,  /* localHourAngle */H
        )
        Truth.assertThat(h).isWithin(0.0001).of(15.1249)
    }

    @Test
    fun testTransitAndHourAngle() {
        // values from Astronomical Algorithms page 103
        val longitude = -71.0833
        val Θ = 177.74208
        val α1 = 40.68021
        val α2 = 41.73129
        val α3 = 42.78204
        val m0 = approximateTransit(
            longitude,  /* siderealTime */
            Θ,  /* rightAscension */α2
        )
        Truth.assertThat(m0).isWithin(0.00001).of(0.81965)
        val transit = correctedTransit( /* approximateTransit */
            m0, longitude,  /* siderealTime */Θ,  /* rightAscension */
            α2,  /* previousRightAscension */α1,  /* nextRightAscension */
            α3
        ) / 24
        Truth.assertThat(transit).isWithin(0.00001).of(0.81980)
        val δ1 = 18.04761
        val δ2 = 18.44092
        val δ3 = 18.82742
        val rise = correctedHourAngle( /* approximateTransit */m0,  /* angle */
            -0.5667, Coordinates( /* latitude */42.3333, longitude),  /* afterTransit */
            false,  /* siderealTime */Θ,  /* rightAscension */
            α2,  /* previousRightAscension */α1,  /* nextRightAscension */
            α3,  /* declination */δ2,  /* previousDeclination */
            δ1,  /* nextDeclination */δ3
        ) / 24
        Truth.assertThat(rise).isWithin(0.00001).of(0.51766)
    }

    @Test
    fun testSolarTime() {
        /*
     * Comparison values generated from
     * http://aa.usno.navy.mil/rstt/onedaytable?form=1&ID=AA&year=2015&month=7&day=12&state=NC&place=raleigh
     */
        val coordinates = Coordinates(35 + 47.0 / 60.0, -78 - 39.0 / 60.0)
        val solar = SolarTime(DateComponents(2015, 7, 12), coordinates)
        val transit = solar.transit
        val sunrise = solar.sunrise
        val sunset = solar.sunset
        val twilightStart = solar.hourAngle(-6.0,  /* afterTransit */false)
        val twilightEnd = solar.hourAngle(-6.0,  /* afterTransit */true)
        val invalid = solar.hourAngle(-36.0,  /* afterTransit */true)
        Truth.assertThat(timeString(twilightStart)).isEqualTo("9:38")
        Truth.assertThat(timeString(sunrise)).isEqualTo("10:08")
        Truth.assertThat(timeString(transit)).isEqualTo("17:20")
        Truth.assertThat(timeString(sunset)).isEqualTo("24:32")
        Truth.assertThat(timeString(twilightEnd)).isEqualTo("25:02")
        Truth.assertThat(timeString(invalid)).isEqualTo("")
    }

    private fun timeString(`when`: Double): String {
        val components = fromDouble(`when`) ?: return ""
        val minutes = (components.minutes + Math.round(components.seconds / 60.0)).toInt()
        return String.format(Locale.US, "%d:%02d", components.hours, minutes)
    }

    @Test
    fun testCalendricalDate() {
        // generated from http://aa.usno.navy.mil/data/docs/RS_OneYear.php for KUKUIHAELE, HAWAII
        val coordinates = Coordinates( /* latitude */
            20 + 7.0 / 60.0,  /* longitude */-155.0 - 34.0 / 60.0
        )
        val day1solar = SolarTime(DateComponents(2015, 4,  /* day */2), coordinates)
        val day2solar = SolarTime(DateComponents(2015, 4, 3), coordinates)
        val day1 = day1solar.sunrise
        val day2 = day2solar.sunrise
        Truth.assertThat(timeString(day1)).isEqualTo("16:15")
        Truth.assertThat(timeString(day2)).isEqualTo("16:14")
    }

    @Test
    fun testInterpolation() {
        // values from Astronomical Algorithms page 25
        val interpolatedValue = interpolate( /* value */0.877366,  /* previousValue */
            0.884226,  /* nextValue */0.870531,  /* factor */4.35 / 24
        )
        Truth.assertThat(interpolatedValue).isWithin(0.000001).of(0.876125)
        val i1 = interpolate( /* value */
            1.0,  /* previousValue */-1.0,  /* nextValue */3.0,  /* factor */0.6
        )
        Truth.assertThat(i1).isWithin(0.000001).of(2.2)
    }

    @Test
    fun testAngleInterpolation() {
        val i1 = interpolateAngles( /* value */1.0,  /* previousValue */-1.0,  /* nextValue */
            3.0,  /* factor */0.6
        )
        Truth.assertThat(i1).isWithin(0.000001).of(2.2)
        val i2 = interpolateAngles( /* value */1.0,  /* previousValue */359.0,  /* nextValue */
            3.0,  /* factor */0.6
        )
        Truth.assertThat(i2).isWithin(0.000001).of(2.2)
    }

    @Test
    fun testJulianDay() {
        /*
     * Comparison values generated from http://aa.usno.navy.mil/data/docs/JulianDate.php
     */
        Truth.assertThat(julianDay( /* year */2010,  /* month */1,  /* day */2))
            .isWithin(0.00001).of(2455198.500000)
        Truth.assertThat(julianDay( /* year */2011,  /* month */2,  /* day */4))
            .isWithin(0.00001).of(2455596.500000)
        Truth.assertThat(julianDay( /* year */2012,  /* month */3,  /* day */6))
            .isWithin(0.00001).of(2455992.500000)
        Truth.assertThat(julianDay( /* year */2013,  /* month */4,  /* day */8))
            .isWithin(0.00001).of(2456390.500000)
        Truth.assertThat(julianDay( /* year */2014,  /* month */5,  /* day */10))
            .isWithin(0.00001).of(2456787.500000)
        Truth.assertThat(julianDay( /* year */2015,  /* month */6,  /* day */12))
            .isWithin(0.00001).of(2457185.500000)
        Truth.assertThat(julianDay( /* year */2016,  /* month */7,  /* day */14))
            .isWithin(0.00001).of(2457583.500000)
        Truth.assertThat(julianDay( /* year */2017,  /* month */8,  /* day */16))
            .isWithin(0.00001).of(2457981.500000)
        Truth.assertThat(julianDay( /* year */2018,  /* month */9,  /* day */18))
            .isWithin(0.00001).of(2458379.500000)
        Truth.assertThat(julianDay( /* year */2019,  /* month */10,  /* day */20))
            .isWithin(0.00001).of(2458776.500000)
        Truth.assertThat(julianDay( /* year */2020,  /* month */11,  /* day */22))
            .isWithin(0.00001).of(2459175.500000)
        Truth.assertThat(julianDay( /* year */2021,  /* month */12,  /* day */24))
            .isWithin(0.00001).of(2459572.500000)
        val jdVal = 2457215.67708333
        Truth.assertThat(
            julianDay( /* year */2015,  /* month */7,  /* day */12,  /* hours */4.25)
        )
            .isWithin(0.000001).of(jdVal)
        val components: Date = makeDate( /* year */2015,  /* month */7,  /* day */12,  /* hour */
            4,  /* minute */15
        )
        Truth.assertThat(julianDay(components)).isWithin(0.000001).of(jdVal)
        Truth.assertThat(julianDay( /* year */2015,  /* month */7,  /* day */12,  /* hours */8.0))
            .isWithin(0.000001)
            .of(2457215.833333)
        Truth.assertThat(julianDay( /* year */1992,  /* month */10,  /* day */13,  /* hours */0.0))
            .isWithin(0.000001)
            .of(2448908.5)
    }

    @Test
    fun testJulianHours() {
        val j1 = julianDay( /* year */2010,  /* month */1,  /* day */3)
        val j2 = julianDay( /* year */2010,  /* month */
            1,  /* day */1,  /* hours */48.0
        )
        Truth.assertThat(j1).isWithin(0.0000001).of(j2)
    }

    @Test
    fun testLeapYear() {
        Truth.assertThat(isLeapYear(2015)).isFalse()
        Truth.assertThat(isLeapYear(2016)).isTrue()
        Truth.assertThat(isLeapYear(1600)).isTrue()
        Truth.assertThat(isLeapYear(2000)).isTrue()
        Truth.assertThat(isLeapYear(2400)).isTrue()
        Truth.assertThat(isLeapYear(1700)).isFalse()
        Truth.assertThat(isLeapYear(1800)).isFalse()
        Truth.assertThat(isLeapYear(1900)).isFalse()
        Truth.assertThat(isLeapYear(2100)).isFalse()
        Truth.assertThat(isLeapYear(2200)).isFalse()
        Truth.assertThat(isLeapYear(2300)).isFalse()
        Truth.assertThat(isLeapYear(2500)).isFalse()
        Truth.assertThat(isLeapYear(2600)).isFalse()
    }
}