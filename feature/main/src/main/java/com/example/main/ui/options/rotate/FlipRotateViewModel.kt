package com.example.main.ui.options.rotate

import android.graphics.Bitmap
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

//  FIXME: wait for jobs to be done
    fun performFlip() {
        viewModelScope.launch(Dispatchers.Default) {
            source = flipRotate.flip(FlipAlignment.Y_AXIS)
        }
    }

    fun performRotate() {
        viewModelScope.launch(Dispatchers.Default) {
            source = flipRotate.rotate(RotateAlignment.RIGHT)
        }
    }
}