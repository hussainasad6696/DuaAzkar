package  com.mera.islam.duaazkar.core.presentation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import ir.kaaveh.sdpcompose.ssp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopAppBar(
    title: String? = null,
    hasBackButton: Boolean = true,
    navHostController: NavHostController,
    titleContent: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = {
            title?.let {
                Text(text = title, color = Color.White, fontSize = 12.ssp)
            } ?: titleContent()
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.Blue.copy(alpha = 0.2f),
            titleContentColor = Color.Black.copy(0.8f)
        ),
        navigationIcon = {
            if (hasBackButton) {
                IconButton(onClick = { navHostController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Localized description",
                        tint = Color.White
                    )
                }
            }
        },
        actions = actions
    )
}