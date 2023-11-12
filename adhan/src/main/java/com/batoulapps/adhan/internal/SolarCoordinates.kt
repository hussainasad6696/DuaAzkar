package com.batoulapps.adhan.internal

internal class SolarCoordinates(julianDay: Double) {
    /**
     * The declination of the sun, the angle between
     * the rays of the Sun and the plane of the Earth's
     * equator, in degrees.
     */
    @JvmField
    val declination: Double

    /**
     * Right ascension of the Sun, the angular distance on the
     * celestial equator from the vernal equinox to the hour circle,
     * in degrees.
     */
    @JvmField
    val rightAscension: Double

    /**
     * Apparent sidereal time, the hour angle of the vernal
     * equinox, in degrees.
     */
    @JvmField
    val apparentSiderealTime: Double

    init {
        val T = CalendricalHelper.julianCentury(julianDay)
        val L0 = Astronomical.meanSolarLongitude( /* julianCentury */T)
        val Lp = Astronomical.meanLunarLongitude( /* julianCentury */T)
        val Ω = Astronomical.ascendingLunarNodeLongitude( /* julianCentury */T)
        val λ = Math.toRadians(
            Astronomical.apparentSolarLongitude( /* julianCentury*/T,  /* meanLongitude */L0)
        )
        val θ0 = Astronomical.meanSiderealTime( /* julianCentury */T)
        val ΔΨ = Astronomical.nutationInLongitude( /* julianCentury */T,  /* solarLongitude */
            L0,  /* lunarLongitude */
            Lp,  /* ascendingNode */
            Ω
        )
        val Δε = Astronomical.nutationInObliquity( /* julianCentury */T,  /* solarLongitude */
            L0,  /* lunarLongitude */
            Lp,  /* ascendingNode */
            Ω
        )
        val ε0 = Astronomical.meanObliquityOfTheEcliptic( /* julianCentury */T)
        val εapp = Math.toRadians(
            Astronomical.apparentObliquityOfTheEcliptic( /* julianCentury */
                T,  /* meanObliquityOfTheEcliptic */ε0
            )
        )

        /* Equation from Astronomical Algorithms page 165 */declination = Math.toDegrees(
            Math.asin(
                Math.sin(εapp) * Math.sin(λ)
            )
        )

        /* Equation from Astronomical Algorithms page 165 */rightAscension = DoubleUtil.unwindAngle(
            Math.toDegrees(Math.atan2(Math.cos(εapp) * Math.sin(λ), Math.cos(λ)))
        )

        /* Equation from Astronomical Algorithms page 88 */apparentSiderealTime =
            θ0 + ΔΨ * 3600 * Math.cos(
                Math.toRadians(ε0 + Δε)
            ) / 3600
    }
}