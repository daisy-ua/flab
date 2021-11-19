package com.example.flab.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.flab.constants.ScreenConstants

sealed class Screen(
    val route: String,
    val arguments: List<NamedNavArgument>
) {
    object Home : Screen(
        route = ScreenConstants.HOME_ROUTE,
        arguments = emptyList()
    )

    object Main : Screen(
        route = ScreenConstants.MAIN_ROUTE,
//        arguments = listOf(navArgument(ScreenConstants.MAIN_IMAGE_ARGUMENT) {
//            type = NavType.StringType
//        })
        arguments = emptyList()
    )

    object TuneEdit : Screen(
        route = ScreenConstants.EDIT_TUNE,
        arguments = emptyList()
    )
}
