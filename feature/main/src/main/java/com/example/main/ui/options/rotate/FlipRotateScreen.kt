package com.example.main.ui.options.rotate

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.imagesource.SourceViewModel
import com.example.main.components.DefaultOptionScreen
import com.example.main.components.OptionsBottomBar
import com.example.main.models.FlipRotateOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun FlipRotateScreen(
    modifier: Modifier = Modifier,
    sourceViewModel: SourceViewModel = viewModel(),
    save: () -> Unit,
    screenViewModel: FlipRotateViewModel = viewModel(),
) {
    val bitmap = screenViewModel.source
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        screenViewModel.setup(
            processManager = sourceViewModel.processManager!!,
            initialSource = sourceViewModel.currentSource!!
        )
    }

    DefaultOptionScreen(
        modifier = modifier.fillMaxWidth(),
        bitmapImage = bitmap?.asImageBitmap(),
        onSave = {
            scope.launch(Dispatchers.Main) {
                sourceViewModel.flipRotateCounter = screenViewModel.flipRotateCounter
                sourceViewModel.originalSource = sourceViewModel.applyFlipRotate(sourceViewModel.originalSource!!)
                sourceViewModel.applyFlipRotate(screenViewModel.originalSource)?.also { original ->
                    sourceViewModel.processManager?.setSource(original)
                    sourceViewModel.processManager?.setNewOriginalSource(original)
                }

                sourceViewModel.currentSource = bitmap
                save()
            }
        }
    ) {
        OptionsBottomBar(
            modifier = modifier.fillMaxWidth(),
            listOf(
                Pair(FlipRotateOptions.FlipOption, { screenViewModel.performFlip() }),
                Pair(FlipRotateOptions.RotateOption, { screenViewModel.performRotate() })
            )
        )
    }
}