package com.example.flab.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.flab.constants.ScreenConstants
import com.example.imagesource.SourceViewModel
import com.example.main.ui.options.TuneScreen

fun NavGraphBuilder.editGraph(navController: NavHostController, vm: SourceViewModel) {
    navigation(
        startDestination = Screen.Main.route,
        route = ScreenConstants.EDIT_GRAPH_ROUTE
    ) {
        composable(Screen.TuneEdit.route) {
            TuneScreen(
                sourceViewModel = vm,
                save = { navController.navigate(Screen.Main.route) {
                    popUpTo(Screen.Home.route)
                    launchSingleTop = true
                } }
            )
        }
    }
}