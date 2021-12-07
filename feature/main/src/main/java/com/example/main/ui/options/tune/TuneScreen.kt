package com.example.main.ui.options.tune

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.imagesource.SourceViewModel
import com.example.main.components.OptionsBottomBar
import com.example.main.components.SliderScreen
import com.example.main.models.TuneScreenOptions


@Composable
fun TuneScreen(
    modifier: Modifier = Modifier,
    sourceViewModel: SourceViewModel = viewModel(LocalContext.current as ComponentActivity),
    screenViewModel: TuneViewModel = viewModel(),
    save: () -> Unit,
) {
    val bitmap = screenViewModel.source

    var contrastValue by remember {
        mutableStateOf(
            sourceViewModel.contrastValue?.let { _contrast ->
                screenViewModel.contrastPercentage(_contrast)
            } ?: 0f
        )
    }
    var brightnessValue by remember {
        mutableStateOf(
            sourceViewModel.brightnessValue?.let { _brightness ->
                screenViewModel.brightnessPercentage(_brightness)
            } ?: 0f
        )
    }

    val sliderValue = remember { mutableStateOf<Float?>(null) }
    var onValueChange by remember { mutableStateOf({ }) }
    var currentSelectedModeId by remember { mutableStateOf<Int?>(null) }

    val onSourceUpdate: suspend () -> Unit = {
        onValueChange()
        screenViewModel.source = sourceViewModel.applyTransforms(
            contrast = screenViewModel.contrast(contrastValue),
            brightness = screenViewModel.brightness(brightnessValue)
        )
    }

    val onSave: () -> Unit = {
        sourceViewModel.currentSource = bitmap
        sourceViewModel.contrastValue = screenViewModel.contrast(contrastValue)
        sourceViewModel.brightnessValue = screenViewModel.brightness(brightnessValue)
        save()
    }

    LaunchedEffect(key1 = true) {
        screenViewModel.setup(
            processManager = sourceViewModel.processManager!!,
            sourceViewModel.applyTransforms()
        )
    }

    SliderScreen(
        imageSource = bitmap,
        sliderValue = sliderValue,
        onSave = onSave,
        onSliderUpdate = onSourceUpdate
    ) {
        OptionsBottomBar(
            modifier = modifier.fillMaxWidth(),
            photoOptions = listOf(
                Pair(TuneScreenOptions.Contrast, {
                    currentSelectedModeId = TuneScreenOptions.Contrast.nameId
                    sliderValue.value = contrastValue
                    onValueChange = {
                        contrastValue = sliderValue.value!!
                    }
                }),
                Pair(TuneScreenOptions.Brightness, {
                    currentSelectedModeId = TuneScreenOptions.Brightness.nameId
                    sliderValue.value = brightnessValue
                    onValueChange = {
                        brightnessValue = sliderValue.value!!
                    }
                })
            ),
            currentSelectedId = currentSelectedModeId
        )
    }
}