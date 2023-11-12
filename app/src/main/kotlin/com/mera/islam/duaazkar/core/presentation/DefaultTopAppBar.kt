package  com.mera.islam.duaazkar.core.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
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
                    color = darkTextGrayColor,
                    fontSize = 20.ssp,
                    fontFamily = RobotoFonts.ROBOTO_BOLD.getFont()
                )
            } ?: titleContent()
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = applicationBackgroundColor
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
                    var searchText by remember {
                        mutableStateOf("")
                    }

                    BasicTextField(
                        value = searchText,
                        onValueChange = {
                            searchText = it
                            onSearchText(searchText)
                        },
                        decorationBox = { innerTextField ->
                            if (searchText.isEmpty()) {
                                Text(
                                    text = stringResource(id = R.string.search, title ?: "here"),
                                    fontFamily = RobotoFonts.ROBOTO_MEDIUM.getFont(),
                                    fontSize = 16.ssp,
                                    color = darkTextGrayColor.copy(0.8f),
                                    fontStyle = FontStyle.Italic
                                )
                                innerTextField()
                            }
                        },
                        cursorBrush = SolidColor(primary),
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
        },
        modifier = Modifier.shadow(elevation = 5.sdp)
    )
}