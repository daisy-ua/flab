package com.example.main.ui.options.tune

import android.graphics.Bitmap
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.imagesource.SourceViewModel
import com.example.main.components.AmountSlider
import com.example.main.components.DefaultOptionScreen
import com.example.main.components.OptionsBottomBar
import com.example.main.models.TuneScreenOptions
import com.example.main.ui.options.convertPercentageToValue
import com.example.main.ui.options.convertValueToPercentage
import com.example.main.ui.options.tune.constants.BrightnessConstants
import com.example.main.ui.options.tune.constants.ContrastConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


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
                convertValueToPercentage(
                    _contrast.toFloat(),
                    ContrastConstants
                )
            } ?: 0f
        )
    }
    var brightnessValue by remember {
        mutableStateOf(
            sourceViewModel.brightnessValue?.let { _brightness ->
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

    var tuneBitmap: Bitmap? = null
    val originalSource = sourceViewModel.processManager?.originalSource!!
    val hsvBitmap = sourceViewModel.processManager!!.run {
        sourceViewModel.processManager!!.tune.updateSource(originalSource)

        applyHSVTransform(
            sourceViewModel.hueValue,
            sourceViewModel.saturationValue,
            sourceViewModel.valueValue
        )
    } ?: originalSource

    LaunchedEffect(key1 = true) {
        screenViewModel.setupProcessor(originalSource)
        if (contrastValue == 0f && brightnessValue == 0f) {
            screenViewModel.source =
                sourceViewModel.processManager?.blend?.blend(hsvBitmap, originalSource)
        } else {
            sourceViewModel.processManager!!.tune.updateSource(originalSource)
            tuneBitmap = sourceViewModel.processManager!!.applyLinearTransform(
                sourceViewModel.contrastValue,
                sourceViewModel.brightnessValue,
                originalSource
            )
            Log.d("rita", "${tuneBitmap?.width}   --  ${hsvBitmap.width}")


            screenViewModel.source =
                sourceViewModel.processManager?.blend?.blend(tuneBitmap!!, hsvBitmap)
        }

        sourceViewModel.sharpnessValue?.let {
            screenViewModel.source = sourceViewModel.resetSource(source = screenViewModel.source!!)
        }

    }

    DefaultOptionScreen(
        modifier = modifier.fillMaxWidth(),
        bitmapImage = bitmap?.asImageBitmap(),
        onSave = {
            sourceViewModel.tuneBitmap = tuneBitmap
            sourceViewModel.currentSource = bitmap
            sourceViewModel.contrastValue =
                convertPercentageToValue(contrastValue, ContrastConstants)
            sourceViewModel.brightnessValue =
                convertPercentageToValue(brightnessValue, BrightnessConstants)
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
                                tuneBitmap = screenViewModel.tune?.setBrightnessContrast(
                                    convertPercentageToValue(contrastValue, ContrastConstants),
                                    convertPercentageToValue(brightnessValue, BrightnessConstants),
                                )

                                val source =
                                    sourceViewModel.processManager?.blend?.blend(tuneBitmap!!,
                                        hsvBitmap)
                                screenViewModel.source = sourceViewModel.sharpnessValue?.let {
                                    sourceViewModel.resetSource(source = source!!)
                                } ?: source
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