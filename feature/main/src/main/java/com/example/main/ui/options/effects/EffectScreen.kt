package com.example.main.ui.options.effects

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.imagesource.SourceViewModel
import com.example.main.components.BitmapOptionsBottomBar
import com.example.main.components.DefaultOptionScreen
import kotlinx.coroutines.launch

@Composable
fun EffectScreen(
    modifier: Modifier = Modifier,
    sourceViewModel: SourceViewModel = viewModel(LocalContext.current as ComponentActivity),
    screenViewModel: EffectViewModel = viewModel(),
    save: () -> Unit,
) {
    val bitmap = screenViewModel.source
    val optionList = screenViewModel.photoOptions

    val scope = rememberCoroutineScope()

    val onSave: () -> Unit = {
        scope.launch {
            try {
                bitmap?.let {
                    sourceViewModel.currentSource = it
                    sourceViewModel.originalSource =
                        screenViewModel.getBitmapForSave(sourceViewModel.originalSource!!)
                    sourceViewModel.processManager?.setSource(it)
                    sourceViewModel.processManager?.setNewOriginalSource(it)
                    sourceViewModel.resetAdjust()
                }
                save()
            } catch (ex: Exception) {

            }
        }
    }

    LaunchedEffect(key1 = true) {
        screenViewModel.setup(
            sourceViewModel.processManager!!,
            sourceViewModel.currentSource
        )
    }

    DefaultOptionScreen(
        modifier = modifier.fillMaxWidth(),
        bitmapImage = bitmap?.asImageBitmap(),
        onSave = onSave
    ) {
        BitmapOptionsBottomBar(
            modifier = modifier.fillMaxWidth(),
            photoOptions = optionList
        )
    }
}