package com.batoulapps.adhan.internal

import com.batoulapps.adhan.Coordinates

object QiblaUtil {
    private val MAKKAH = Coordinates(21.4225241, 39.8261818)
    fun calculateQiblaDirection(coordinates: Coordinates): Double {
        // Equation from "Spherical Trigonometry For the use of colleges and schools" page 50
        val longitudeDelta =
            Math.toRadians(MAKKAH.longitude) - Math.toRadians(coordinates.longitude)
        val latitudeRadians = Math.toRadians(coordinates.latitude)
        val term1 = Math.sin(longitudeDelta)
        val term2 = Math.cos(latitudeRadians) * Math.tan(Math.toRadians(MAKKAH.latitude))
        val term3 = Math.sin(latitudeRadians) * Math.cos(longitudeDelta)
        val angle = Math.atan2(term1, term2 - term3)
        return DoubleUtil.unwindAngle(Math.toDegrees(angle))
    }
}