package com.mera.islam.duaazkar.presentation.dua_tasbih_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mera.islam.duaazkar.R
import com.mera.islam.duaazkar.core.presentation.TitleAndSwitch
import com.mera.islam.duaazkar.core.presentation.custom_bottom_sheet.CustomBottomSheet
import com.mera.islam.duaazkar.ui.theme.RobotoFonts
import com.mera.islam.duaazkar.ui.theme.darkTextGrayColor
import com.mera.islam.duaazkar.ui.theme.green
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun TasbihBottomSheetCustomLimit(
    selectedOptions: Int = 33,
    isTasbihSoundOn: Boolean = false,
    onCloseBottomSheet: () -> Unit = {},
    onRadioOptionSelected: (Int) -> Unit = {},
    onSoundOptionsChanged: (Boolean) -> Unit = {},
    onCustomOption: () -> Unit = {}
) {
    CustomBottomSheet(
        title = stringResource(id = R.string.set_goals),
        onCloseBottomSheet = onCloseBottomSheet
    ) {
        PreDefinedCounts(
            selectedOptions = selectedOptions,
            onRadioOptionSelected = onRadioOptionSelected
        )

        Divider(
            thickness = 1.dp,
            color = Color.darkTextGrayColor.copy(0.10f),
            modifier = Modifier.padding(10.sdp)
        )

        TextButton(
            onClick = onCustomOption,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.sdp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            contentPadding = ButtonDefaults.ContentPadding
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.custom),
                    fontFamily = RobotoFonts.ROBOTO_REGULAR.getFont(),
                    color = Color.darkTextGrayColor,
                    fontSize = 18.ssp
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_next_icon),
                    contentDescription = "Next",
                    tint = Color(0xff515151)
                )
            }
        }

        Divider(
            thickness = 1.dp,
            color = Color.darkTextGrayColor.copy(0.10f),
            modifier = Modifier.padding(10.sdp)
        )

        Spacer(modifier = Modifier.height(10.sdp))

        TitleAndSwitch(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.sdp),
            title = stringResource(id = R.string.tasbih_sound_on),
            isChecked = isTasbihSoundOn,
            onCheckedChange = onSoundOptionsChanged
        )

        Spacer(modifier = Modifier.height(10.sdp))
    }
}

@Composable
private fun PreDefinedCounts(selectedOptions: Int, onRadioOptionSelected: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.sdp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        listOf(33, 66, 99).forEach {
            CustomRadioButton(
                name = it.toString(),
                isSelected = it == selectedOptions,
                onRadioOptionSelected = { onRadioOptionSelected(it) }
            )
        }
    }
}

@Composable
private fun CustomRadioButton(
    name: String,
    isSelected: Boolean = false,
    onRadioOptionSelected: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onRadioOptionSelected() }) {
        RadioButton(
            selected = isSelected,
            onClick = onRadioOptionSelected,
            colors = RadioButtonDefaults.colors(
                selectedColor = Color.green,
                unselectedColor = Color(0xffc8c8c8)
            )
        )

        Text(
            text = name,
            color = Color.darkTextGrayColor,
            fontSize = 20.ssp,
            fontFamily = RobotoFonts.ROBOTO_MEDIUM.getFont()
        )
    }
}