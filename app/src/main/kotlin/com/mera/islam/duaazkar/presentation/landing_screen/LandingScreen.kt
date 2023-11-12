package  com.mera.islam.duaazkar.presentation.landing_screen

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import  com.mera.islam.duaazkar.R
import  com.mera.islam.duaazkar.core.extensions.listToString
import  com.mera.islam.duaazkar.core.presentation.DuaAzkarWithBackground
import  com.mera.islam.duaazkar.core.presentation.permissions.RequestPermission
import  com.mera.islam.duaazkar.core.utils.SdkHelper
import  com.mera.islam.duaazkar.presentation.landing_screen.components.LandingScreenTopBar
import  com.mera.islam.duaazkar.presentation.landing_screen.components.LandingScreenTopSelection
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
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            topBar = { LandingScreenTopBar {} }
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                Spacer(modifier = Modifier.height(10.sdp))
                Row(
                    modifier = Modifier
                        .fillMaxHeight(0.4f)
                        .padding(start = 10.sdp)
                        .horizontalScroll(rememberScrollState())
                ) {
                    LandingScreenTopSelection(
                        modifier = Modifier.padding(end = 5.sdp),
                        resource = R.drawable.ic_morning_azkar
                    )

                    LandingScreenTopSelection(
                        modifier = Modifier.padding(end = 5.sdp),
                        resource = R.drawable.ic_evening_azkar
                    )
                }
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
