package com.example.main.ui.options

import android.util.Log
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
import com.example.main.constants.BrightnessConstants
import com.example.main.constants.ContrastConstants
import com.example.main.constants.GaussianKernelConstants
import com.example.main.models.TuneScreenOptions
import flab.editor.library.adjust.Sharpness
import flab.editor.library.adjust.Tune
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val FILTER_COROUTINE = "FilterCoroutine"

@Composable
fun TuneScreen(
    modifier: Modifier = Modifier,
    sourceViewModel: SourceViewModel = viewModel(),
    save: () -> Unit,
) {
//    TODO: move logic to viewmodel
    var bitmap by remember { mutableStateOf(sourceViewModel.currentSource) }
    val tune = Tune(bitmap!!)
    val sharpness = Sharpness(bitmap!!)

    var contrastValue by remember { mutableStateOf(0f) }
    var brightnessValue by remember { mutableStateOf(0f) }
    var sharpeningValue by remember { mutableStateOf(0f) }

    var sliderValue by remember { mutableStateOf<Float?>(null) }
    var onValueChange by remember { mutableStateOf({ }) }

    val scope = rememberCoroutineScope()
    var job: Job? by remember {
        mutableStateOf(null)
    }

    DefaultOptionScreen(
        modifier = modifier.fillMaxWidth(),
        bitmapImage = bitmap?.asImageBitmap()
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
                            } catch (ex: Exception) {
                                Log.i(FILTER_COROUTINE, "Applying filter cancelled")
                            }
                        }
                    }
                )
            }

            OptionsBottomBar(
                photoOptions = listOf(
                    Pair(TuneScreenOptions.Contrast, {
                        sliderValue = contrastValue
                        onValueChange = {
                            bitmap = tune.setBrightnessContrast(
                                convertPercentageToValue(sliderValue!!, ContrastConstants),
                                convertPercentageToValue(brightnessValue, BrightnessConstants),
                            )
                            contrastValue = sliderValue!!
                        }
                    }),
                    Pair(TuneScreenOptions.Brightness, {
                        sliderValue = brightnessValue
                        onValueChange = {
                            bitmap = tune.setBrightnessContrast(
                                convertPercentageToValue(contrastValue, ContrastConstants),
                                convertPercentageToValue(sliderValue!!, BrightnessConstants),
                            )
                            brightnessValue = sliderValue!!
                        }
                    }),
                    Pair(TuneScreenOptions.Sharpening, {
                        sliderValue = sharpeningValue
                        onValueChange = {
                            var kernelSize = convertPercentageToValue(sliderValue!!, GaussianKernelConstants)
                            if (kernelSize.toInt() % 2 == 0)
                                kernelSize += 1.0
                            bitmap = if (kernelSize > 0)
                                sharpness.sharpen(kernelSize)
                            else sharpness.blur(-kernelSize)
                            sharpeningValue = sliderValue!!
                        }
                    })
                )
            )
        }
    }
}
