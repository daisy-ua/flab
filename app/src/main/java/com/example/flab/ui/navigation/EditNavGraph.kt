package com.example.flab.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.flab.constants.ScreenConstants
import com.example.imagesource.SourceViewModel
import com.example.main.ui.options.rotate.FlipRotateScreen
import com.example.main.ui.options.sharpness.Sharpness
import com.example.main.ui.options.tune.TuneScreen

fun NavGraphBuilder.editGraph(navController: NavHostController, vm: SourceViewModel) {
    navigation(
        startDestination = Screen.Main.route,
        route = ScreenConstants.EDIT_GRAPH_ROUTE
    ) {
        composable(Screen.TuneEdit.route) {
            TuneScreen(
                sourceViewModel = vm,
                save = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Home.route)
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Screen.FlipRotateEdit.route) {
            FlipRotateScreen(
                sourceViewModel = vm,
                save = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Home.route)
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Screen.ClarityEdit.route) {
            Sharpness(
                sourceViewModel = vm,
                save = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Home.route)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}