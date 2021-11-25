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
import com.example.main.models.ColorScreenOptions
import com.example.main.ui.options.ColorViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun ColorScreen(
    modifier: Modifier = Modifier,
    sourceViewModel: SourceViewModel = viewModel(),
    save: () -> Unit,
) {
    val screenViewModel: ColorViewModel =
        viewModel(factory = ColorViewModelFactory(sourceViewModel.currentSource!!))
    val bitmap = screenViewModel.source

    var hueValue by remember { mutableStateOf(0f) }
    var saturationValue by remember { mutableStateOf(0f) }
    var brightnessValue by remember { mutableStateOf(0f) }

    var sliderValue by remember { mutableStateOf<Float?>(null) }
    var onValueChange by remember { mutableStateOf({ }) }

    val scope = rememberCoroutineScope()
    var job: Job? by remember {
        mutableStateOf(null)
    }

    DefaultOptionScreen(
        modifier = modifier.fillMaxWidth(),
        bitmapImage = bitmap.asImageBitmap(),
        onSave = {
            sourceViewModel.currentSource = bitmap
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
                        job = scope.launch(Dispatchers.Default) {
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