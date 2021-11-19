package com.example.main.ui.options

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import com.example.main.components.DefaultOptionScreen
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.imagesource.SourceViewModel
import flab.editor.library.adjust.Tune

@Composable
fun TuneScreen(
    modifier: Modifier = Modifier,
    sourceViewModel: SourceViewModel = viewModel(),
    save: () -> Unit
) {
    var bitmap by remember {
        mutableStateOf(sourceViewModel.currentSource)
    }
    val tune = Tune(sourceViewModel.currentSource!!)

    var contrastValue by remember { mutableStateOf(0f) }
    var brightnessValue by remember { mutableStateOf(0f) }

    DefaultOptionScreen(
        modifier = modifier.fillMaxWidth(),
        bitmapImage = bitmap?.asImageBitmap()
    ) {
        Column {
            Row {
               Text(text = "Contrast")
               Slider(
                   value = contrastValue,
                   onValueChange = { contrastValue = it },
                   valueRange = 1f..3f,
                   onValueChangeFinished = {
                       bitmap = tune.setContrast(contrastValue.toDouble())
                   }
               )
            }
            Row {
                Text(text = "Brightness")
                Slider(
                    value = brightnessValue,
                    onValueChange = { brightnessValue = it },
                    valueRange = 0f..100f,
                    onValueChangeFinished = {
                        bitmap = tune.setBrightness(brightnessValue.toDouble())
                    }
                )
            }
        }

    }


}

