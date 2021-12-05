package com.example.main.ui.options.sharpness

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.imagesource.SourceViewModel
import com.example.main.components.SliderScreen
import com.example.main.ui.options.sharpness.constants.GaussianKernelConstants
import com.example.main.ui.options.utils.convertValueToPercentage

@Composable
fun SharpnessScreen(
    modifier: Modifier = Modifier,
    sourceViewModel: SourceViewModel = viewModel(),
    save: () -> Unit,
    screenViewModel: SharpnessViewModel = viewModel(),
) {
    val bitmap = screenViewModel.source

    val sharpeningValue = remember {
        mutableStateOf(
            sourceViewModel.sharpnessValue?.let { _sharpness ->
                convertValueToPercentage(
                    _sharpness.toFloat(),
                    GaussianKernelConstants
                )
            } ?: 0f
        )
    }

    val onSourceUpdate: suspend () -> Unit = {
        screenViewModel.updateSource(sharpeningValue.value)
    }

    val onSave: () -> Unit = {
        sourceViewModel.currentSource = bitmap
        sourceViewModel.sharpnessValue =
            screenViewModel.sharpness(sharpeningValue.value)
        save()
    }

    LaunchedEffect(key1 = true) {
        screenViewModel.setup(
            processManager = sourceViewModel.processManager!!,
            source = null
        )
        val source = sourceViewModel.applyColorTransforms()

        screenViewModel.processManager.updateSharpness(source)

        screenViewModel.source = if (sharpeningValue.value != 0f) {
            sourceViewModel.processManager?.applySharpness(
                sourceViewModel.sharpnessValue,
                source = source!!
            )
        } else source
    }

    SliderScreen(
        modifier = modifier,
        imageSource = bitmap,
        sliderValue = sharpeningValue as MutableState<Float?>,
        onSave = onSave,
        onSliderUpdate = onSourceUpdate,
        bottomBar = { }
    )
}