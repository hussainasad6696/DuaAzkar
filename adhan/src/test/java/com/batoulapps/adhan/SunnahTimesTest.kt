package com.batoulapps.adhan

import com.batoulapps.adhan.data.DateComponents
import com.google.common.truth.Truth
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.TimeZone

class SunnahTimesTest {
    @Test
    fun testSunnahTimesNY() {
        val params = CalculationMethod.NORTH_AMERICA.parameters
        val coordinates = Coordinates(35.7750, -78.6336)
        val formatter = SimpleDateFormat("M/d/yy, h:mm a, ss.SSS")
        formatter.timeZone = TimeZone.getTimeZone("America/New_York")
        val todayComponents = DateComponents(2015, 7, 12)
        val todayPrayers = PrayerTimes(coordinates, todayComponents, params)
        Truth.assertThat(formatter.format(todayPrayers.maghrib))
            .isEqualTo("7/12/15, 8:32 PM, 00.000")
        val tomorrowComponents = DateComponents(2015, 7, 13)
        val tomorrowPrayers = PrayerTimes(coordinates, tomorrowComponents, params)
        Truth.assertThat(formatter.format(tomorrowPrayers.fajr))
            .isEqualTo("7/13/15, 4:43 AM, 00.000")

        /*
         Night: 8:32 PM to 4:43 AM
         Duration: 8 hours, 11 minutes
         Middle = 8:32 PM + 4 hours, 5.5 minutes = 12:37:30 AM which rounds to 12:38 AM
         Last Third = 8:32 PM + 5 hours, 27.3 minutes = 1:59:20 AM which rounds to 1:59 AM
        */
        val sunnahTimes = SunnahTimes(todayPrayers)
        Truth.assertThat(formatter.format(sunnahTimes.middleOfTheNight))
            .isEqualTo("7/13/15, 12:38 AM, 00.000")
        Truth.assertThat(formatter.format(sunnahTimes.lastThirdOfTheNight))
            .isEqualTo("7/13/15, 1:59 AM, 00.000")
    }

    @Test
    fun testSunnahTimesLondon() {
        val params = CalculationMethod.MOON_SIGHTING_COMMITTEE.parameters
        val coordinates = Coordinates(51.5074, -0.1278)
        val formatter = SimpleDateFormat("M/d/yy, h:mm a, ss.SSS")
        formatter.timeZone = TimeZone.getTimeZone("Europe/London")
        val todayComponents = DateComponents(2016, 12, 31)
        val todayPrayers = PrayerTimes(coordinates, todayComponents, params)
        Truth.assertThat(formatter.format(todayPrayers.maghrib))
            .isEqualTo("12/31/16, 4:04 PM, 00.000")
        val tomorrowComponents = DateComponents(2017, 1, 1)
        val tomorrowPrayers = PrayerTimes(coordinates, tomorrowComponents, params)
        Truth.assertThat(formatter.format(tomorrowPrayers.fajr))
            .isEqualTo("1/1/17, 6:25 AM, 00.000")

        /*
         Night: 4:04 PM to 6:25 AM
         Duration: 14 hours, 21 minutes
         Middle = 4:04 PM + 7 hours, 10.5 minutes = 11:14:30 PM which rounds to 11:15 PM
         Last Third = 4:04 PM + 9 hours, 34 minutes = 1:38 AM
        */
        val sunnahTimes = SunnahTimes(todayPrayers)
        Truth.assertThat(formatter.format(sunnahTimes.middleOfTheNight))
            .isEqualTo("12/31/16, 11:15 PM, 00.000")
        Truth.assertThat(formatter.format(sunnahTimes.lastThirdOfTheNight))
            .isEqualTo("1/1/17, 1:38 AM, 00.000")
    }

    @Test
    fun testSunnahTimesOslo() {
        val params = CalculationMethod.MUSLIM_WORLD_LEAGUE.parameters
        params!!.highLatitudeRule = HighLatitudeRule.MIDDLE_OF_THE_NIGHT
        val coordinates = Coordinates(59.9094, 10.7349)
        val formatter = SimpleDateFormat("M/d/yy, h:mm a, ss.SSS")
        formatter.timeZone = TimeZone.getTimeZone("Europe/Oslo")
        val todayComponents = DateComponents(2016, 7, 1)
        val todayPrayers = PrayerTimes(coordinates, todayComponents, params)
        Truth.assertThat(formatter.format(todayPrayers.maghrib))
            .isEqualTo("7/1/16, 10:41 PM, 00.000")
        val tomorrowComponents = DateComponents(2016, 7, 2)
        val tomorrowPrayers = PrayerTimes(coordinates, tomorrowComponents, params)
        Truth.assertThat(formatter.format(tomorrowPrayers.fajr))
            .isEqualTo("7/2/16, 1:20 AM, 00.000")

        /*
         Night: 10:41 PM to 1:20 AM
         Duration: 2 hours, 39 minutes
         Middle = 10:41 PM + 1 hours, 19.5 minutes = 12:00:30 AM which rounds to 12:01 AM
         Last Third = 10:41 PM + 1 hours, 46 minutes = 12:27 AM
        */
        val sunnahTimes = SunnahTimes(todayPrayers)
        Truth.assertThat(formatter.format(sunnahTimes.middleOfTheNight))
            .isEqualTo("7/2/16, 12:01 AM, 00.000")
        Truth.assertThat(formatter.format(sunnahTimes.lastThirdOfTheNight))
            .isEqualTo("7/2/16, 12:27 AM, 00.000")
    }

