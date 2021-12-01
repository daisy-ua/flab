package com.example.main.ui.options.rotate

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.constants.FlipAlignment
import com.example.constants.RotateAlignment
import com.example.imagesource.SourceViewModel
import com.example.main.components.DefaultOptionScreen
import com.example.main.components.OptionsBottomBar
import com.example.main.models.FlipRotateOptions
import com.example.main.ui.options.OptionViewModelFactory
import flab.editor.library.adjust.FlipRotate

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

    var original = sourceViewModel.processManager?.originalSource ?: sourceViewModel.processManager?.tune?.getSource()

    DefaultOptionScreen(
        modifier = modifier.fillMaxWidth(),
        bitmapImage = bitmap.asImageBitmap(),
        onSave = {
            val flipRotate = FlipRotate(original!!)

            for (direction in rotateViewModel.rotateDirections) {
                if (direction.name == rotateViewModel.direction.name) {
                    break
                }
                original = flipRotate.rotate(RotateAlignment.RIGHT)
            }
            flipRotate.updateSource(original!!)

            if (rotateViewModel.isFlipped) {
                original = flipRotate.flip(FlipAlignment.Y_AXIS)
            }

            sourceViewModel.currentSource = bitmap
            sourceViewModel.processManager?.setNewOriginalSource(original!!)
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