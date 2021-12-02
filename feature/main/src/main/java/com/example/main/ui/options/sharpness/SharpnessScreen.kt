package com.example.main.ui.options.sharpness

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.imagesource.SourceViewModel
import com.example.main.components.SliderScreen
import com.example.main.ui.options.convertValueToPercentage
import com.example.main.ui.options.sharpness.constants.GaussianKernelConstants

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
            getInitialHSVBitmap = sourceViewModel::getHSVBitmap,
            getInitialTuneBitmap = sourceViewModel::getTuneBitmap
        )

        val source = screenViewModel.setInitialSource()

        screenViewModel.source = if (sharpeningValue.value != 0f) {
            sourceViewModel.resetSource(source = source)
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