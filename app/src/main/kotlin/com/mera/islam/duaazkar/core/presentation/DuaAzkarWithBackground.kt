package  com.mera.islam.duaazkar.core.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mera.islam.duaazkar.ui.theme.applicationBackgroundColor

@Composable
fun DuaAzkarWithBackground(
    modifier: Modifier = Modifier,
    addScaffolding: Boolean = false,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = applicationBackgroundColor
    ) {
        CompositionLocalProvider(LocalAbsoluteTonalElevation provides 0.dp) {
            if (!addScaffolding)
                content(PaddingValues.Absolute())
            else {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.onBackground,
                    topBar = topBar,
                    bottomBar = bottomBar
                ) { content(it) }
            }
        }
    }
}