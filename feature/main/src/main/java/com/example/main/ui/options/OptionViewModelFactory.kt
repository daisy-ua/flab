package com.example.main.ui.options

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.main.ui.options.rotate.FlipRotateViewModel

//TODO: optimize for all
class OptionViewModelFactory(private val bitmap: Bitmap): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FlipRotateViewModel(bitmap) as T
    }
}
