package com.example.main.ui.options.rotate

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.constants.FlipAlignment
import com.example.constants.RotateAlignment
import flab.editor.library.adjust.FlipRotate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FlipRotateViewModel(bitmap: Bitmap) : ViewModel() {
    var source by mutableStateOf(bitmap)
        private set

    private var flipRotate = FlipRotate(source)

    private var rotateCount = 0

//  FIXME: wait for jobs to be done
    fun performFlip() {
        viewModelScope.launch(Dispatchers.Default) {
            source = flipRotate.flip(FlipAlignment.Y_AXIS)
            isFlipped = !isFlipped
            rotateCount += 2
        }
    }

    fun performRotate() {
        viewModelScope.launch(Dispatchers.Default) {
            source = flipRotate.rotate(RotateAlignment.RIGHT)
            rotateCount++
            if (rotateCount >= 4) rotateCount = 0
            direction = rotateDirections[rotateCount]
        }
    }

    enum class Directions {
        UP, RIGHT, DOWN, LEFT
    }

    val rotateDirections = listOf(
        Directions.UP,
        Directions.RIGHT,
        Directions.DOWN,
        Directions.LEFT
    )

    var direction = Directions.UP

    var isFlipped = false
}