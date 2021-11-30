package com.example.flab.ui.navigation

import android.net.Uri
import android.util.Log
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
            CreateHomeScreen(navController, vm)
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
        navigateToClarityScreen = { navController.navigate(Screen.ClarityEdit.route) },
        navigateToRotateScreen = { navController.navigate(Screen.FlipRotateEdit.route) },
        navigateToColorScreen = { navController.navigate(Screen.ColorEdit.route) }
    )

@Composable
private fun CreateHomeScreen(navController: NavHostController, vm: SourceViewModel) {
    HomeScreen(
        R.drawable.ic_logo_stroke,
        navigateToMainScreen = { imageUri ->
            val uriDecoded = Uri.decode(imageUri)
            vm.setupBitmap(Uri.parse(uriDecoded))
            navigateToMainScreen(navController)
        }
    )
}

private fun navigateToMainScreen(navController: NavHostController) =
    navController.navigate(Screen.Main.route) {
        popUpTo(Screen.Home.route) { inclusive = true }
        launchSingleTop = true
    }