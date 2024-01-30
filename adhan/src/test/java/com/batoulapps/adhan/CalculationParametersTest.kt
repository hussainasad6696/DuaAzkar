package com.batoulapps.adhan

import com.google.common.truth.Truth
import org.junit.Test

class CalculationParametersTest {
    @Test
    @Throws(Exception::class)
    fun testNightPortion() {
        var parameters = CalculationParameters(18.0, 18)
        parameters.highLatitudeRule = HighLatitudeRule.MIDDLE_OF_THE_NIGHT
        Truth.assertThat(parameters.nightPortions().fajr).isWithin(0.001).of(0.5)
        Truth.assertThat(parameters.nightPortions().isha).isWithin(0.001).of(0.5)
        parameters = CalculationParameters(18.0, 18.0)
        parameters.highLatitudeRule = HighLatitudeRule.SEVENTH_OF_THE_NIGHT
        Truth.assertThat(parameters.nightPortions().fajr).isWithin(0.001).of(1.0 / 7.0)
        Truth.assertThat(parameters.nightPortions().isha).isWithin(0.001).of(1.0 / 7.0)
        parameters = CalculationParameters(10.0, 15.0)
        parameters.highLatitudeRule = HighLatitudeRule.TWILIGHT_ANGLE
        Truth.assertThat(parameters.nightPortions().fajr).isWithin(0.001).of(10.0 / 60.0)
        Truth.assertThat(parameters.nightPortions().isha).isWithin(0.001).of(15.0 / 60.0)
    }
}