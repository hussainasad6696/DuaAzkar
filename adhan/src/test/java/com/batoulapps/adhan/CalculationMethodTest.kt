package com.batoulapps.adhan

import com.google.common.truth.Truth
import org.junit.Test

class CalculationMethodTest {
    @Test
    fun testCalculationMethods() {
        var params = CalculationMethod.MUSLIM_WORLD_LEAGUE.parameters
        Truth.assertThat(params!!.fajrAngle).isWithin(0.000001).of(18.0)
        Truth.assertThat(params.ishaAngle).isWithin(0.000001).of(17.0)
        Truth.assertThat(params.ishaInterval).isEqualTo(0)
        Truth.assertThat(params.method).isEqualTo(CalculationMethod.MUSLIM_WORLD_LEAGUE)
        params = CalculationMethod.EGYPTIAN.parameters
        Truth.assertThat(params!!.fajrAngle).isWithin(0.000001).of(19.5)
        Truth.assertThat(params.ishaAngle).isWithin(0.000001).of(17.5)
        Truth.assertThat(params.ishaInterval).isEqualTo(0)
        Truth.assertThat(params.method).isEqualTo(CalculationMethod.EGYPTIAN)
        params = CalculationMethod.KARACHI.parameters
        Truth.assertThat(params!!.fajrAngle).isWithin(0.000001).of(18.0)
        Truth.assertThat(params.ishaAngle).isWithin(0.000001).of(18.0)
        Truth.assertThat(params.ishaInterval).isEqualTo(0)
        Truth.assertThat(params.method).isEqualTo(CalculationMethod.KARACHI)
        params = CalculationMethod.UMM_AL_QURA.parameters
        Truth.assertThat(params!!.fajrAngle).isWithin(0.000001).of(18.5)
        Truth.assertThat(params.ishaAngle).isWithin(0.000001).of(0.0)
        Truth.assertThat(params.ishaInterval).isEqualTo(90)
        Truth.assertThat(params.method).isEqualTo(CalculationMethod.UMM_AL_QURA)
        params = CalculationMethod.DUBAI.parameters
        Truth.assertThat(params!!.fajrAngle).isWithin(0.000001).of(18.2)
        Truth.assertThat(params.ishaAngle).isWithin(0.000001).of(18.2)
        Truth.assertThat(params.ishaInterval).isEqualTo(0)
        Truth.assertThat(params.method).isEqualTo(CalculationMethod.DUBAI)
        params = CalculationMethod.MOON_SIGHTING_COMMITTEE.parameters
        Truth.assertThat(params!!.fajrAngle).isWithin(0.000001).of(18.0)
        Truth.assertThat(params.ishaAngle).isWithin(0.000001).of(18.0)
        Truth.assertThat(params.ishaInterval).isEqualTo(0)
        Truth.assertThat(params.method).isEqualTo(CalculationMethod.MOON_SIGHTING_COMMITTEE)
        params = CalculationMethod.NORTH_AMERICA.parameters
        Truth.assertThat(params!!.fajrAngle).isWithin(0.000001).of(15.0)
        Truth.assertThat(params.ishaAngle).isWithin(0.000001).of(15.0)
        Truth.assertThat(params.ishaInterval).isEqualTo(0)
        Truth.assertThat(params.method).isEqualTo(CalculationMethod.NORTH_AMERICA)
        params = CalculationMethod.KUWAIT.parameters
        Truth.assertThat(params!!.fajrAngle).isWithin(0.000001).of(18.0)
        Truth.assertThat(params.ishaAngle).isWithin(0.000001).of(17.5)
        Truth.assertThat(params.ishaInterval).isEqualTo(0)
        Truth.assertThat(params.method).isEqualTo(CalculationMethod.KUWAIT)
        params = CalculationMethod.QATAR.parameters
        Truth.assertThat(params!!.fajrAngle).isWithin(0.000001).of(18.0)
        Truth.assertThat(params.ishaAngle).isWithin(0.000001).of(0.0)
        Truth.assertThat(params.ishaInterval).isEqualTo(90)
        Truth.assertThat(params.method).isEqualTo(CalculationMethod.QATAR)
        params = CalculationMethod.OTHER.parameters
        Truth.assertThat(params!!.fajrAngle).isWithin(0.000001).of(0.0)
        Truth.assertThat(params.ishaAngle).isWithin(0.000001).of(0.0)
        Truth.assertThat(params.ishaInterval).isEqualTo(0)
        Truth.assertThat(params.method).isEqualTo(CalculationMethod.OTHER)
    }
}