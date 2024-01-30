package com.batoulapps.adhan

import com.batoulapps.adhan.PrayerTimes.Companion.daysSinceSolstice
import com.batoulapps.adhan.data.DateComponents
import com.batoulapps.adhan.internal.TestUtils.addSeconds
import com.batoulapps.adhan.internal.TestUtils.getDayOfYear
import com.batoulapps.adhan.internal.TestUtils.makeDate
import com.google.common.truth.Truth
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.TimeZone

class PrayerTimesTest {
    @Test
    fun testDaysSinceSolstice() {
        daysSinceSolsticeTest(11,  /* year */2016,  /* month */1,  /* day */1,  /* latitude */1.0)
        daysSinceSolsticeTest(10,  /* year */2015,  /* month */12,  /* day */31,  /* latitude */1.0)
        daysSinceSolsticeTest(10,  /* year */2016,  /* month */12,  /* day */31,  /* latitude */1.0)
        daysSinceSolsticeTest(0,  /* year */2016,  /* month */12,  /* day */21,  /* latitude */1.0)
        daysSinceSolsticeTest(1,  /* year */2016,  /* month */12,  /* day */22,  /* latitude */1.0)
        daysSinceSolsticeTest(71,  /* year */2016,  /* month */3,  /* day */1,  /* latitude */1.0)
        daysSinceSolsticeTest(70,  /* year */2015,  /* month */3,  /* day */1,  /* latitude */1.0)
        daysSinceSolsticeTest(
            365,  /* year */
            2016,  /* month */
            12,  /* day */
            20,  /* latitude */
            1.0
        )
        daysSinceSolsticeTest(
            364,  /* year */
            2015,  /* month */
            12,  /* day */
            20,  /* latitude */
            1.0
        )
        daysSinceSolsticeTest(0,  /* year */2015,  /* month */6,  /* day */21,  /* latitude */-1.0)
        daysSinceSolsticeTest(0,  /* year */2016,  /* month */6,  /* day */21,  /* latitude */-1.0)
        daysSinceSolsticeTest(
            364,  /* year */
            2015,  /* month */
            6,  /* day */
            20,  /* latitude */
            -1.0
        )
        daysSinceSolsticeTest(
            365,  /* year */
            2016,  /* month */
            6,  /* day */
            20,  /* latitude */
            -1.0
        )
    }

    private fun daysSinceSolsticeTest(
        value: Int,
        year: Int,
        month: Int,
        day: Int,
        latitude: Double
    ) {
        // For Northern Hemisphere start from December 21
        // (DYY=0 for December 21, and counting forward, DYY=11 for January 1 and so on).
        // For Southern Hemisphere start from June 21
        // (DYY=0 for June 21, and counting forward)
        val date = makeDate(year, month, day)
        val dayOfYear = getDayOfYear(date)
        Truth.assertThat(daysSinceSolstice(dayOfYear, date.year, latitude)).isEqualTo(value)
    }

    @Test
    fun testPrayerTimes() {
        val date = DateComponents(2015, 7, 12)
        val params = CalculationMethod.NORTH_AMERICA.parameters
        params!!.madhab = Madhab.HANAFI
        val coordinates = Coordinates(35.7750, -78.6336)
        val prayerTimes = PrayerTimes(coordinates, date, params)
        val formatter = SimpleDateFormat("hh:mm a, ss.SSS")
        formatter.timeZone = TimeZone.getTimeZone("America/New_York")
        Truth.assertThat(formatter.format(prayerTimes.fajr)).isEqualTo("04:42 AM, 00.000")
        Truth.assertThat(formatter.format(prayerTimes.sunrise)).isEqualTo("06:08 AM, 00.000")
        Truth.assertThat(formatter.format(prayerTimes.dhuhr)).isEqualTo("01:21 PM, 00.000")
        Truth.assertThat(formatter.format(prayerTimes.asr)).isEqualTo("06:22 PM, 00.000")
        Truth.assertThat(formatter.format(prayerTimes.maghrib)).isEqualTo("08:32 PM, 00.000")
        Truth.assertThat(formatter.format(prayerTimes.isha)).isEqualTo("09:57 PM, 00.000")
    }

