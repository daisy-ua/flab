package com.example.flab.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.flab.constants.ScreenConstants
import com.example.imagesource.SourceViewModel
import com.example.main.ui.options.color.ColorScreen
import com.example.main.ui.options.rotate.FlipRotateScreen
import com.example.main.ui.options.sharpness.SharpnessScreen
import com.example.main.ui.options.tune.TuneScreen

fun NavGraphBuilder.editGraph(navController: NavHostController, vm: SourceViewModel) {
    navigation(
        startDestination = Screen.Main.route,
        route = ScreenConstants.EDIT_GRAPH_ROUTE
    ) {
        composable(Screen.TuneEdit.route) {
            TuneScreen(
                sourceViewModel = vm,
                save = { navController.popBackStack() }
            )
        }

        composable(Screen.FlipRotateEdit.route) {
            FlipRotateScreen(
                sourceViewModel = vm,
                save = { navController.popBackStack() }
            )
        }

        composable(Screen.ClarityEdit.route) {
            SharpnessScreen(
                sourceViewModel = vm,
                save = { navController.popBackStack() }
            )
        }

        composable(Screen.ColorEdit.route) {
            ColorScreen(
                sourceViewModel = vm,
                save = { navController.popBackStack() }
            )
        }
    }
}