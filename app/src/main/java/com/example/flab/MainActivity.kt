package com.example.flab

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.flab.constants.ScreenConstants
import com.example.flab.ui.navigation.Screen
import com.example.flab.ui.theme.FlabTheme
import com.example.home.ui.HomeScreen
import com.example.main.ui.MainScreen
import org.opencv.android.OpenCVLoader

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlabApp()
        }
    }
}

@Composable
fun FlabApp() {
    FlabTheme {
        initOpenCV()
        val navController = rememberNavController()

        NavigationComponent(navController)
    }
}

fun initOpenCV() {
    if (OpenCVLoader.initDebug())
        Log.d("OpenCVDebug", "Success")
    else Log.d("OpenCVDebug", "Failure")
}

@Composable
fun NavigationComponent(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
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
            MainScreen(imageUri = Uri.parse(uri))
        }
    }
}

private fun navigateToMainScreen(navController: NavHostController, imageUri: String) =
    navController.navigate("${Screen.Main.route}/$imageUri") {
//        popUpTo(Screen.Home.route) { inclusive = true }
    }

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HomeScreen(
        R.drawable.ic_logo_stroke
    ) { }
}