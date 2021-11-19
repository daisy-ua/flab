package com.example.flab

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.flab.constants.ScreenConstants
import com.example.flab.ui.navigation.Screen
import com.example.flab.ui.navigation.editGraph
import com.example.flab.ui.navigation.homeGraph
import com.example.flab.ui.theme.FlabTheme
import com.example.home.ui.HomeScreen
import com.example.imagesource.SourceViewModel
import com.example.main.ui.MainScreen
import com.example.main.ui.options.TuneScreen
import org.opencv.android.OpenCVLoader

private lateinit var vm: SourceViewModel

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
        vm = viewModel()

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
        startDestination = ScreenConstants.HOME_GRAPH_ROUTE,
        modifier = modifier
    ) {
        homeGraph(navController, vm)
        editGraph(navController, vm)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HomeScreen(
        R.drawable.ic_logo_stroke
    ) { }
}