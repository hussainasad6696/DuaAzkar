package com.mera.islam.duaazkar.presentation.dua_tasbih_screen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.mera.islam.duaazkar.R
import com.mera.islam.duaazkar.core.presentation.custom_bottom_sheet.CustomBottomSheet
import com.mera.islam.duaazkar.ui.theme.RobotoFonts
import com.mera.islam.duaazkar.ui.theme.green
import com.mera.islam.duaazkar.ui.theme.lightTextGrayColor
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun TasbihBottomSheetReset(onCancelClick: () -> Unit, onResetClick: () -> Unit) {
    CustomBottomSheet(
        title = stringResource(id = R.string.reset),
        onCloseBottomSheet = onCancelClick,
        addCloseButton = false,
        content = {
            Text(
                text = stringResource(R.string.do_you_really_want_to_reset_the_tasbih_counter),
                color = Color.lightTextGrayColor,
                fontSize = 16.ssp,
                fontFamily = RobotoFonts.ROBOTO_REGULAR.getFont(),
                modifier = Modifier.padding(horizontal = 15.sdp)
            )

            Spacer(modifier = Modifier.height(15.sdp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.sdp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = onCancelClick,
                    shape = RoundedCornerShape(24.sdp),
                    modifier = Modifier
                        .weight(1f)
                        .height(45.sdp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xffeeeeee)
                    )
                ) {
                    Text(
                        text = stringResource(R.string.cancel),
                        fontFamily = RobotoFonts.ROBOTO_MEDIUM.getFont(),
                        fontSize = 16.ssp,
                        color = Color(0xffa1a1a1)
                    )
                }

                Spacer(modifier = Modifier.width(8.sdp))

                TextButton(
                    onClick = onResetClick,
                    shape = RoundedCornerShape(24.sdp),
                    modifier = Modifier
                        .weight(1f)
                        .height(45.sdp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.green
                    )
                ) {
                    Text(
                        text = stringResource(R.string.reset),
                        fontFamily = RobotoFonts.ROBOTO_MEDIUM.getFont(),
                        fontSize = 16.ssp,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.sdp))
        })
}