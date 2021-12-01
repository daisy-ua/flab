package com.example.main.ui.options.sharpness

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.imagesource.SourceViewModel
import com.example.main.R
import com.example.main.components.AmountSlider
import com.example.main.components.DefaultOptionScreen
import com.example.main.ui.options.convertValueToPercentage
import com.example.main.ui.options.sharpness.constants.GaussianKernelConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun SharpnessScreen(
    modifier: Modifier = Modifier,
    sourceViewModel: SourceViewModel = viewModel(),
    save: () -> Unit,
) {
    val screenViewModel: SharpnessViewModel = viewModel()
    val bitmap = screenViewModel.source

    var sharpeningValue by remember {
        mutableStateOf(
            sourceViewModel.sharpnessValue?.let { _sharpness ->
                convertValueToPercentage(
                    _sharpness.toFloat(),
                    GaussianKernelConstants
                )
            } ?: 0f
        )
    }

    val scope = rememberCoroutineScope()
    var job: Job? by remember {
        mutableStateOf(null)
    }

    var originalSource = sourceViewModel.processManager?.originalSource!!

    val tuneBitmap = sourceViewModel.processManager!!.applyLinearTransform(
        sourceViewModel.contrastValue,
        sourceViewModel.brightnessValue,
        originalSource
    ) ?: originalSource

    val hsvBitmap = sourceViewModel.processManager!!.applyHSVTransform(
        sourceViewModel.hueValue,
        sourceViewModel.saturationValue,
        sourceViewModel.valueValue
    ) ?: originalSource

    LaunchedEffect(key1 = true) {
        originalSource =
            sourceViewModel.processManager?.blend?.blend(tuneBitmap, hsvBitmap)!!
        screenViewModel.setupProcessor(originalSource)
        screenViewModel.source = originalSource
        if (sharpeningValue != 0f) {
            screenViewModel.source = sourceViewModel.resetSource(source = screenViewModel.source!!)
        }
    }

    DefaultOptionScreen(
        modifier = modifier.fillMaxWidth(),
        bitmapImage = bitmap?.asImageBitmap(),
        onSave = {
            sourceViewModel.currentSource = bitmap
            sourceViewModel.sharpnessValue = screenViewModel.getConvertedKernelSize(sharpeningValue)
            save()
        }
    ) {
        Column {
            AmountSlider(
                sliderName = R.string.sharpness_option,
                sliderValue = sharpeningValue,
                onValueChange = { sharpeningValue = it },
                onValueChangeFinished = {
                    job?.cancel()
                    job = scope.launch(Dispatchers.Default) {
                        try {
                            screenViewModel.setSharpness(sharpeningValue)
                        } catch (ex: Exception) {
                        }
                    }
                }
            )
        }
    }
}