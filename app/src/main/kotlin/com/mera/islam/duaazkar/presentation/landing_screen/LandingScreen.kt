package  com.mera.islam.duaazkar.presentation.landing_screen

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mera.islam.duaazkar.NavControllerRoutes
import  com.mera.islam.duaazkar.R
import  com.mera.islam.duaazkar.core.extensions.listToString
import com.mera.islam.duaazkar.core.presentation.DefaultTopAppBar
import  com.mera.islam.duaazkar.core.presentation.DuaAzkarWithBackground
import  com.mera.islam.duaazkar.core.presentation.permissions.RequestPermission
import  com.mera.islam.duaazkar.core.utils.SdkHelper
import com.mera.islam.duaazkar.presentation.categories_screen.CategoriesScreen
import com.mera.islam.duaazkar.presentation.dua_bookmark_screen.DuaBookmarkScreen
import com.mera.islam.duaazkar.presentation.home_screen.HomeScreen
import com.mera.islam.duaazkar.presentation.landing_screen.components.BottomNavItems
import com.mera.islam.duaazkar.presentation.landing_screen.components.LandingScreenBottomNavBar
import  com.mera.islam.duaazkar.presentation.landing_screen.components.LandingScreenTopBar
import ir.kaaveh.sdpcompose.sdp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingScreen(
    navController: NavHostController,
    viewModel: LandingScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = true, block = {
        viewModel.setAlarmForTomorrow()
    })

    DuaAzkarWithBackground {
        var selectedScreen by remember {
            mutableStateOf(BottomNavItems.Home)
        }

        BackHandler(enabled = selectedScreen != BottomNavItems.Home) {
            selectedScreen = BottomNavItems.Home
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            topBar = {
                if (selectedScreen == BottomNavItems.Home) LandingScreenTopBar {}
                else DefaultTopAppBar(
                    navHostController = navController,
                    hasBackButton = false,
                    hasSearch = false,
                    title = selectedScreen.name,
                    actions = {
                        if (selectedScreen == BottomNavItems.Categories)
                            IconButton(onClick = {
                                navController.navigate(NavControllerRoutes.DUA_SEARCH_SCREEN().route)
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_search_icon),
                                    contentDescription = "Search from types",
                                    tint = Color.Unspecified,
                                    modifier = Modifier.size(18.sdp)
                                )
                            }
                    }
                )
            },
            bottomBar = {
                LandingScreenBottomNavBar(
                    selectedScreen = selectedScreen,
                    onItemClick = {
                        selectedScreen = it
                    }
                )
            }
        ) { paddingValues ->
            when (selectedScreen) {
                BottomNavItems.Home -> HomeScreen(
                    navController = navController,
                    modifier = Modifier.padding(paddingValues),
                    viewModel = viewModel,
                    onViewAllClick = { selectedScreen = BottomNavItems.Categories }
                )

                BottomNavItems.Categories -> CategoriesScreen(
                    modifier = Modifier.padding(
                        paddingValues
                    ),
                    viewModel = viewModel,
                    navController = navController
                )

                BottomNavItems.Bookmarks -> DuaBookmarkScreen(
                    modifier = Modifier.padding(
                        paddingValues
                    ),
                    viewModel = viewModel,
                    navController = navController
                )
            }
        }
    }

    RequestLocationPermission(context)
    RequestPostNotificationPermission(context)
}

@Composable
fun RequestLocationPermission(context: Context) {
    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_DENIED && ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_DENIED
    ) {
        RequestPermission(
            title = stringResource(id = R.string.request_location_permission),
            subTitle = stringResource(id = R.string.location_permission_required),
            permissions = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            allPermissionsGranted = {
                Toast.makeText(
                    context,
                    "Location permission granted",
                    Toast.LENGTH_SHORT
                ).show()
            },
            permissionsDenied = {
                Toast.makeText(context, "Denied ${it.listToString()}", Toast.LENGTH_SHORT).show()
            }
        )
    }
}

@Composable
fun RequestPostNotificationPermission(context: Context) {
    if (SdkHelper.isTiramisu() && ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_DENIED
    ) {
        RequestPermission(
            permissions = arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            allPermissionsGranted = {
                Toast.makeText(
                    context,
                    "Notification permission granted",
                    Toast.LENGTH_SHORT
                ).show()
            },
            permissionsDenied = {
                Toast.makeText(context, "Denied ${it.listToString()}", Toast.LENGTH_SHORT).show()
            }
        )
    }
}
