package com.example.main.ui.options.rotate

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.main.ui.options.rotate.constants.FlipRotateCounter
import com.example.main.ui.options.IProcessManager
import flab.editor.library.ImageProcessManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FlipRotateViewModel : ViewModel(), IProcessManager {
    var source by mutableStateOf<Bitmap?>(null)
    private val flipRotateCounter = FlipRotateCounter()

    override lateinit var processManager: ImageProcessManager

    fun setup(
        processManager: ImageProcessManager,
        initialSource: Bitmap,
    ) {
        setupProcessor(processManager)
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

    suspend fun updateOriginal() = withContext(Dispatchers.Default) {
        var original = originalSource
        processManager.updateFlipRotate(original)

        for (direction in flipRotateCounter.rotateDirections) {
            if (direction.name == flipRotateCounter.direction.name) {
                break
            }
            original = processManager.applyRotate()
        }

        if (flipRotateCounter.isFlipped) {
            processManager.updateFlipRotate(original)
            original = processManager.applyFlip()
        }
        return@withContext original
    }
}