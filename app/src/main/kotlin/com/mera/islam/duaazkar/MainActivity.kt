package com.mera.islam.duaazkar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mera.islam.duaazkar.domain.models.DuaType
import com.mera.islam.duaazkar.presentation.dua_bookmark_screen.DuaBookmarkScreen
import com.mera.islam.duaazkar.presentation.dua_screen.DuaScreen
import com.mera.islam.duaazkar.presentation.dua_search_screen.DuaSearchScreen
import com.mera.islam.duaazkar.presentation.landing_screen.LandingScreen
import com.mera.islam.duaazkar.ui.theme.DuaAzkarTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DuaAzkarTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Beginning()
                }
            }
        }
    }
}

@Composable
fun Beginning() {
    val navController = rememberNavController()
    val root = NavControllerRoutes.ROOT()
    NavHost(navController = navController, startDestination = root.route) {
        composable(root.route,
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition() }) {
            LandingScreen(navController)
        }
        val duaScreenNav = NavControllerRoutes.DUA_SCREEN()
        composable(
            duaScreenNav.getPath(),
            arguments = duaScreenNav.listOfArguments,
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition() }
        ) { backStackEntry ->
            val lastReadId = backStackEntry.arguments?.getInt("lastReadId") ?: -1
            val duaType =
                DuaType.toDuaType(backStackEntry.arguments?.getInt("duaType") ?: DuaType.ALL.type)
            DuaScreen(navHostController = navController, lastRead = lastReadId, duaType = duaType)
        }
        composable(NavControllerRoutes.DUA_SEARCH_SCREEN().route,
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition() }) {
            DuaSearchScreen(navController)
        }
    }
}

fun AnimatedContentTransitionScope<NavBackStackEntry>.enterTransition() = slideIntoContainer(
    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
    animationSpec = tween(700)
)

fun AnimatedContentTransitionScope<NavBackStackEntry>.exitTransition() = slideOutOfContainer(
    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
    animationSpec = tween(700)
)

fun AnimatedContentTransitionScope<NavBackStackEntry>.popEnterTransition() = slideIntoContainer(
    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
    animationSpec = tween(700)
)

fun AnimatedContentTransitionScope<NavBackStackEntry>.popExitTransition() = slideOutOfContainer(
    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
    animationSpec = tween(700)
)
