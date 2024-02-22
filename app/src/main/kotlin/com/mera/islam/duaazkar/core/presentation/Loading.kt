package  com.mera.islam.duaazkar.core.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ir.kaaveh.sdpcompose.sdp


@Composable
fun Loading(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false
) {
    if (isLoading)
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(50.sdp)
                    .padding(5.sdp),
                color = Color.Blue,
                strokeWidth = 4.sdp
            )
        }
}