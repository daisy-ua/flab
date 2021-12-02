package com.example.main.components

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


@Composable
fun SliderScreen(
    modifier: Modifier = Modifier,
    imageSource: Bitmap?,
    sliderValue: MutableState<Float?>,
    onSave: () -> Unit,
    onSliderUpdate: suspend () -> Unit,
    bottomBar: @Composable () -> Unit,
) {
    val scope = rememberCoroutineScope()
    var job: Job? by remember {
        mutableStateOf(null)
    }

    DefaultOptionScreen(
        modifier = modifier.fillMaxWidth(),
        bitmapImage = imageSource?.asImageBitmap(),
        onSave = onSave
    ) {
        Column {
            sliderValue.value?.let { value ->
                AmountSlider(
                    sliderValue = value,
                    onValueChange = { sliderValue.value = it },
                    onValueChangeFinished = {
                        job?.cancel()
                        job = scope.launch(Dispatchers.Main) {
                            try {
                                onSliderUpdate()
                            } catch (ex: Exception) {
                            }
                        }
                    }
                )
            }

            bottomBar()
        }
    }
}