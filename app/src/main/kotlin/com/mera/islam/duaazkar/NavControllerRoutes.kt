package com.mera.islam.duaazkar

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.mera.islam.duaazkar.domain.models.DuaType


sealed class NavControllerRoutes(val route: String) {
    data class ROOT(val rout: String = "root") : NavControllerRoutes(rout)
    data class DUA_SCREEN(
        val rout: String = "duaScreen",
        val lastReadId: Int = -1,
        val duaType: DuaType = DuaType.ALL
    ) :
        NavControllerRoutes(rout) {
        val listOfArguments = listOf(
            navArgument("lastReadId") {
                type = NavType.IntType
                defaultValue = -1
            },
            navArgument("duaType") {
                type = NavType.IntType
                defaultValue = duaType.type
            }
        )
        fun getPath() = "$rout?lastReadId={lastReadId}&duaType={duaType}"
        fun getPathWithNavArgs(): String {
            var mainPath = rout

            if (lastReadId != -1)
                mainPath += "${if (mainPath.contains("?")) "&" else "?"}lastReadId=$lastReadId"

            mainPath += "${if (mainPath.contains("?")) "&" else "?"}duaType=${duaType.type}"

            return mainPath
        }
    }

    data class DUA_BOOKMARK_SCREEN(val rout: String = "duaBookmarkScreen") :
        NavControllerRoutes(rout)
}