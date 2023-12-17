package com.mera.islam.duaazkar.core.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.Color
import androidx.palette.graphics.Palette
import com.mera.islam.duaazkar.R
import com.mera.islam.duaazkar.ui.theme.applicationBackgroundColor
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PaletteGenerator @Inject constructor(@ApplicationContext private val context: Context) {

    private fun getBitmapsList(): Bitmap? =
        BitmapFactory.decodeResource(context.resources, randomImage())

    private var lastColorListIndex = 0
    fun getPaletteList(): List<Color> {
        val colors = listOf(
            listOf(
                Color.applicationBackgroundColor,
                Color(0x4ca5683b),
                Color.applicationBackgroundColor
            ),
            listOf(
                Color.applicationBackgroundColor,
                Color(0x4c70a992),
                Color.applicationBackgroundColor
            ),
            listOf(
                Color.applicationBackgroundColor,
                Color(0x4cdda673),
                Color.applicationBackgroundColor
            ),
            listOf(
                Color.applicationBackgroundColor,
                Color(0x4c156c94),
                Color.applicationBackgroundColor
            ),
            listOf(
                Color.applicationBackgroundColor,
                Color(0x4c0dc0c6),
                Color.applicationBackgroundColor
            ),
            listOf(
                Color.applicationBackgroundColor,
                Color(0x4c204161),
                Color.applicationBackgroundColor
            ),
        )

        lastColorListIndex = (lastColorListIndex + 1) % colors.size
        return colors[lastColorListIndex]
    }

    private fun Palette.Swatch.getColor(): Color = Color(rgb)

    private var lastImageIndex = 0
    fun randomImage(): Int {
        val images = arrayOf(
            R.drawable.ic_asma_01,
            R.drawable.ic_asma_02,
            R.drawable.ic_asma_03,
            R.drawable.ic_asma_04,
            R.drawable.ic_asma_05,
            R.drawable.ic_asma_06
        )

        lastImageIndex = (lastImageIndex + 1) % images.size
        return images[lastImageIndex]
    }
}