package com.batoulapps.adhan.internal

import com.batoulapps.adhan.Coordinates
import kotlin.math.pow

/**
 * Astronomical equations
 */
internal object Astronomical {
    /**
     * The geometric mean longitude of the sun in degrees.
     * @param T the julian century
     * @return the geometric longitude of the sun in degrees
     */
    @JvmStatic
    fun meanSolarLongitude(T: Double): Double {
        /* Equation from Astronomical Algorithms page 163 */
        val term1 = 280.4664567
        val term2 = 36000.76983 * T
        val term3 = 0.0003032 * T.pow(2.0)
        val L0 = term1 + term2 + term3
        return DoubleUtil.unwindAngle(L0)
    }

    /**
     * The geometric mean longitude of the moon in degrees
     * @param T the julian century
     * @return the geometric mean longitude of the moon in degrees
     */
    @JvmStatic
    fun meanLunarLongitude(T: Double): Double {
        /* Equation from Astronomical Algorithms page 144 */
        val term1 = 218.3165
        val term2 = 481267.8813 * T
        val Lp = term1 + term2
        return DoubleUtil.unwindAngle(Lp)
    }

    /**
     * The apparent longitude of the Sun, referred to the true equinox of the date.
     * @param T the julian century
     * @param L0 the mean longitude
     * @return the true equinox of the date
     */
    @JvmStatic
    fun apparentSolarLongitude(T: Double, L0: Double): Double {
        /* Equation from Astronomical Algorithms page 164 */
        val longitude = L0 + solarEquationOfTheCenter(T, meanSolarAnomaly(T))
        val Ω = 125.04 - 1934.136 * T
        val λ = longitude - 0.00569 - 0.00478 * Math.sin(Math.toRadians(Ω))
        return DoubleUtil.unwindAngle(λ)
    }

    /**
     * The ascending lunar node longitude
     * @param T the julian century
     * @return the ascending lunar node longitude
     */
    @JvmStatic
    fun ascendingLunarNodeLongitude(T: Double): Double {
        /* Equation from Astronomical Algorithms page 144 */
        val term1 = 125.04452
        val term2 = 1934.136261 * T
        val term3 = 0.0020708 * Math.pow(T, 2.0)
        val term4 = Math.pow(T, 3.0) / 450000
        val Ω = term1 - term2 + term3 + term4
        return DoubleUtil.unwindAngle(Ω)
    }

    /**
     * The mean anomaly of the sun
     * @param T the julian century
     * @return the mean solar anomaly
     */
    @JvmStatic
    fun meanSolarAnomaly(T: Double): Double {
        /* Equation from Astronomical Algorithms page 163 */
        val term1 = 357.52911
        val term2 = 35999.05029 * T
        val term3 = 0.0001537 * Math.pow(T, 2.0)
        val M = term1 + term2 - term3
        return DoubleUtil.unwindAngle(M)
    }

    /**
     * The Sun's equation of the center in degrees.
     * @param T the julian century
     * @param M the mean anomaly
     * @return the sun's equation of the center in degrees
     */
    @JvmStatic
    fun solarEquationOfTheCenter(T: Double, M: Double): Double {
        /* Equation from Astronomical Algorithms page 164 */
        val Mrad = Math.toRadians(M)
        val term1 = (1.914602 - 0.004817 * T - 0.000014 * Math.pow(T, 2.0)) * Math.sin(Mrad)
        val term2 = (0.019993 - 0.000101 * T) * Math.sin(2 * Mrad)
        val term3 = 0.000289 * Math.sin(3 * Mrad)
        return term1 + term2 + term3
    }

    /**
     * The mean obliquity of the ecliptic in degrees
     * formula adopted by the International Astronomical Union.
     * @param T the julian century
     * @return the mean obliquity of the ecliptic in degrees
     */
    @JvmStatic
    fun meanObliquityOfTheEcliptic(T: Double): Double {
        /* Equation from Astronomical Algorithms page 147 */
        val term1 = 23.439291
        val term2 = 0.013004167 * T
        val term3 = 0.0000001639 * Math.pow(T, 2.0)
        val term4 = 0.0000005036 * Math.pow(T, 3.0)
        return term1 - term2 - term3 + term4
    }

