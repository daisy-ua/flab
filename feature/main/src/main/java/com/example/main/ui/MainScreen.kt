package com.example.main.ui

import androidx.activity.ComponentActivity
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
import com.example.main.components.NotifyToast
import com.example.main.components.OptionsBottomBar
import com.example.main.models.FirstLevelOptions
import com.example.storage.IOProcesses
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    sourceViewModel: SourceViewModel = viewModel(LocalContext.current as ComponentActivity),
    navigateToTuneScreen: () -> Unit,
    navigateToClarityScreen: () -> Unit,
    navigateToRotateScreen: () -> Unit,
    navigateToColorScreen: () -> Unit,
    navigateToEffectScreen: () -> Unit,
    navigateBack: () -> Unit,
) {
    val context = LocalContext.current
    var sourceSaved by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    if (sourceSaved) {
        NotifyToast(context)
        sourceSaved = false
    }

    val bitmap = sourceViewModel.currentSource

    val onSave: () -> Unit = {
        scope.launch {
            try {
                val source = sourceViewModel.getBitmapForSave()
                source?.let {
                    IOProcesses.saveMediaToStorage(
                        context,
                        it,
                    )
                }
                sourceSaved = true
            } catch (ex: Exception) {

            }
        }
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
                    onOpenClick = {
                        sourceViewModel.resetAdjust()
                        sourceViewModel.currentSource = null
                        navigateBack()
                    },
                    onSaveClick = onSave
                )

                bitmap?.asImageBitmap()?.let {
                    Image(
                        bitmap = it,
                        contentDescription = "",
                        modifier = Modifier
                            .width(itemWidth)
                            .height(itemHeight)
                    )
                }
            }

            bitmap?.let {
                OptionsBottomBar(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth(),
                    photoOptions = listOf(
                        Pair(FirstLevelOptions.TuneOption, navigateToTuneScreen),
                        Pair(FirstLevelOptions.Clarity, navigateToClarityScreen),
                        Pair(FirstLevelOptions.FlipRotateOption, navigateToRotateScreen),
                        Pair(FirstLevelOptions.ColorOption, navigateToColorScreen),
                        Pair(FirstLevelOptions.EffectOption, navigateToEffectScreen)
                    )
                )
            }
        }
    }

}