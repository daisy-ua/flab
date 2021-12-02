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

    val onSourceUpdate: suspend () -> Unit = {
        onValueChange()
        screenViewModel.updateTuneBitmap(contrastValue, brightnessValue)

        val source =
            screenViewModel.mergeSource(screenViewModel.hsvBitmap!!, screenViewModel.tuneBitmap!!)

        screenViewModel.source = sourceViewModel.sharpnessValue?.let {
            sourceViewModel.resetSource(source = source)
        } ?: source
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
            getInitialHSVBitmap = sourceViewModel::getHSVBitmap,
            getInitialTuneBitmap = sourceViewModel::getTuneBitmap
        )

        val source = screenViewModel.setInitialSource()

        screenViewModel.source = sourceViewModel.sharpnessValue?.let {
            sourceViewModel.resetSource(source = source)
        } ?: source
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
                    sliderValue.value = contrastValue
                    onValueChange = {
                        contrastValue = sliderValue.value!!
                    }
                }),
                Pair(TuneScreenOptions.Brightness, {
                    sliderValue.value = brightnessValue
                    onValueChange = {
                        brightnessValue = sliderValue.value!!
                    }
                })
            )
        )
    }
}