package com.batoulapps.adhan

import com.batoulapps.adhan.internal.QiblaUtil

class Qibla(coordinates: Coordinates) {
    @JvmField
    val direction: Double

    init {
        direction = QiblaUtil.calculateQiblaDirection(coordinates)
    }

    companion object {
        private val MAKKAH = Coordinates(21.4225241, 39.8261818)
    }
}