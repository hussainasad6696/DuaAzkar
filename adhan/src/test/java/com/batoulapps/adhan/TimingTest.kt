package com.batoulapps.adhan

import com.batoulapps.adhan.data.TimingFile
import com.batoulapps.adhan.data.TimingParameters
import com.batoulapps.adhan.internal.TestUtils.getDateComponents
import com.google.common.truth.Truth
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import okio.buffer
import okio.source
import org.junit.Before
import org.junit.Test
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

class TimingTest {
    private var jsonAdapter: JsonAdapter<TimingFile>? = null
    @Before
    fun setup() {
        val moshi = Moshi.Builder().build()
        jsonAdapter = moshi.adapter(TimingFile::class.java)
    }

    @Test
    @Throws(Exception::class)
    fun testTimes() {
        val timingDirectory = File(PATH)
        val files = timingDirectory.listFiles { pathname -> pathname.name.endsWith(".json") }
        for (timingFile in files) {
            testTimingFile(timingFile)
        }
    }

    @Throws(Exception::class)
    private fun testTimingFile(jsonFile: File) {
        println("testing timings for " + jsonFile.name)
        val timingFile = jsonAdapter!!.fromJson(jsonFile.source().buffer())
        Truth.assertThat(timingFile).isNotNull()
        val coordinates = Coordinates(
            timingFile!!.params!!.latitude, timingFile.params!!.longitude
        )
        val parameters = parseParameters(timingFile.params)
        for (info in timingFile.timingInfos) {
            val dateComponents = getDateComponents(
                info.date!!
            )
            val prayerTimes = PrayerTimes(coordinates, dateComponents, parameters)
            val fajrDifference =
                getDifferenceInMinutes(prayerTimes.fajr, info.fajr, timingFile.params!!.timezone)
            Truth.assertThat(fajrDifference).isAtMost(timingFile.variance)
            val sunriseDifference = getDifferenceInMinutes(
                prayerTimes.sunrise,
                info.sunrise,
                timingFile.params!!.timezone
            )
            Truth.assertThat(sunriseDifference).isAtMost(timingFile.variance)
            val dhuhrDifference =
                getDifferenceInMinutes(prayerTimes.dhuhr, info.dhuhr, timingFile.params!!.timezone)
            Truth.assertThat(dhuhrDifference).isAtMost(timingFile.variance)
            val asrDifference =
                getDifferenceInMinutes(prayerTimes.asr, info.asr, timingFile.params!!.timezone)
            Truth.assertThat(asrDifference).isAtMost(timingFile.variance)
            val maghribDifference = getDifferenceInMinutes(
                prayerTimes.maghrib,
                info.maghrib,
                timingFile.params!!.timezone
            )
            Truth.assertThat(maghribDifference).isAtMost(timingFile.variance)
            val ishaDifference =
                getDifferenceInMinutes(prayerTimes.isha, info.isha, timingFile.params!!.timezone)
            Truth.assertThat(ishaDifference).isAtMost(timingFile.variance)
        }
    }

    private fun getDifferenceInMinutes(
        prayerTime: Date?,
        jsonTime: String?,
        timezone: String?
    ): Long {
        val formatter = SimpleDateFormat("h:mm a")
        formatter.timeZone = TimeZone.getTimeZone(timezone)
        var referenceTime: Date
        try {
            val parsedCalendar = Calendar.getInstance(TimeZone.getTimeZone(timezone))
            parsedCalendar.time = formatter.parse(jsonTime)
            val referenceCalendar = Calendar.getInstance(TimeZone.getTimeZone(timezone))
            referenceCalendar.time = prayerTime
            referenceCalendar[Calendar.HOUR] = parsedCalendar[Calendar.HOUR]
            referenceCalendar[Calendar.MINUTE] = parsedCalendar[Calendar.MINUTE]
            referenceCalendar[Calendar.AM_PM] = parsedCalendar[Calendar.AM_PM]
            referenceTime = referenceCalendar.time
        } catch (e: Exception) {
            referenceTime = Date()
        }
        return Math.abs((prayerTime!!.time - referenceTime.time) / (60 * 1000))
    }

    private fun parseParameters(timingParameters: TimingParameters?): CalculationParameters? {
        val method: CalculationMethod
        method = when (timingParameters!!.method) {
            "MuslimWorldLeague" -> {
                CalculationMethod.MUSLIM_WORLD_LEAGUE
            }

            "Egyptian" -> {
                CalculationMethod.EGYPTIAN
            }

            "Karachi" -> {
                CalculationMethod.KARACHI
            }

            "UmmAlQura" -> {
                CalculationMethod.UMM_AL_QURA
            }

            "Dubai" -> {
                CalculationMethod.DUBAI
            }

            "MoonsightingCommittee" -> {
                CalculationMethod.MOON_SIGHTING_COMMITTEE
            }

            "NorthAmerica" -> {
                CalculationMethod.NORTH_AMERICA
            }

            "Kuwait" -> {
                CalculationMethod.KUWAIT
            }

            "Qatar" -> {
                CalculationMethod.QATAR
            }

            "Singapore" -> {
                CalculationMethod.SINGAPORE
            }

            else -> {
                CalculationMethod.OTHER
            }
        }
        val parameters = method.parameters
        if ("Shafi" == timingParameters.madhab) {
            parameters!!.madhab = Madhab.SHAFI
        } else if ("Hanafi" == timingParameters.madhab) {
            parameters!!.madhab = Madhab.HANAFI
        }
        if ("SeventhOfTheNight" == timingParameters.highLatitudeRule) {
            parameters!!.highLatitudeRule = HighLatitudeRule.SEVENTH_OF_THE_NIGHT
        } else if ("TwilightAngle" == timingParameters.highLatitudeRule) {
            parameters!!.highLatitudeRule = HighLatitudeRule.TWILIGHT_ANGLE
        } else {
            parameters!!.highLatitudeRule = HighLatitudeRule.MIDDLE_OF_THE_NIGHT
        }
        return parameters
    }

    companion object {
        private const val PATH = "../Shared/Times/"
    }
}
