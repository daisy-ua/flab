package com.example.main.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.components.DefaultScreenUI
import com.example.imagesource.SourceViewModel
import com.example.main.components.MainNavTopBar
import com.example.main.components.OptionsBottomBar
import com.example.main.components.SaveToGallery
import com.example.main.models.FirstLevelOptions

@Composable
fun MainScreen(
    sourceViewModel: SourceViewModel = viewModel(),
    navigateToTuneScreen: () -> Unit,
    navigateToClarityScreen: () -> Unit,
    navigateToRotateScreen: () -> Unit,
    navigateToColorScreen: () -> Unit
) {
    val context = LocalContext.current
    var saveClicked by remember { mutableStateOf(false) }

    if (saveClicked) {
        SaveToGallery(context, sourceViewModel.currentSource!!)
        saveClicked = false
    }

    DefaultScreenUI {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val itemWidth = maxWidth
            val itemHeight = maxHeight - 150.dp


            Column {

                MainNavTopBar(
                    onSaveClick = { saveClicked = true }
                )

                Image(
                    bitmap = sourceViewModel.currentSource!!.asImageBitmap(),
                    contentDescription = "",
                    modifier = Modifier
                        .width(itemWidth)
                        .height(itemHeight)
                )
            }

            OptionsBottomBar(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth(),
                photoOptions = listOf(
                    Pair(FirstLevelOptions.TuneOption, navigateToTuneScreen),
                    Pair(FirstLevelOptions.Clarity, navigateToClarityScreen),
                    Pair(FirstLevelOptions.FlipRotateOption, navigateToRotateScreen),
                    Pair(FirstLevelOptions.ColorOption, navigateToColorScreen)
                )
            )
        }
    }

}