package com.example.main.ui.options.rotate

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.imagesource.SourceViewModel
import com.example.main.components.DefaultOptionScreen
import com.example.main.components.OptionsBottomBar
import com.example.main.models.FlipRotateOptions
import com.example.main.ui.options.OptionViewModelFactory

@Composable
fun FlipRotateScreen(
    modifier: Modifier = Modifier,
    sourceViewModel: SourceViewModel = viewModel(),
    save: () -> Unit,
) {
    val rotateViewModel: FlipRotateViewModel = viewModel(
        factory = OptionViewModelFactory(sourceViewModel.currentSource!!)
    )

    val bitmap = rotateViewModel.source

    DefaultOptionScreen(
        modifier = modifier.fillMaxWidth(),
        bitmapImage = bitmap.asImageBitmap(),
        onSave = {
            sourceViewModel.currentSource = bitmap
            save()
        }
    ) {
        OptionsBottomBar(
            modifier = modifier.fillMaxWidth(),
            listOf(
                Pair(FlipRotateOptions.FlipOption, { rotateViewModel.performFlip() }),
                Pair(FlipRotateOptions.RotateOption, { rotateViewModel.performRotate() })
            )
        )
    }
}