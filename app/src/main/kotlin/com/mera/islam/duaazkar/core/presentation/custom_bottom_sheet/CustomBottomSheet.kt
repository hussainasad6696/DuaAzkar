package com.mera.islam.duaazkar.core.presentation.custom_bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mera.islam.duaazkar.R
import ir.kaaveh.sdpcompose.sdp

@Composable
fun CustomBottomSheet(
    title: String = stringResource(id = R.string.display),
    addCloseButton: Boolean = true,
    onCloseBottomSheet: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
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

        BottomSheetSettingsNavigationTitle(
            title = title,
            onClose = onCloseBottomSheet
        )

        content()
    }
}