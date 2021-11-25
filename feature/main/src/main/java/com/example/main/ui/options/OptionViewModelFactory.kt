package com.example.main.ui.options

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.main.ui.options.color.ColorViewModel
import com.example.main.ui.options.rotate.FlipRotateViewModel
import com.example.main.ui.options.sharpness.SharpnessViewModel
import com.example.main.ui.options.tune.TuneViewModel

//TODO: optimize for all
class OptionViewModelFactory(private val bitmap: Bitmap): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FlipRotateViewModel(bitmap) as T
    }
}

class TuneViewModelFactory(private val bitmap: Bitmap): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TuneViewModel(bitmap) as T
    }
}

class SharpnessViewModelFactory(private val bitmap: Bitmap): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SharpnessViewModel(bitmap) as T
    }
}

class ColorViewModelFactory(private val bitmap: Bitmap): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ColorViewModel(bitmap) as T
    }
}