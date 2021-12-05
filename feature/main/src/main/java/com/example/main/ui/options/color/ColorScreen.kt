package com.example.main.ui.options.color

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.imagesource.SourceViewModel
import com.example.imagesource.data.AdjustFilters
import com.example.main.components.OptionsBottomBar
import com.example.main.components.SliderScreen
import com.example.main.models.ColorScreenOptions

@Composable
fun ColorScreen(
    modifier: Modifier = Modifier,
    sourceViewModel: SourceViewModel = viewModel(),
    save: () -> Unit,
    screenViewModel: ColorViewModel = viewModel(),
) {
    val bitmap = screenViewModel.source

    var hueValue by remember {
        mutableStateOf(
            sourceViewModel.hueValue?.let { _hue ->
                screenViewModel.huePercentage(_hue)
            } ?: 0f
        )
    }
    var saturationValue by remember {
        mutableStateOf(
            sourceViewModel.saturationValue?.let { _saturation ->
                screenViewModel.saturationPercentage(_saturation)
            } ?: 0f
        )
    }
    var brightnessValue by remember {
        mutableStateOf(
            sourceViewModel.valueValue?.let { _brightness ->
                screenViewModel.valuePercentage(_brightness)
            } ?: 0f
        )
    }

    val sliderValue = remember { mutableStateOf<Float?>(null) }
    var onValueChange by remember { mutableStateOf({ }) }

    val onSourceUpdate: suspend () -> Unit = {
        onValueChange()
        screenViewModel.source = sourceViewModel.applyTransforms(
            hue = screenViewModel.hue(hueValue),
            saturation = screenViewModel.saturation(saturationValue),
            value = screenViewModel.value(brightnessValue)
        )
    }

    val onSave: () -> Unit = {
        sourceViewModel.currentSource = bitmap
        sourceViewModel.hueValue = screenViewModel.hue(hueValue)
        sourceViewModel.saturationValue = screenViewModel.saturation(saturationValue)
        sourceViewModel.valueValue = screenViewModel.value(brightnessValue)
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
                Pair(ColorScreenOptions.HueOption, {
                    sliderValue.value = hueValue
                    onValueChange = {
                        hueValue = sliderValue.value!!
                    }
                }),
                Pair(ColorScreenOptions.SaturationOption, {
                    sliderValue.value = saturationValue
                    onValueChange = {
                        saturationValue = sliderValue.value!!
                    }
                }),
                Pair(ColorScreenOptions.ValueOption, {
                    sliderValue.value = brightnessValue
                    onValueChange = {
                        brightnessValue = sliderValue.value!!
                    }
                })
            )
        )
    }
}