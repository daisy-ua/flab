package com.example.main.ui.options

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import com.example.main.components.DefaultOptionScreen
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.imagesource.SourceViewModel
import com.example.main.components.AmountSlider
import com.example.main.components.OptionsBottomBar
import com.example.main.constants.BrightnessConstants
import com.example.main.constants.ContrastConstants
import com.example.main.models.TuneScreenOptions
import flab.editor.library.adjust.Tune
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

@Composable
fun TuneScreen(
    modifier: Modifier = Modifier,
    sourceViewModel: SourceViewModel = viewModel(),
    save: () -> Unit
) {
    val tune = Tune(sourceViewModel.currentSource!!)
    var bitmap by remember { mutableStateOf(sourceViewModel.currentSource) }

    var contrastValue by remember { mutableStateOf(0f) }
    var brightnessValue by remember { mutableStateOf(0f) }

    var sliderValue by remember { mutableStateOf<Float?>(null) }
    var onValueChange by remember { mutableStateOf({  }) }

    DefaultOptionScreen(
        modifier = modifier.fillMaxWidth(),
        bitmapImage = bitmap?.asImageBitmap()
    ) {
        Column {
            sliderValue?.let { value ->
                AmountSlider(
                    sliderValue = value,
                    onValueChange = { sliderValue = it },
                    onValueChangeFinished = onValueChange
                )

                Log.i("rita", "$contrastValue     ${convertPercentageToValue(brightnessValue, BrightnessConstants)}")
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
                    })
                )
            )
        }
    }
}
