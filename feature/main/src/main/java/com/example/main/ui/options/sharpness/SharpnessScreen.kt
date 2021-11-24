package com.example.main.ui.options.sharpness

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.imagesource.SourceViewModel
import com.example.main.R
import com.example.main.components.AmountSlider
import com.example.main.components.DefaultOptionScreen
import com.example.main.ui.options.SharpnessViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun SharpnessScreen(
    modifier: Modifier = Modifier,
    sourceViewModel: SourceViewModel = viewModel(),
    save: () -> Unit,
) {
    val screenViewModel: SharpnessViewModel =
        viewModel(factory = SharpnessViewModelFactory(sourceViewModel.currentSource!!))
    val bitmap = screenViewModel.source

    var sharpeningValue by remember { mutableStateOf(0f) }

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
            AmountSlider(
                sliderName = R.string.sharpness_option,
                sliderValue = sharpeningValue,
                onValueChange = { sharpeningValue = it },
                onValueChangeFinished = {
                    job?.cancel()
                    job = scope.launch(Dispatchers.Default) {
                        try {
                            screenViewModel.setSharpness(sharpeningValue)
                        } catch (ex: Exception) {
                        }
                    }
                }
            )
        }
    }
}