    /**
     * The mean obliquity of the ecliptic, corrected for calculating the
     * apparent position of the sun, in degrees.
     * @param T the julian century
     * @param ε0 the mean obliquity of the ecliptic
     * @return the corrected mean obliquity of the ecliptic in degrees
     */
    @JvmStatic
    fun apparentObliquityOfTheEcliptic(T: Double, ε0: Double): Double {
        /* Equation from Astronomical Algorithms page 165 */
        val O = 125.04 - 1934.136 * T
        return ε0 + 0.00256 * Math.cos(Math.toRadians(O))
    }

    /**
     * Mean sidereal time, the hour angle of the vernal equinox, in degrees.
     * @param T the julian century
     * @return the mean sidereal time in degrees
     */
    @JvmStatic
    fun meanSiderealTime(T: Double): Double {
        /* Equation from Astronomical Algorithms page 165 */
        val JD = T * 36525 + 2451545.0
        val term1 = 280.46061837
        val term2 = 360.98564736629 * (JD - 2451545)
        val term3 = 0.000387933 * Math.pow(T, 2.0)
        val term4 = Math.pow(T, 3.0) / 38710000
        val θ = term1 + term2 + term3 - term4
        return DoubleUtil.unwindAngle(θ)
    }

    /**
     * Get the nutation in longitude
     * @param T the julian century
     * @param L0 the solar longitude
     * @param Lp the lunar longitude
     * @param Ω the ascending node
     * @return the nutation in longitude
     */
    @JvmStatic
    fun nutationInLongitude(T: Double, L0: Double, Lp: Double, Ω: Double): Double {
        /* Equation from Astronomical Algorithms page 144 */
        val term1 = -17.2 / 3600 * Math.sin(Math.toRadians(Ω))
        val term2 = 1.32 / 3600 * Math.sin(2 * Math.toRadians(L0))
        val term3 = 0.23 / 3600 * Math.sin(2 * Math.toRadians(Lp))
        val term4 = 0.21 / 3600 * Math.sin(2 * Math.toRadians(Ω))
        return term1 - term2 - term3 + term4
    }

    /**
     * Get the nutation in obliquity
     * @param T the julian century
     * @param L0 the solar longitude
     * @param Lp the lunar longitude
     * @param Ω the ascending node
     * @return the nutation in obliquity
     */
    @JvmStatic
    fun nutationInObliquity(T: Double, L0: Double, Lp: Double, Ω: Double): Double {
        /* Equation from Astronomical Algorithms page 144 */
        val term1 = 9.2 / 3600 * Math.cos(Math.toRadians(Ω))
        val term2 = 0.57 / 3600 * Math.cos(2 * Math.toRadians(L0))
        val term3 = 0.10 / 3600 * Math.cos(2 * Math.toRadians(Lp))
        val term4 = 0.09 / 3600 * Math.cos(2 * Math.toRadians(Ω))
        return term1 + term2 + term3 - term4
    }

    /**
     * Return the altitude of the celestial body
     * @param φ the observer latitude
     * @param δ the declination
     * @param H the local hour angle
     * @return the altitude of the celestial body
     */
    @JvmStatic
    fun altitudeOfCelestialBody(φ: Double, δ: Double, H: Double): Double {
        /* Equation from Astronomical Algorithms page 93 */
        val term1 = Math.sin(Math.toRadians(φ)) * Math.sin(Math.toRadians(δ))
        val term2 = Math.cos(Math.toRadians(φ)) *
                Math.cos(Math.toRadians(δ)) * Math.cos(Math.toRadians(H))
        return Math.toDegrees(Math.asin(term1 + term2))
    }

    /**
     * Return the approximate transite
     * @param L the longitude
     * @param Θ0 the sidereal time
     * @param α2 the right ascension
     * @return the approximate transite
     */
    @JvmStatic
    fun approximateTransit(L: Double, Θ0: Double, α2: Double): Double {
        /* Equation from page Astronomical Algorithms 102 */
        val Lw = L * -1
        return DoubleUtil.normalizeWithBound((α2 + Lw - Θ0) / 360, 1.0)
    }

