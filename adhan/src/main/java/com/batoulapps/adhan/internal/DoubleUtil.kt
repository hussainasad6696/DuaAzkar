package com.batoulapps.adhan.internal

internal object DoubleUtil {
    @JvmStatic
    fun normalizeWithBound(value: Double, max: Double): Double {
        return value - max * Math.floor(value / max)
    }

    @JvmStatic
    fun unwindAngle(value: Double): Double {
        return normalizeWithBound(value, 360.0)
    }

    @JvmStatic
    fun closestAngle(angle: Double): Double {
        return if (angle >= -180 && angle <= 180) {
            angle
        } else angle - 360 * Math.round(angle / 360)
    }
}