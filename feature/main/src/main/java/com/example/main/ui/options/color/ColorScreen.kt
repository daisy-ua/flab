package com.example.main.ui.options.color

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.imagesource.SourceViewModel
import com.example.main.components.AmountSlider
import com.example.main.components.DefaultOptionScreen
import com.example.main.components.OptionsBottomBar
import com.example.main.constants.HueConstants
import com.example.main.constants.SaturationConstants
import com.example.main.constants.ValueConstants
import com.example.main.models.ColorScreenOptions
import com.example.main.ui.options.convertPercentageToValue
import com.example.main.ui.options.convertValueToPercentage
import com.example.main.ui.options.tune.constants.BrightnessConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun ColorScreen(
    modifier: Modifier = Modifier,
    sourceViewModel: SourceViewModel = viewModel(),
    save: () -> Unit,
) {
    val screenViewModel: ColorViewModel = viewModel()
    val bitmap = screenViewModel.source

    var hueValue by remember {
        mutableStateOf(
            sourceViewModel.hueValue?.let { _hue ->
                convertValueToPercentage(
                    _hue.toFloat(),
                    HueConstants
                )
            } ?: 0f
        )
    }
    var saturationValue by remember {
        mutableStateOf(
            sourceViewModel.saturationValue?.let { _saturation ->
                convertValueToPercentage(
                    _saturation.toFloat(),
                    SaturationConstants
                )
            } ?: 0f
        )
    }
    var brightnessValue by remember {
        mutableStateOf(
            sourceViewModel.valueValue?.let { _brightness ->
                convertValueToPercentage(
                    _brightness.toFloat(),
                    BrightnessConstants
                )
            } ?: 0f
        )
    }

    var sliderValue by remember { mutableStateOf<Float?>(null) }
    var onValueChange by remember { mutableStateOf({ }) }

    val scope = rememberCoroutineScope()
    var job: Job? by remember {
        mutableStateOf(null)
    }

    LaunchedEffect(key1 = true) {
        sourceViewModel.updateSource(hue = null, saturation = null, value = null)
        screenViewModel.processManager = sourceViewModel.processManager
        screenViewModel.processManager?.hsvTransform?.updateSource(sourceViewModel.currentSource!!)
        if (hueValue == 0f && saturationValue == 0f && brightnessValue == 0f) {
            screenViewModel.source = sourceViewModel.currentSource!!
        } else {
            screenViewModel.setColorTransform(
                hueValue,
                saturationValue,
                brightnessValue
            )
        }
    }

    DefaultOptionScreen(
        modifier = modifier.fillMaxWidth(),
        bitmapImage = bitmap?.asImageBitmap(),
        onSave = {
            sourceViewModel.currentSource = bitmap
            sourceViewModel.hueValue =
                convertPercentageToValue(hueValue, HueConstants)
            sourceViewModel.saturationValue =
                convertPercentageToValue(saturationValue, SaturationConstants)
            sourceViewModel.valueValue =
                convertPercentageToValue(brightnessValue, ValueConstants)
            screenViewModel.source = null
            screenViewModel.processManager = null
            save()
        }
    ) {
        Column {
            sliderValue?.let { value ->
                AmountSlider(
                    sliderValue = value,
                    onValueChange = { sliderValue = it },
                    onValueChangeFinished = {
                        job?.cancel()
                        job = scope.launch(Dispatchers.Main) {
                            try {
                                onValueChange()
                                screenViewModel.setColorTransform(
                                    hueValue,
                                    saturationValue,
                                    brightnessValue
                                )
                            } catch (ex: Exception) {
                            }
                        }
                    }
                )
            }

            OptionsBottomBar(
                modifier = modifier.fillMaxWidth(),
                photoOptions = listOf(
                    Pair(ColorScreenOptions.HueOption, {
                        sliderValue = hueValue
                        onValueChange = {
                            hueValue = sliderValue!!
                        }
                    }),
                    Pair(ColorScreenOptions.SaturationOption, {
                        sliderValue = saturationValue
                        onValueChange = {
                            saturationValue = sliderValue!!
                        }
                    }),
                    Pair(ColorScreenOptions.ValueOption, {
                        sliderValue = brightnessValue
                        onValueChange = {
                            brightnessValue = sliderValue!!
                        }
                    })
                )
            )
        }
    }
}