    @Test
    fun testOffsets() {
        val date = DateComponents(2015, 12, 1)
        val coordinates = Coordinates(35.7750, -78.6336)
        val formatter = SimpleDateFormat("hh:mm a, ss.SSS")
        formatter.timeZone = TimeZone.getTimeZone("America/New_York")
        val parameters = CalculationMethod.MUSLIM_WORLD_LEAGUE.parameters
        var prayerTimes = PrayerTimes(coordinates, date, parameters)
        Truth.assertThat(formatter.format(prayerTimes.fajr)).isEqualTo("05:35 AM, 00.000")
        Truth.assertThat(formatter.format(prayerTimes.sunrise)).isEqualTo("07:06 AM, 00.000")
        Truth.assertThat(formatter.format(prayerTimes.dhuhr)).isEqualTo("12:05 PM, 00.000")
        Truth.assertThat(formatter.format(prayerTimes.asr)).isEqualTo("02:42 PM, 00.000")
        Truth.assertThat(formatter.format(prayerTimes.maghrib)).isEqualTo("05:01 PM, 00.000")
        Truth.assertThat(formatter.format(prayerTimes.isha)).isEqualTo("06:26 PM, 00.000")
        parameters!!.adjustments.fajr = 10
        parameters.adjustments.sunrise = 10
        parameters.adjustments.dhuhr = 10
        parameters.adjustments.asr = 10
        parameters.adjustments.maghrib = 10
        parameters.adjustments.isha = 10
        prayerTimes = PrayerTimes(coordinates, date, parameters)
        Truth.assertThat(formatter.format(prayerTimes.fajr)).isEqualTo("05:45 AM, 00.000")
        Truth.assertThat(formatter.format(prayerTimes.sunrise)).isEqualTo("07:16 AM, 00.000")
        Truth.assertThat(formatter.format(prayerTimes.dhuhr)).isEqualTo("12:15 PM, 00.000")
        Truth.assertThat(formatter.format(prayerTimes.asr)).isEqualTo("02:52 PM, 00.000")
        Truth.assertThat(formatter.format(prayerTimes.maghrib)).isEqualTo("05:11 PM, 00.000")
        Truth.assertThat(formatter.format(prayerTimes.isha)).isEqualTo("06:36 PM, 00.000")
        parameters.adjustments = PrayerAdjustments()
        prayerTimes = PrayerTimes(coordinates, date, parameters)
        Truth.assertThat(formatter.format(prayerTimes.fajr)).isEqualTo("05:35 AM, 00.000")
        Truth.assertThat(formatter.format(prayerTimes.sunrise)).isEqualTo("07:06 AM, 00.000")
        Truth.assertThat(formatter.format(prayerTimes.dhuhr)).isEqualTo("12:05 PM, 00.000")
        Truth.assertThat(formatter.format(prayerTimes.asr)).isEqualTo("02:42 PM, 00.000")
        Truth.assertThat(formatter.format(prayerTimes.maghrib)).isEqualTo("05:01 PM, 00.000")
        Truth.assertThat(formatter.format(prayerTimes.isha)).isEqualTo("06:26 PM, 00.000")
    }

    @Test
    fun testMoonsightingMethod() {
        val date = DateComponents(2016, 1, 31)
        val coordinates = Coordinates(35.7750, -78.6336)
        val prayerTimes = PrayerTimes(
            coordinates, date, CalculationMethod.MOON_SIGHTING_COMMITTEE.parameters
        )
        val formatter = SimpleDateFormat("hh:mm a, ss.SSS")
        formatter.timeZone = TimeZone.getTimeZone("America/New_York")
        Truth.assertThat(formatter.format(prayerTimes.fajr)).isEqualTo("05:48 AM, 00.000")
        Truth.assertThat(formatter.format(prayerTimes.sunrise)).isEqualTo("07:16 AM, 00.000")
        Truth.assertThat(formatter.format(prayerTimes.dhuhr)).isEqualTo("12:33 PM, 00.000")
        Truth.assertThat(formatter.format(prayerTimes.asr)).isEqualTo("03:20 PM, 00.000")
        Truth.assertThat(formatter.format(prayerTimes.maghrib)).isEqualTo("05:43 PM, 00.000")
        Truth.assertThat(formatter.format(prayerTimes.isha)).isEqualTo("07:05 PM, 00.000")
    }

    @Test
    fun testMoonsightingMethodHighLat() {
        // Values from http://www.moonsighting.com/pray.php
        val date = DateComponents(2016, 1, 1)
        val parameters = CalculationMethod.MOON_SIGHTING_COMMITTEE.parameters
        parameters!!.madhab = Madhab.HANAFI
        val coordinates = Coordinates(59.9094, 10.7349)
        val prayerTimes = PrayerTimes(coordinates, date, parameters)
        val formatter = SimpleDateFormat("hh:mm a, ss.SSS")
        formatter.timeZone = TimeZone.getTimeZone("Europe/Oslo")
        Truth.assertThat(formatter.format(prayerTimes.fajr)).isEqualTo("07:34 AM, 00.000")
        Truth.assertThat(formatter.format(prayerTimes.sunrise)).isEqualTo("09:19 AM, 00.000")
        Truth.assertThat(formatter.format(prayerTimes.dhuhr)).isEqualTo("12:25 PM, 00.000")
        Truth.assertThat(formatter.format(prayerTimes.asr)).isEqualTo("01:36 PM, 00.000")
        Truth.assertThat(formatter.format(prayerTimes.maghrib)).isEqualTo("03:25 PM, 00.000")
        Truth.assertThat(formatter.format(prayerTimes.isha)).isEqualTo("05:02 PM, 00.000")
    }

