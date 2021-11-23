package com.example.flab.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.flab.R
import com.example.flab.constants.ScreenConstants
import com.example.home.ui.HomeScreen
import com.example.imagesource.SourceViewModel
import com.example.main.ui.MainScreen

fun NavGraphBuilder.homeGraph(navController: NavHostController, vm: SourceViewModel) {
    navigation(
        startDestination = Screen.Home.route,
        route = ScreenConstants.HOME_GRAPH_ROUTE
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                R.drawable.ic_logo_stroke,
                navigateToMainScreen = { imageUri ->
                    navigateToMainScreen(navController, imageUri)
                }
            )
        }

        composable(
            Screen.Main.route +
                    "/{${ScreenConstants.MAIN_IMAGE_ARGUMENT}}"
        ) { backStackEntry ->
            val uri = backStackEntry.arguments?.getString(ScreenConstants.MAIN_IMAGE_ARGUMENT)
            vm.sourceString = uri
            vm.sourceUri = Uri.parse(uri)
            vm.currentSource = vm.originalSource
            CreateMainScreen(navController, vm)
        }

        composable(Screen.Main.route) {
            CreateMainScreen(navController, vm)
        }
    }
}

@Composable
private fun CreateMainScreen(navController: NavHostController, vm: SourceViewModel) =
    MainScreen(
        vm,
        navigateToTuneScreen = { navController.navigate(Screen.TuneEdit.route) },
        navigateToRotateScreen = { navController.navigate(Screen.FlipRotateEdit.route) }
    )

private fun navigateToMainScreen(navController: NavHostController, imageUri: String) =
    navController.navigate("${Screen.Main.route}/$imageUri") {
//        popUpTo(Screen.Home.route) { inclusive = true }
        launchSingleTop = true
    }