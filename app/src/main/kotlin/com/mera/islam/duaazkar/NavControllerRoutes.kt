package com.mera.islam.duaazkar

import android.net.Uri
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.mera.islam.duaazkar.core.extensions.log
import com.mera.islam.duaazkar.domain.models.dua.DuaType


sealed class NavControllerRoutes(val route: String) {
    data class ROOT(val rout: String = "root") : NavControllerRoutes(rout)
    data class DUA_TASBIH_SCREEN(
        val rout: String = "duaTasbihScreen",
        val duaId: Int = -1
    ): NavControllerRoutes(rout) {
        val listOfArguments = listOf(
            navArgument("duaId") {
                type = NavType.IntType
                defaultValue = duaId
            }
        )

        fun getPath() = "$rout?duaId={duaId}"

        fun getPathWithNavArgs(): String {
            var mainPath = rout

            if (duaId != -1)
                mainPath += "${if (mainPath.contains("?")) "&" else "?"}duaId=$duaId"

            return mainPath
        }
    }
    data class DUA_SCREEN(
        val rout: String = "duaScreen",
        val lastReadId: Int = -1,
        val duaType: DuaType = DuaType.ALL,
        val matchTextList: List<String> = emptyList()
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
            },
            navArgument("matchTextList") {
                type = NavType.StringType
                defaultValue = matchTextList.joinToString(",")
            }
        )

        fun getPath() = "$rout?lastReadId={lastReadId}&duaType={duaType}&matchTextList={matchTextList}"
        fun getPathWithNavArgs(): String {
            var mainPath = rout

            if (lastReadId != -1)
                mainPath += "${if (mainPath.contains("?")) "&" else "?"}lastReadId=$lastReadId"

            mainPath += "${if (mainPath.contains("?")) "&" else "?"}matchTextList=${
                Uri.encode(matchTextList.joinToString(
                    ","
                ))
            }"

            mainPath += "${if (mainPath.contains("?")) "&" else "?"}duaType=${duaType.type}"

            return mainPath
        }
    }

    data class DUA_SEARCH_SCREEN(val rout: String = "duaSearchScreen") :
        NavControllerRoutes(rout)

    data class ASMA_UL_HUSNA(val rout: String = "asmaulHusna"): NavControllerRoutes(rout)

    data class DUA_LISTING_SCREEN(
        val rout: String = "duaListingScreen",
        val duaListArray: IntArray = IntArray(0),
        val matchTextList: List<String> = emptyList()
    ) : NavControllerRoutes(rout) {
        val listOfArguments = listOf(
            navArgument("duaIds") {
                type = NavType.StringType
                defaultValue = duaListArray.joinToString(",")
            },
            navArgument("matchTextList") {
                type = NavType.StringType
                defaultValue = matchTextList.joinToString(",")
            }
        )

        fun getPath() = "$rout?duaIds={duaIds}&matchTextList={matchTextList}"

        fun getPathWithNavArgs(): String {
            var mainPath = rout

            mainPath += "?duaIds=${duaListArray.joinToString(",")}"


            mainPath += "${if (mainPath.contains("?")) "&" else "?"}matchTextList=${
                Uri.encode(
                    matchTextList.joinToString(
                        ","
                    )
                )
            }"

            "DUA_LISTING_SCREEN $mainPath".log()

            return mainPath
        }
    }
}