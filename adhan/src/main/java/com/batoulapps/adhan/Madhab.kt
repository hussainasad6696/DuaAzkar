package com.batoulapps.adhan

import com.batoulapps.adhan.internal.ShadowLength

/**
 * Madhab for determining how Asr is calculated
 */
enum class Madhab {
    /**
     * Shafi Madhab
     */
    SHAFI,

    /**
     * Hanafi Madhab
     */
    HANAFI;

    val shadowLength: ShadowLength
        get() = when (this) {
            SHAFI -> {
                ShadowLength.SINGLE
            }

            HANAFI -> {
                ShadowLength.DOUBLE
            }

            else -> {
                throw IllegalArgumentException("Invalid Madhab")
            }
        }
}