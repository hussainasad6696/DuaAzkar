package com.batoulapps.adhan

import com.batoulapps.adhan.data.CalendarUtil
import com.batoulapps.adhan.data.DateComponents
import com.batoulapps.adhan.data.TimeComponents
import com.batoulapps.adhan.internal.SolarTime
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.TimeZone
import kotlin.math.abs

class PrayerTimes(
    val coordinates: Coordinates?,
    val dateComponents: DateComponents,
    val calculationParameters: CalculationParameters?
) {
    @JvmField
    var fajr: Date? = null
    @JvmField
    var sunrise: Date? = null
    @JvmField
    var dhuhr: Date? = null
    @JvmField
    var asr: Date? = null
    @JvmField
    var maghrib: Date? = null
    @JvmField
    var isha: Date? = null

    /**
     * Calculate PrayerTimes
     * @param coordinates the coordinates of the location
     * @param date the date components for that location
     * @param parameters the parameters for the calculation
     */
    init {
        var tempFajr: Date? = null
        var tempSunrise: Date? = null
        var tempDhuhr: Date? = null
        var tempAsr: Date? = null
        var tempMaghrib: Date? = null
        var tempIsha: Date? = null
        val prayerDate = CalendarUtil.resolveTime(dateComponents)
        val calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.time = prayerDate
        val dayOfYear = calendar[Calendar.DAY_OF_YEAR]
        val tomorrowDate = CalendarUtil.add(prayerDate, 1, Calendar.DATE)
        val tomorrow: DateComponents = DateComponents.Companion.fromUTC(tomorrowDate)
        val solarTime = SolarTime(dateComponents, coordinates)
        var timeComponents: TimeComponents? = TimeComponents.fromDouble(solarTime.transit)
        val transit = timeComponents?.dateComponents(
            dateComponents
        )
        timeComponents = TimeComponents.fromDouble(solarTime.sunrise)
        val sunriseComponents = timeComponents?.dateComponents(
            dateComponents
        )
        timeComponents = TimeComponents.fromDouble(solarTime.sunset)
        val sunsetComponents = timeComponents?.dateComponents(
            dateComponents
        )
        val tomorrowSolarTime = SolarTime(tomorrow, coordinates)
        val tomorrowSunriseComponents: TimeComponents? =
            TimeComponents.Companion.fromDouble(tomorrowSolarTime.sunrise)
        val error =
            transit == null || sunriseComponents == null || sunsetComponents == null || tomorrowSunriseComponents == null
        if (!error) {
            tempDhuhr = transit
            tempSunrise = sunriseComponents
            tempMaghrib = sunsetComponents
            timeComponents = TimeComponents.fromDouble(
                solarTime.afternoon(calculationParameters!!.madhab.shadowLength)
            )
            if (timeComponents != null) {
                tempAsr = timeComponents.dateComponents(dateComponents)
            }

            // get night length
            val tomorrowSunrise = tomorrowSunriseComponents?.dateComponents(tomorrow)
            val night = tomorrowSunrise!!.time - sunsetComponents!!.time
            timeComponents = TimeComponents.Companion.fromDouble(
                solarTime.hourAngle(
                    -calculationParameters.fajrAngle,
                    false
                )
            )
            if (timeComponents != null) {
                tempFajr = timeComponents.dateComponents(dateComponents)
            }
            if (calculationParameters.method == CalculationMethod.MOON_SIGHTING_COMMITTEE &&
                coordinates!!.latitude >= 55
            ) {
                tempFajr = CalendarUtil.add(
                    sunriseComponents, -1 * (night / 7000).toInt(), Calendar.SECOND
                )
            }
            val nightPortions = calculationParameters.nightPortions()
            val safeFajr: Date? = if (calculationParameters.method == CalculationMethod.MOON_SIGHTING_COMMITTEE) {
                    seasonAdjustedMorningTwilight(
                        coordinates!!.latitude,
                        dayOfYear,
                        dateComponents.year,
                        sunriseComponents
                    )
                } else {
                    val portion = nightPortions.fajr
                    val nightFraction = (portion * night / 1000).toLong()
                    CalendarUtil.add(
                        sunriseComponents, -1 * nightFraction.toInt(), Calendar.SECOND
                    )
                }
            if (tempFajr == null || tempFajr.before(safeFajr)) {
                tempFajr = safeFajr
            }

            // Isha calculation with check against safe value
            if (calculationParameters.ishaInterval > 0) {
                tempIsha = CalendarUtil.add(
                    tempMaghrib,
                    calculationParameters.ishaInterval * 60,
                    Calendar.SECOND
                )
            } else {
                timeComponents = TimeComponents.Companion.fromDouble(
                    solarTime.hourAngle(-calculationParameters.ishaAngle, true)
                )
                if (timeComponents != null) {
                    tempIsha = timeComponents.dateComponents(dateComponents)
                }
                if (calculationParameters.method == CalculationMethod.MOON_SIGHTING_COMMITTEE &&
                    coordinates!!.latitude >= 55
                ) {
                    val nightFraction = night / 7000
                    tempIsha =
                        CalendarUtil.add(sunsetComponents, nightFraction.toInt(), Calendar.SECOND)
                }
                val safeIsha: Date?
                safeIsha =
                    if (calculationParameters.method == CalculationMethod.MOON_SIGHTING_COMMITTEE) {
                        seasonAdjustedEveningTwilight(
                            coordinates!!.latitude, dayOfYear, dateComponents.year, sunsetComponents
                        )
                    } else {
                        val portion = nightPortions.isha
                        val nightFraction = (portion * night / 1000).toLong()
                        CalendarUtil.add(sunsetComponents, nightFraction.toInt(), Calendar.SECOND)
                    }
                if (tempIsha == null || tempIsha.after(safeIsha)) {
                    tempIsha = safeIsha
                }
            }
        }
        if (error || tempAsr == null) {
            // if we don't have all prayer times then initialization failed
            fajr = null
            sunrise = null
            dhuhr = null
            asr = null
            maghrib = null
            isha = null
        } else {
            // Assign final times to public struct members with all offsets
            fajr = CalendarUtil.roundedMinute(
                CalendarUtil.add(
                    CalendarUtil.add(
                        tempFajr,
                        calculationParameters!!.adjustments.fajr,
                        Calendar.MINUTE
                    ),
                    calculationParameters.methodAdjustments.fajr,
                    Calendar.MINUTE
                )
            )
            sunrise = CalendarUtil.roundedMinute(
                CalendarUtil.add(
                    CalendarUtil.add(
                        tempSunrise,
                        calculationParameters.adjustments.sunrise,
                        Calendar.MINUTE
                    ),
                    calculationParameters.methodAdjustments.sunrise,
                    Calendar.MINUTE
                )
            )
            dhuhr = CalendarUtil.roundedMinute(
                CalendarUtil.add(
                    CalendarUtil.add(
                        tempDhuhr,
                        calculationParameters.adjustments.dhuhr,
                        Calendar.MINUTE
                    ),
                    calculationParameters.methodAdjustments.dhuhr,
                    Calendar.MINUTE
                )
            )
            asr = CalendarUtil.roundedMinute(
                CalendarUtil.add(
                    CalendarUtil.add(
                        tempAsr,
                        calculationParameters.adjustments.asr,
                        Calendar.MINUTE
                    ),
                    calculationParameters.methodAdjustments.asr,
                    Calendar.MINUTE
                )
            )
            maghrib = CalendarUtil.roundedMinute(
                CalendarUtil.add(
                    CalendarUtil.add(
                        tempMaghrib,
                        calculationParameters.adjustments.maghrib,
                        Calendar.MINUTE
                    ),
                    calculationParameters.methodAdjustments.maghrib,
                    Calendar.MINUTE
                )
            )
            isha = CalendarUtil.roundedMinute(
                CalendarUtil.add(
                    CalendarUtil.add(
                        tempIsha,
                        calculationParameters.adjustments.isha,
                        Calendar.MINUTE
                    ),
                    calculationParameters.methodAdjustments.isha,
                    Calendar.MINUTE
                )
            )
        }
    }

    @JvmOverloads
    fun currentPrayer(time: Date = Date()): Prayer {
        val `when` = time.time
        return if (isha!!.time - `when` <= 0) {
            Prayer.ISHA
        } else if (maghrib!!.time - `when` <= 0) {
            Prayer.MAGHRIB
        } else if (asr!!.time - `when` <= 0) {
            Prayer.ASR
        } else if (dhuhr!!.time - `when` <= 0) {
            Prayer.DHUHR
        } else if (sunrise!!.time - `when` <= 0) {
            Prayer.SUNRISE
        } else if (fajr!!.time - `when` <= 0) {
            Prayer.FAJR
        } else {
            Prayer.NONE
        }
    }

    @JvmOverloads
    fun nextPrayer(time: Date = Date()): Prayer {
        val `when` = time.time
        return if (isha!!.time - `when` <= 0) {
            Prayer.NONE
        } else if (maghrib!!.time - `when` <= 0) {
            Prayer.ISHA
        } else if (asr!!.time - `when` <= 0) {
            Prayer.MAGHRIB
        } else if (dhuhr!!.time - `when` <= 0) {
            Prayer.ASR
        } else if (sunrise!!.time - `when` <= 0) {
            Prayer.DHUHR
        } else if (fajr!!.time - `when` <= 0) {
            Prayer.SUNRISE
        } else {
            Prayer.FAJR
        }
    }

    fun timeForPrayer(prayer: Prayer?): Date? {
        return when (prayer) {
            Prayer.FAJR -> fajr
            Prayer.SUNRISE -> sunrise
            Prayer.DHUHR -> dhuhr
            Prayer.ASR -> asr
            Prayer.MAGHRIB -> maghrib
            Prayer.ISHA -> isha
            Prayer.NONE -> null
            else -> null
        }
    }

    companion object {
        private fun seasonAdjustedMorningTwilight(
            latitude: Double, day: Int, year: Int, sunrise: Date?
        ): Date? {
            val a = 75 + 28.65 / 55.0 * abs(latitude)
            val b = 75 + 19.44 / 55.0 * abs(latitude)
            val c = 75 + 32.74 / 55.0 * abs(latitude)
            val d = 75 + 48.10 / 55.0 * abs(latitude)
            val adjustment: Double
            val dyy = daysSinceSolstice(day, year, latitude)
            adjustment = if (dyy < 91) {
                a + (b - a) / 91.0 * dyy
            } else if (dyy < 137) {
                b + (c - b) / 46.0 * (dyy - 91)
            } else if (dyy < 183) {
                c + (d - c) / 46.0 * (dyy - 137)
            } else if (dyy < 229) {
                d + (c - d) / 46.0 * (dyy - 183)
            } else if (dyy < 275) {
                c + (b - c) / 46.0 * (dyy - 229)
            } else {
                b + (a - b) / 91.0 * (dyy - 275)
            }
            return CalendarUtil.add(
                sunrise,
                -Math.round(adjustment * 60.0).toInt(),
                Calendar.SECOND
            )
        }

        private fun seasonAdjustedEveningTwilight(
            latitude: Double, day: Int, year: Int, sunset: Date?
        ): Date? {
            val a = 75 + 25.60 / 55.0 * abs(latitude)
            val b = 75 + 2.050 / 55.0 * abs(latitude)
            val c = 75 - 9.210 / 55.0 * abs(latitude)
            val d = 75 + 6.140 / 55.0 * abs(latitude)
            val adjustment: Double
            val dyy = daysSinceSolstice(day, year, latitude)
            adjustment = if (dyy < 91) {
                a + (b - a) / 91.0 * dyy
            } else if (dyy < 137) {
                b + (c - b) / 46.0 * (dyy - 91)
            } else if (dyy < 183) {
                c + (d - c) / 46.0 * (dyy - 137)
            } else if (dyy < 229) {
                d + (c - d) / 46.0 * (dyy - 183)
            } else if (dyy < 275) {
                c + (b - c) / 46.0 * (dyy - 229)
            } else {
                b + (a - b) / 91.0 * (dyy - 275)
            }
            return CalendarUtil.add(sunset, Math.round(adjustment * 60.0).toInt(), Calendar.SECOND)
        }

        @JvmStatic
        fun daysSinceSolstice(dayOfYear: Int, year: Int, latitude: Double): Int {
            var daysSinceSolistice: Int
            val northernOffset = 10
            val isLeapYear = CalendarUtil.isLeapYear(year)
            val southernOffset = if (isLeapYear) 173 else 172
            val daysInYear = if (isLeapYear) 366 else 365
            if (latitude >= 0) {
                daysSinceSolistice = dayOfYear + northernOffset
                if (daysSinceSolistice >= daysInYear) {
                    daysSinceSolistice -= daysInYear
                }
            } else {
                daysSinceSolistice = dayOfYear - southernOffset
                if (daysSinceSolistice < 0) {
                    daysSinceSolistice += daysInYear
                }
            }
            return daysSinceSolistice
        }
    }
}