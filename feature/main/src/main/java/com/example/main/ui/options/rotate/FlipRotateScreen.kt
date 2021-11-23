package com.example.main.ui.options.rotate

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.imagesource.SourceViewModel
import com.example.main.components.DefaultOptionScreen
import com.example.main.components.OptionsBottomBar
import com.example.main.models.FlipRotateOptions
import com.example.constants.FlipAlignment
import com.example.constants.RotateAlignment
import com.example.main.ui.options.OptionViewModelFactory
import flab.editor.library.adjust.FlipRotate
import kotlinx.coroutines.Job

@Composable
fun FlipRotateScreen(
    modifier: Modifier = Modifier,
    sourceViewModel: SourceViewModel = viewModel(),
    save: () -> Unit
) {
    val rotateViewModel: FlipRotateViewModel = viewModel(
        factory = OptionViewModelFactory(sourceViewModel.currentSource!!)
    )

    val bitmap = rotateViewModel.source

    DefaultOptionScreen(
        modifier = modifier.fillMaxWidth(),
        bitmapImage = bitmap.asImageBitmap()
    ) {
        OptionsBottomBar(
            listOf(
                Pair(FlipRotateOptions.FlipOption, { rotateViewModel.performFlip() }),
                Pair(FlipRotateOptions.RotateOption, { rotateViewModel.performRotate() })
            )
        )
    }
}