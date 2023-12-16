package com.mera.islam.duaazkar.core.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mera.islam.duaazkar.R
import com.mera.islam.duaazkar.ui.theme.green
import com.mera.islam.duaazkar.ui.theme.lightTextGrayColor
import ir.kaaveh.sdpcompose.sdp

@Composable
fun AudioPlayer(
    progress: Float = 0.5f,
    isPlaying: Boolean = false,
    onRepeatClick: () -> Unit = {},
    onSkipToPrev: () -> Unit = {},
    onSkipToNext: () -> Unit = {},
    onPlayClick: () -> Unit = {},
    onStopClick: () -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .padding(horizontal = 10.sdp)
                .fillMaxWidth()
                .drawBehind {
                    drawRoundRect(
                        color = Color(0xfff0f9f8),
                        cornerRadius = CornerRadius(50.dp.toPx(),50.dp.toPx())
                    )
                },
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(10.sdp))

            IconButton(
                onClick = onRepeatClick,
                modifier = Modifier.size(24.sdp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_repeat_icon),
                    contentDescription = "Repeat icon",
                    tint = Color(0xffb3b5b5)
                )
            }

            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onSkipToPrev,
                    modifier = Modifier.size(34.sdp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_skip_previous_icon),
                        contentDescription = "Repeat icon",
                        tint = Color.lightTextGrayColor
                    )
                }

                IconButton(
                    onClick = onPlayClick,
                    modifier = Modifier.size(44.sdp)
                ) {
                    Icon(
                        painter = painterResource(id = if (isPlaying) R.drawable.ic_resume_audio else R.drawable.ic_play_audio),
                        contentDescription = "Repeat icon",
                        tint = Color.Unspecified
                    )
                }

                IconButton(
                    onClick = onSkipToNext,
                    modifier = Modifier.size(34.sdp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_skip_next_icon),
                        contentDescription = "Repeat icon",
                        tint = Color.lightTextGrayColor
                    )
                }
            }

            IconButton(
                onClick = onStopClick,
                modifier = Modifier.size(24.sdp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_stop_icon),
                    contentDescription = "Repeat icon",
                    tint = Color.Unspecified
                )
            }

            Spacer(modifier = Modifier.width(10.sdp))
        }

        LinearProgressIndicator(
            modifier = Modifier
                .padding(horizontal = 25.sdp)
                .height(1.sdp),
            progress = progress,
            trackColor = Color.Transparent,
            color = Color.green,
            strokeCap = StrokeCap.Round
        )
    }
}