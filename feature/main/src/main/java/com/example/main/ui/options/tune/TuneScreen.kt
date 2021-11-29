package com.example.main.ui.options.tune

import android.graphics.Bitmap
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
import com.example.main.models.TuneScreenOptions
import com.example.main.ui.options.TuneViewModelFactory
import com.example.main.ui.options.convertPercentageToValue
import flab.editor.library.adjust.Tune
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


@Composable
fun TuneScreen(
    modifier: Modifier = Modifier,
    sourceViewModel: SourceViewModel = viewModel(),
    save: () -> Unit,
) {
    val screenViewModel: TuneViewModel =
        viewModel(factory = TuneViewModelFactory(sourceViewModel.currentSource!!))
    val bitmap = screenViewModel.src

    var contrastValue by remember { mutableStateOf(sourceViewModel.contrastValue.toFloat()) }
    var brightnessValue by remember { mutableStateOf(sourceViewModel.brightnessValue.toFloat()) }

    var sliderValue by remember { mutableStateOf<Float?>(null) }
    var onValueChange by remember { mutableStateOf({ }) }

    val scope = rememberCoroutineScope()
    var job: Job? by remember {
        mutableStateOf(null)
    }

    LaunchedEffect(key1 = true) {
        sourceViewModel.updateSource(null, null)
        screenViewModel.source = sourceViewModel.currentSource!!
        screenViewModel.src = sourceViewModel.currentSource!!

//        screenViewModel.setBrightnessContrast(contrastValue, brightnessValue)

    }

    DefaultOptionScreen(
        modifier = modifier.fillMaxWidth(),
        bitmapImage = bitmap?.asImageBitmap(),
        onSave = {
            sourceViewModel.currentSource = bitmap
            sourceViewModel.contrastValue = contrastValue.toDouble()
            sourceViewModel.brightnessValue = brightnessValue.toDouble()
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
                                screenViewModel.setBrightnessContrast(contrastValue,brightnessValue)
                            } catch (ex: Exception) {
                            }
                        }
                    }
                )
            }

            OptionsBottomBar(
                modifier = modifier.fillMaxWidth(),
                photoOptions = listOf(
                    Pair(TuneScreenOptions.Contrast, {
                        sliderValue = contrastValue
                        onValueChange = {
                            contrastValue = sliderValue!!
                        }
                    }),
                    Pair(TuneScreenOptions.Brightness, {
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