    @Test
    fun testTimeForPrayer() {
        val components = DateComponents(2016, 7, 1)
        val parameters = CalculationMethod.MUSLIM_WORLD_LEAGUE.parameters
        parameters!!.madhab = Madhab.HANAFI
        parameters.highLatitudeRule = HighLatitudeRule.TWILIGHT_ANGLE
        val coordinates = Coordinates(59.9094, 10.7349)
        val p = PrayerTimes(coordinates, components, parameters)
        Truth.assertThat(p.fajr).isEqualTo(p.timeForPrayer(Prayer.FAJR))
        Truth.assertThat(p.sunrise).isEqualTo(p.timeForPrayer(Prayer.SUNRISE))
        Truth.assertThat(p.dhuhr).isEqualTo(p.timeForPrayer(Prayer.DHUHR))
        Truth.assertThat(p.asr).isEqualTo(p.timeForPrayer(Prayer.ASR))
        Truth.assertThat(p.maghrib).isEqualTo(p.timeForPrayer(Prayer.MAGHRIB))
        Truth.assertThat(p.isha).isEqualTo(p.timeForPrayer(Prayer.ISHA))
        Truth.assertThat(p.timeForPrayer(Prayer.NONE)).isNull()
    }

    @Test
    fun testCurrentPrayer() {
        val components = DateComponents(2015, 9, 1)
        val parameters = CalculationMethod.KARACHI.parameters
        parameters!!.madhab = Madhab.HANAFI
        parameters.highLatitudeRule = HighLatitudeRule.TWILIGHT_ANGLE
        val coordinates = Coordinates(33.720817, 73.090032)
        val p = PrayerTimes(coordinates, components, parameters)
        Truth.assertThat(p.currentPrayer(addSeconds(p.fajr, -1))).isEqualTo(Prayer.NONE)
        Truth.assertThat(p.currentPrayer(p.fajr!!)).isEqualTo(Prayer.FAJR)
        Truth.assertThat(p.currentPrayer(addSeconds(p.fajr, 1))).isEqualTo(Prayer.FAJR)
        Truth.assertThat(p.currentPrayer(addSeconds(p.sunrise, 1))).isEqualTo(Prayer.SUNRISE)
        Truth.assertThat(p.currentPrayer(addSeconds(p.dhuhr, 1))).isEqualTo(Prayer.DHUHR)
        Truth.assertThat(p.currentPrayer(addSeconds(p.asr, 1))).isEqualTo(Prayer.ASR)
        Truth.assertThat(p.currentPrayer(addSeconds(p.maghrib, 1))).isEqualTo(Prayer.MAGHRIB)
        Truth.assertThat(p.currentPrayer(addSeconds(p.isha, 1))).isEqualTo(Prayer.ISHA)
    }

    @Test
    fun testNextPrayer() {
        val components = DateComponents(2015, 9, 1)
        val parameters = CalculationMethod.KARACHI.parameters
        parameters!!.madhab = Madhab.HANAFI
        parameters.highLatitudeRule = HighLatitudeRule.TWILIGHT_ANGLE
        val coordinates = Coordinates(33.720817, 73.090032)
        val p = PrayerTimes(coordinates, components, parameters)
        Truth.assertThat(p.nextPrayer(addSeconds(p.fajr, -1))).isEqualTo(Prayer.FAJR)
        Truth.assertThat(p.nextPrayer(p.fajr!!)).isEqualTo(Prayer.SUNRISE)
        Truth.assertThat(p.nextPrayer(addSeconds(p.fajr, 1))).isEqualTo(Prayer.SUNRISE)
        Truth.assertThat(p.nextPrayer(addSeconds(p.sunrise, 1))).isEqualTo(Prayer.DHUHR)
        Truth.assertThat(p.nextPrayer(addSeconds(p.dhuhr, 1))).isEqualTo(Prayer.ASR)
        Truth.assertThat(p.nextPrayer(addSeconds(p.asr, 1))).isEqualTo(Prayer.MAGHRIB)
        Truth.assertThat(p.nextPrayer(addSeconds(p.maghrib, 1))).isEqualTo(Prayer.ISHA)
        Truth.assertThat(p.nextPrayer(addSeconds(p.isha, 1))).isEqualTo(Prayer.NONE)
    }
}