    @Test
    fun testSunnahTimesDST1() {
        val params = CalculationMethod.NORTH_AMERICA.parameters
        val coordinates = Coordinates(37.7749, -122.4194)
        val formatter = SimpleDateFormat("M/d/yy, h:mm a, ss.SSS")
        formatter.timeZone = TimeZone.getTimeZone("America/Los_Angeles")
        val todayComponents = DateComponents(2017, 3, 11)
        val todayPrayers = PrayerTimes(coordinates, todayComponents, params)
        Truth.assertThat(formatter.format(todayPrayers.fajr)).isEqualTo("3/11/17, 5:14 AM, 00.000")
        Truth.assertThat(formatter.format(todayPrayers.maghrib))
            .isEqualTo("3/11/17, 6:13 PM, 00.000")
        val tomorrowComponents = DateComponents(2017, 3, 12)
        val tomorrowPrayers = PrayerTimes(coordinates, tomorrowComponents, params)
        Truth.assertThat(formatter.format(tomorrowPrayers.fajr))
            .isEqualTo("3/12/17, 6:13 AM, 00.000")
        Truth.assertThat(formatter.format(tomorrowPrayers.maghrib))
            .isEqualTo("3/12/17, 7:14 PM, 00.000")

        /*
         Night: 6:13 PM PST to 6:13 AM PDT
         Duration: 11 hours (1 hour is skipped due to DST)
         Middle = 6:13 PM + 5 hours, 30 minutes = 11:43 PM
         Last Third = 6:13 PM + 7 hours, 20 minutes = 1:33 AM
        */
        val sunnahTimes = SunnahTimes(todayPrayers)
        Truth.assertThat(formatter.format(sunnahTimes.middleOfTheNight))
            .isEqualTo("3/11/17, 11:43 PM, 00.000")
        Truth.assertThat(formatter.format(sunnahTimes.lastThirdOfTheNight))
            .isEqualTo("3/12/17, 1:33 AM, 00.000")
    }

    @Test
    fun testSunnahTimesDST2() {
        val params = CalculationMethod.MUSLIM_WORLD_LEAGUE.parameters
        params!!.highLatitudeRule = HighLatitudeRule.SEVENTH_OF_THE_NIGHT
        val coordinates = Coordinates(48.8566, 2.3522)
        val formatter = SimpleDateFormat("M/d/yy, h:mm a, ss.SSS")
        formatter.timeZone = TimeZone.getTimeZone("Europe/Paris")
        val todayComponents = DateComponents(2015, 10, 24)
        val todayPrayers = PrayerTimes(coordinates, todayComponents, params)
        Truth.assertThat(formatter.format(todayPrayers.fajr)).isEqualTo("10/24/15, 6:38 AM, 00.000")
        Truth.assertThat(formatter.format(todayPrayers.maghrib))
            .isEqualTo("10/24/15, 6:45 PM, 00.000")
        val tomorrowComponents = DateComponents(2015, 10, 25)
        val tomorrowPrayers = PrayerTimes(coordinates, tomorrowComponents, params)
        Truth.assertThat(formatter.format(tomorrowPrayers.fajr))
            .isEqualTo("10/25/15, 5:40 AM, 00.000")
        Truth.assertThat(formatter.format(tomorrowPrayers.maghrib))
            .isEqualTo("10/25/15, 5:43 PM, 00.000")

        /*
	     Night: 6:45 PM CEST to 5:40 AM CET
         Duration: 11 hours 55 minutes (1 extra hour is added due to DST)
         Middle = 6:45 PM + 5 hours, 57.5 minutes = 12:42:30 AM which rounds to 12:43 AM
         Last Third = 6:45 PM + 7 hours, 56 minutes, 40 seconds = 2:41:40 AM which rounds to 2:42 AM
        */
        val sunnahTimes = SunnahTimes(todayPrayers)
        Truth.assertThat(formatter.format(sunnahTimes.middleOfTheNight))
            .isEqualTo("10/25/15, 12:43 AM, 00.000")
        Truth.assertThat(formatter.format(sunnahTimes.lastThirdOfTheNight))
            .isEqualTo("10/25/15, 2:42 AM, 00.000")
    }
}