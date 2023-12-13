package com.mera.islam.duaazkar.presentation.dua_tasbih_screen.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import com.mera.islam.duaazkar.R
import com.mera.islam.duaazkar.core.extensions.log
import com.mera.islam.duaazkar.core.presentation.custom_bottom_sheet.CustomBottomSheet
import com.mera.islam.duaazkar.ui.theme.RobotoFonts
import com.mera.islam.duaazkar.ui.theme.darkTextGrayColor
import com.mera.islam.duaazkar.ui.theme.green
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun TasbihBottomSheetSetCustomGoals(
    onCloseBottomSheet: () -> Unit = {},
    onSaveClick: (String) -> Unit
) {
    CustomBottomSheet(
        title = stringResource(id = R.string.set_goals),
        onCloseBottomSheet = onCloseBottomSheet
    ) {
        Spacer(modifier = Modifier.height(10.sdp))

        var text by remember {
            mutableStateOf("")
        }

        var isError by remember {
            mutableStateOf(false)
        }

        OutlinedTextField(
            value = text,
            onValueChange = {
                if (it.isEmpty()) text = it
                else it.toIntOrNull()?.let { value ->
                        if (value <= 1000) {
                            if (isError)
                                isError = false
                            text = value.toString()
                        } else isError = true
                    } ?: let { isError = true }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.sdp),
            singleLine = true,
            maxLines = 1,
            isError = isError,
            label = {
                Text(
                    text = stringResource(R.string.custom_tasbih_round),
                    color = Color.green,
                    fontFamily = RobotoFonts.ROBOTO_REGULAR.getFont(),
                    fontSize = 10.ssp
                )
            },
            colors = TextFieldDefaults.colors(
                errorLabelColor = Color.Red,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                errorContainerColor = Color.White
            ),
            shape = RoundedCornerShape(12.sdp),
            placeholder = {
                Text(
                    text = stringResource(R.string.custom_tasbih_round),
                    color = Color.darkTextGrayColor.copy(0.3f),
                    fontFamily = RobotoFonts.ROBOTO_REGULAR.getFont(),
                    fontSize = 16.ssp
                )
            },
            textStyle = TextStyle(
                color = Color.darkTextGrayColor,
                fontSize = 16.ssp,
                fontFamily = RobotoFonts.ROBOTO_REGULAR.getFont()
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            keyboardActions = KeyboardActions(onDone = { onSaveClick(text) })
        )

        Spacer(modifier = Modifier.height(10.sdp))

        TextButton(
            onClick = { onSaveClick(text) },
            shape = RoundedCornerShape(24.sdp),
            modifier = Modifier
                .fillMaxWidth()
                .height(45.sdp)
                .padding(horizontal = 15.sdp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.green
            )
        ) {
            Text(
                text = stringResource(R.string.save),
                fontFamily = RobotoFonts.ROBOTO_MEDIUM.getFont(),
                fontSize = 16.ssp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(10.sdp))
    }
}