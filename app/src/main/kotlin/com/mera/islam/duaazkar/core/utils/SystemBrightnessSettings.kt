package com.mera.islam.duaazkar.core.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class SystemBrightnessSettings @Inject constructor(@ApplicationContext private val context: Context) {

    fun changeScreenBrightness(
        screenBrightnessValue: Float
    ) {
        val brightness = normalize(screenBrightnessValue, 0f, 100f, 0.0f, 255.0f)

        Settings.System.putInt(
            context.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS_MODE,
            Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
        )
        Settings.System.putInt(
            context.contentResolver, Settings.System.SCREEN_BRIGHTNESS, brightness.toInt()
        )
    }

    fun hasWriteSettingsPermission(): Boolean = Settings.System.canWrite(context)

    fun changeWriteSettingsPermission() {
        context.startActivity(Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS).apply {
            setData(Uri.parse("package:" + context.packageName))
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    private fun normalize(
        x: Float,
        inMin: Float,
        inMax: Float,
        outMin: Float,
        outMax: Float
    ): Float {
        val outRange = outMax - outMin
        val inRange = inMax - inMin
        return (x - inMin) * outRange / inRange + outMin
    }
}