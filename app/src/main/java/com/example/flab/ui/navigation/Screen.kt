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

    object ClarityEdit : Screen(
        route = ScreenConstants.EDIT_CLARITY,
        arguments = emptyList()
    )

    object TuneEdit : Screen(
        route = ScreenConstants.EDIT_TUNE,
        arguments = emptyList()
    )

    object FlipRotateEdit : Screen(
        route = ScreenConstants.EDIT_ROTATE,
        arguments = emptyList()
    )

    object ColorEdit : Screen(
        route = ScreenConstants.EDIT_COLOR,
        arguments = emptyList()
    )

    object EffectEdit : Screen(
        route = ScreenConstants.EDIT_EFFECT,
        arguments = emptyList()
    )
}