    /**
     * The time at which the sun is at its highest point in the sky (in universal time)
     * @param m0 approximate transit
     * @param L the longitude
     * @param Θ0 the sidereal time
     * @param α2 the right ascension
     * @param α1 the previous right ascension
     * @param α3 the next right ascension
     * @return the time (in universal time) when the sun is at its highest point in the sky
     */
    @JvmStatic
    fun correctedTransit(
        m0: Double,
        L: Double,
        Θ0: Double,
        α2: Double,
        α1: Double,
        α3: Double
    ): Double {
        /* Equation from page Astronomical Algorithms 102 */
        val Lw = L * -1
        val θ = DoubleUtil.unwindAngle(Θ0 + 360.985647 * m0)
        val α = DoubleUtil.unwindAngle(
            interpolateAngles( /* value */
                α2,  /* previousValue */α1,  /* nextValue */α3,  /* factor */m0
            )
        )
        val H = DoubleUtil.closestAngle(θ - Lw - α)
        val Δm = H / -360
        return (m0 + Δm) * 24
    }

    /**
     * Get the corrected hour angle
     * @param m0 the approximate transit
     * @param h0 the angle
     * @param coordinates the coordinates
     * @param afterTransit whether it's after transit
     * @param Θ0 the sidereal time
     * @param α2 the right ascension
     * @param α1 the previous right ascension
     * @param α3 the next right ascension
     * @param δ2 the declination
     * @param δ1 the previous declination
     * @param δ3 the next declination
     * @return the corrected hour angle
     */
    @JvmStatic
    fun correctedHourAngle(
        m0: Double, h0: Double, coordinates: Coordinates?, afterTransit: Boolean,
        Θ0: Double, α2: Double, α1: Double, α3: Double, δ2: Double, δ1: Double, δ3: Double
    ): Double {
        /* Equation from page Astronomical Algorithms 102 */
        val Lw = coordinates!!.longitude * -1
        val term1 = Math.sin(Math.toRadians(h0)) - Math.sin(
            Math.toRadians(
                coordinates.latitude
            )
        ) * Math.sin(Math.toRadians(δ2))
        val term2 = Math.cos(Math.toRadians(coordinates.latitude)) * Math.cos(Math.toRadians(δ2))
        val H0 = Math.toDegrees(Math.acos(term1 / term2))
        val m = if (afterTransit) m0 + H0 / 360 else m0 - H0 / 360
        val θ = DoubleUtil.unwindAngle(Θ0 + 360.985647 * m)
        val α = DoubleUtil.unwindAngle(
            interpolateAngles( /* value */
                α2,  /* previousValue */α1,  /* nextValue */α3,  /* factor */m
            )
        )
        val δ = interpolate( /* value */δ2,  /* previousValue */δ1,  /* nextValue */
            δ3,  /* factor */m
        )
        val H = θ - Lw - α
        val h = altitudeOfCelestialBody( /* observerLatitude */
            coordinates.latitude,  /* declination */
            δ,  /* localHourAngle */H
        )
        val term3 = h - h0
        val term4 = 360 * Math.cos(Math.toRadians(δ)) *
                Math.cos(Math.toRadians(coordinates.latitude)) * Math.sin(Math.toRadians(H))
        val Δm = term3 / term4
        return (m + Δm) * 24
    }
    /* Interpolation of a value given equidistant
  previous and next values and a factor
  equal to the fraction of the interpolated
  point's time over the time between values. */
    /**
     *
     * @param y2 the value
     * @param y1 the previous value
     * @param y3 the next value
     * @param n the factor
     * @return the interpolated value
     */
    @JvmStatic
    fun interpolate(y2: Double, y1: Double, y3: Double, n: Double): Double {
        /* Equation from Astronomical Algorithms page 24 */
        val a = y2 - y1
        val b = y3 - y2
        val c = b - a
        return y2 + n / 2 * (a + b + n * c)
    }

    /**
     * Interpolation of three angles, accounting for angle unwinding
     * @param y2 value
     * @param y1 previousValue
     * @param y3 nextValue
     * @param n factor
     * @return interpolated angle
     */
    @JvmStatic
    fun interpolateAngles(y2: Double, y1: Double, y3: Double, n: Double): Double {
        /* Equation from Astronomical Algorithms page 24 */
        val a = DoubleUtil.unwindAngle(y2 - y1)
        val b = DoubleUtil.unwindAngle(y3 - y2)
        val c = b - a
        return y2 + n / 2 * (a + b + n * c)
    }
}