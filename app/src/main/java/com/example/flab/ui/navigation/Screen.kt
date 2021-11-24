package com.example.flab.ui.navigation

import androidx.navigation.NamedNavArgument
import com.example.flab.constants.ScreenConstants

sealed class Screen(
    val route: String,
    val arguments: List<NamedNavArgument>,
) {
    object Home : Screen(
        route = ScreenConstants.HOME_ROUTE,
        arguments = emptyList()
    )

    object Main : Screen(
        route = ScreenConstants.MAIN_ROUTE,
        arguments = emptyList()
    )

    object TuneEdit : Screen(
        route = ScreenConstants.EDIT_TUNE,
        arguments = emptyList()
    )

    object FlipRotateEdit : Screen(
        route = ScreenConstants.ROTATE_EDIT,
        arguments = emptyList()
    )
}
