package com.example.main.ui.options.rotate

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagesource.utils.FlipRotateCounter
import com.example.main.ui.options.utils.IProcessManager
import flab.editor.library.ImageProcessManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FlipRotateViewModel : ViewModel(), IProcessManager {
    var source by mutableStateOf<Bitmap?>(null)
    val flipRotateCounter = FlipRotateCounter()

    override lateinit var processManager: ImageProcessManager

    fun setup(
        processManager: ImageProcessManager,
        initialSource: Bitmap,
    ) {
        setupProcessor(processManager)
        processManager.updateFlipRotate(initialSource)
        source = initialSource
    }

    fun performFlip() {
        viewModelScope.launch(Dispatchers.Main) {
            source = withContext(Dispatchers.Default) {
                processManager.applyFlip()
            }
            flipRotateCounter.updateFlipCount()
        }
    }

    fun performRotate() {
        viewModelScope.launch(Dispatchers.Main) {
            source = withContext(Dispatchers.Default) {
                processManager.applyRotate()
            }
            flipRotateCounter.updateRotateCount()
        }
    }
}