package com.example.main.ui.options.tune

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
import com.example.main.models.TuneScreenOptions
import com.example.main.ui.options.TuneViewModelFactory
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
    val screenViewModel: TuneViewModel =
        viewModel(factory = TuneViewModelFactory(sourceViewModel.currentSource!!))
    val bitmap = screenViewModel.source

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
                                Log.i(FILTER_COROUTINE, "in scope")
                                onValueChange()
                                Log.i(FILTER_COROUTINE, "after scope")
                            } catch (ex: Exception) {
                                Log.i(FILTER_COROUTINE, "Applying filter cancelled")
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
                            screenViewModel.setBrightnessContrast(sliderValue!!, brightnessValue)
                            contrastValue = sliderValue!!
                        }
                    }),
                    Pair(TuneScreenOptions.Brightness, {
                        sliderValue = brightnessValue
                        onValueChange = {
                            screenViewModel.setBrightnessContrast(sliderValue!!, brightnessValue)
                            brightnessValue = sliderValue!!
                        }
                    }),
                    Pair(TuneScreenOptions.Sharpening, {
                        sliderValue = sharpeningValue
                        onValueChange = {
                            screenViewModel.setSharpness(sliderValue!!)
                            sharpeningValue = sliderValue!!
                        }
                    })
                )
            )
        }
    }
}
