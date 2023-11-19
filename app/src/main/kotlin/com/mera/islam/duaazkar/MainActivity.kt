package com.mera.islam.duaazkar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mera.islam.duaazkar.core.extensions.log
import com.mera.islam.duaazkar.domain.models.dua.DuaType
import com.mera.islam.duaazkar.presentation.asma_ul_husna_screen.AsmaulHusnaScreen
import com.mera.islam.duaazkar.presentation.dua_listing_screen.DuaListingScreen
import com.mera.islam.duaazkar.presentation.dua_screen.DuaScreen
import com.mera.islam.duaazkar.presentation.dua_search_screen.DuaSearchScreen
import com.mera.islam.duaazkar.presentation.landing_screen.LandingScreen
import com.mera.islam.duaazkar.ui.theme.DuaAzkarTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DuaAzkarTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val windowSizeClass = calculateWindowSizeClass(activity = this)
                    Beginning(windowSizeClass)
                }
            }
        }
    }
}

@Composable
fun Beginning(windowSizeClass: WindowSizeClass) {
    val navController = rememberNavController()
    val root = NavControllerRoutes.ROOT()
    NavHost(navController = navController, startDestination = root.route) {
        composable(root.route,
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition() }) {
            LandingScreen(navController = navController, windowSizeClass = windowSizeClass)
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
            val lastReadId =
                backStackEntry.arguments?.getInt("lastReadId") ?: -1

            val duaType =
                DuaType.toDuaType(backStackEntry.arguments?.getInt("duaType") ?: DuaType.ALL.type)

            val matchTextList =
                backStackEntry.arguments?.getString("matchTextList")?.split(",") ?: emptyList()

            DuaScreen(
                navHostController = navController,
                lastRead = lastReadId,
                duaType = duaType,
                matchTextList = matchTextList
            )
        }
        composable(NavControllerRoutes.DUA_SEARCH_SCREEN().route,
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition() }) {
            DuaSearchScreen(navController)
        }

        val duaListingNav = NavControllerRoutes.DUA_LISTING_SCREEN()
        composable(
            duaListingNav.getPath(),
            arguments = duaListingNav.listOfArguments,
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition() }
        ) { backStackEntry ->
            val arrayIds = backStackEntry.arguments?.getString("duaIds") ?: "0"
            val matchTextList =
                backStackEntry.arguments?.getString("matchTextList") ?: ""

            "composable DuaListingScreen $matchTextList".log()

            DuaListingScreen(
                navHostController = navController,
                duaIds = arrayIds.split(",").mapNotNull { it.toIntOrNull() },
                matchTextList = matchTextList.split(",")
            )
        }

        composable(
            NavControllerRoutes.ASMA_UL_HUSNA().route,
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition() }
        ) {
            AsmaulHusnaScreen(navController = navController)
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