package com.mera.islam.duaazkar.presentation.dua_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mera.islam.duaazkar.core.presentation.AudioPlayer
import ir.kaaveh.sdpcompose.sdp

@Composable
fun DuaBottomSheetAudio(
    progress: Float = 0.5f,
    isPlaying: Boolean = false,
    onRepeatClick: () -> Unit = {},
    onSkipToPrev: () -> Unit = {},
    onSkipToNext: () -> Unit = {},
    onPlayClick: () -> Unit = {},
    onStopClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.sdp))

        Box(
            modifier = Modifier
                .width(32.sdp)
                .height(4.sdp)
                .drawBehind {
                    drawRoundRect(
                        color = Color(0xffe3e3e3),
                        cornerRadius = CornerRadius(3.dp.toPx(),3.dp.toPx())
                    )
                }
        )

        Spacer(modifier = Modifier.height(10.sdp))

        AudioPlayer(
            progress = progress,
            isPlaying = isPlaying,
            onRepeatClick = onRepeatClick,
            onSkipToPrev = onSkipToPrev,
            onSkipToNext = onSkipToNext,
            onPlayClick = onPlayClick,
            onStopClick = onStopClick
        )

        Spacer(modifier = Modifier.height(10.sdp))
    }
}