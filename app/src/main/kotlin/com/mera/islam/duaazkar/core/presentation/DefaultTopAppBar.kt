package  com.mera.islam.duaazkar.core.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import com.mera.islam.duaazkar.R
import com.mera.islam.duaazkar.ui.theme.RobotoFonts
import com.mera.islam.duaazkar.ui.theme.applicationBackgroundColor
import com.mera.islam.duaazkar.ui.theme.darkTextGrayColor
import com.mera.islam.duaazkar.ui.theme.primary
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopAppBar(
    title: String? = null,
    hasBackButton: Boolean = true,
    hasSearch: Boolean = true,
    isSearchPressed: Boolean = false,
    searchedText: String = "",
    navHostController: NavHostController,
    titleContent: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    onSearchText: (String) -> Unit = {}
) {
    TopAppBar(
        title = {
            title?.let {
                Text(
                    text = title,
                    color = Color.darkTextGrayColor,
                    fontSize = 20.ssp,
                    fontFamily = RobotoFonts.ROBOTO_BOLD.getFont(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            } ?: titleContent()
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.applicationBackgroundColor
        ),
        navigationIcon = {
            if (hasBackButton) {
                IconButton(onClick = { navHostController.navigateUp() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back_icon),
                        contentDescription = "Localized description",
                        tint = Color.Unspecified
                    )
                }
            }
        },
        actions = {
            if (hasSearch) {
                var search by remember {
                    mutableStateOf(isSearchPressed)
                }

                AnimatedVisibility(visible = search) {

                    TextField(
                        value = searchedText,
                        onValueChange = {
                            onSearchText(it)
                        },
                        placeholder = {
                            if (searchedText.isEmpty()) {
                                Text(
                                    text = stringResource(id = R.string.search, title ?: "here"),
                                    fontFamily = RobotoFonts.ROBOTO_MEDIUM.getFont(),
                                    fontSize = 16.ssp,
                                    color = Color.darkTextGrayColor.copy(0.8f),
                                    fontStyle = FontStyle.Italic
                                )
                            }
                        },
                        trailingIcon = {
                            AnimatedVisibility(visible = searchedText.isNotEmpty()) {
                                IconButton(onClick = {
                                    onSearchText(searchedText)
                                }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_cancel_icon),
                                        contentDescription = "clear",
                                        tint = Color.Unspecified,
                                        modifier = Modifier.size(24.sdp)
                                    )
                                }
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            cursorColor = Color.primary,
                            focusedTextColor = Color.darkTextGrayColor,
                            unfocusedTextColor = Color.darkTextGrayColor.copy(0.5f),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                        ),
                        textStyle = TextStyle(
                            background = Color.Transparent,
                            fontSize = 16.ssp,
                            fontFamily = RobotoFonts.ROBOTO_MEDIUM.getFont()
                        ),
                        modifier = Modifier.fillMaxWidth(0.9f)
                    )
                }

                AnimatedVisibility(visible = !search) {
                    IconButton(onClick = { search = !search }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search_icon),
                            contentDescription = "Search from types",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(18.sdp)
                        )
                    }
                }
            } else actions()
        }
    )
}