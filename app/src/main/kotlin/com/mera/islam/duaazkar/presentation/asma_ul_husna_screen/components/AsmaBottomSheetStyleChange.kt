package com.mera.islam.duaazkar.presentation.asma_ul_husna_screen.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mera.islam.duaazkar.R
import com.mera.islam.duaazkar.core.presentation.custom_bottom_sheet.CustomBottomSheet
import com.mera.islam.duaazkar.ui.theme.RobotoFonts
import com.mera.islam.duaazkar.ui.theme.darkTextGrayColor
import com.mera.islam.duaazkar.ui.theme.gray9fColor
import com.mera.islam.duaazkar.ui.theme.green
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun AsmaBottomSheetStyleChange(
    @DrawableRes selectedPreview: Int = R.drawable.frame_72,
    onItemSelected: (Int) -> Unit = {},
    onCloseBottomSheet: () -> Unit = {}
) {
    CustomBottomSheet(
        title = stringResource(id = R.string.select_preview),
        onCloseBottomSheet = onCloseBottomSheet
    ) {
        Spacer(modifier = Modifier.height(10.sdp))

        Row(modifier = Modifier.fillMaxWidth()) {
            arrayOf(
                Pair(R.drawable.frame_72, R.string.list_view),
                Pair(R.drawable.frame_73, R.string.grid_view),
                Pair(R.drawable.frame_74, R.string.image_view)
            ).forEach { (image, string) ->
                SelectPreviewItem(
                    image = image,
                    stringRes = string,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 5.sdp),
                    isSelected = selectedPreview == image,
                    onItemSelected = { onItemSelected(image) }
                )
            }
        }

        Spacer(modifier = Modifier.height(10.sdp))
    }
}

@Composable
fun SelectPreviewItem(
    modifier: Modifier = Modifier,
    @DrawableRes image: Int,
    @StringRes stringRes: Int,
    isSelected: Boolean = false,
    onItemSelected: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .height(96.sdp)
                .width(100.sdp)
                .border(
                    width = 1.dp,
                    color = if (isSelected) Color.green else Color(0xffe9e9e9),
                    shape = RoundedCornerShape(14.sdp)
                )
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple(),
                    onClick = onItemSelected
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = "selected preview",
                modifier = Modifier
                    .matchParentSize()
                    .padding(10.sdp)
            )
        }

        Spacer(modifier = Modifier.height(5.sdp))

        Text(
            text = stringResource(id = stringRes),
            fontFamily = (if (isSelected) RobotoFonts.ROBOTO_MEDIUM else RobotoFonts.ROBOTO_REGULAR).getFont(),
            fontSize = 14.ssp,
            color = if (isSelected) Color.darkTextGrayColor else Color.gray9fColor
        )
    }
}