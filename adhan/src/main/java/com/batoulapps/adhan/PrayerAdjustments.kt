package com.batoulapps.adhan

/**
 * Adjustment value for prayer times, in minutes
 * These values are added (or subtracted) from the prayer time that is calculated before
 * returning the result times.
 */
class PrayerAdjustments
/**
 * Gets a PrayerAdjustments object with all offsets set to 0
 */ @JvmOverloads constructor(
    /**
     * Fajr offset in minutes
     */
    @JvmField var fajr: Int = 0,
    /**
     * Sunrise offset in minutes
     */
    @JvmField var sunrise: Int = 0,
    /**
     * Dhuhr offset in minutes
     */
    @JvmField var dhuhr: Int = 0,
    /**
     * Asr offset in minutes
     */
    @JvmField var asr: Int = 0,
    /**
     * Maghrib offset in minutes
     */
    @JvmField var maghrib: Int = 0,
    /**
     * Isha offset in minutes
     */
    @JvmField var isha: Int = 0
) {
    /**
     * Gets a PrayerAdjustments object to offset prayer times
     * @param fajr offset from fajr in minutes
     * @param sunrise offset from sunrise in minutes
     * @param dhuhr offset from dhuhr in minutes
     * @param asr offset from asr in minutes
     * @param maghrib offset from maghrib in minutes
     * @param isha offset from isha in minutes
     */
}