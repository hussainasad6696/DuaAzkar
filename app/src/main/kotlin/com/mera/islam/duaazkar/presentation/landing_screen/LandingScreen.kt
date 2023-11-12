package  com.mera.islam.duaazkar.presentation.landing_screen

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mera.islam.duaazkar.NavControllerRoutes
import  com.mera.islam.duaazkar.R
import  com.mera.islam.duaazkar.core.extensions.listToString
import  com.mera.islam.duaazkar.core.presentation.DuaAzkarWithBackground
import  com.mera.islam.duaazkar.core.presentation.permissions.RequestPermission
import  com.mera.islam.duaazkar.core.utils.SdkHelper
import com.mera.islam.duaazkar.presentation.categories_screen.CategoriesScreen
import com.mera.islam.duaazkar.presentation.dua_bookmark_screen.DuaBookmarkScreen
import com.mera.islam.duaazkar.presentation.home_screen.HomeScreen
import com.mera.islam.duaazkar.presentation.landing_screen.components.BottomNavItems
import com.mera.islam.duaazkar.presentation.landing_screen.components.LandingScreenBottomNavBar
import  com.mera.islam.duaazkar.presentation.landing_screen.components.LandingScreenTopBar
import  com.mera.islam.duaazkar.presentation.landing_screen.components.LandingScreenTopSelection
import ir.kaaveh.sdpcompose.sdp

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

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            topBar = { LandingScreenTopBar {} },
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
                BottomNavItems.Home -> HomeScreen(modifier = Modifier.padding(paddingValues))
                BottomNavItems.Categories -> CategoriesScreen(
                    modifier = Modifier.padding(
                        paddingValues
                    )
                )

                BottomNavItems.Bookmarks -> DuaBookmarkScreen(
                    modifier = Modifier.padding(
                        paddingValues
                    ), onDuaClick = { dua ->
                        val duaNav = NavControllerRoutes.DUA_SCREEN(lastReadId = dua.getDataId())
                        navController.navigate(duaNav.getPathWithNavArgs())
                    })
            }
        }
    }

    RequestLocationPermission(context)
    RequestPostNotificationPermission(context)
}
//Column(
//modifier = Modifier
//.padding(paddingValues)
//.padding(16.sdp),
//horizontalAlignment = Alignment.CenterHorizontally,
//verticalArrangement = Arrangement.SpaceEvenly
//) {
//    Button(
//        onClick = { navController.navigate(NavControllerRoutes.DUA_SCREEN().route) },
//        modifier = Modifier.fillMaxWidth(),
//        content = {
//            Text(text = "Open all duas")
//        }
//    )
//
//    Button(
//        onClick = {
//            navController.navigate(
//                NavControllerRoutes.DUA_SCREEN(duaType = DuaType.Morning_Evening_Night)
//                    .getPathWithNavArgs()
//            )
//        },
//        modifier = Modifier.fillMaxWidth(),
//        content = {
//            Text(text = "Open morning evening")
//        }
//    )
//
//    Button(
//        onClick = { navController.navigate(NavControllerRoutes.DUA_BOOKMARK_SCREEN().route) },
//        modifier = Modifier.fillMaxWidth(),
//        content = {
//            Text(text = "Open bookmarks")
//        }
//    )
//
//    Button(
//        onClick = { viewModel.printAllPrayerTimes() },
//        modifier = Modifier.fillMaxWidth(),
//        content = {
//            Text(text = "Prayer time test")
//        }
//    )
//
//    val duaLastReadId by viewModel.settingsDuaLastRead.collectAsStateWithLifecycle()
//
//    if (duaLastReadId != -1) {
//        val duaNav = NavControllerRoutes.DUA_SCREEN(lastReadId = duaLastReadId)
//        Button(
//            onClick = { navController.navigate(duaNav.getPathWithNavArgs()) },
//            modifier = Modifier.fillMaxWidth(),
//            content = {
//                Text(text = "Dua last read available by id $duaLastReadId")
//            }
//        )
//    }
//}

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
