package com.example.imagesource

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.imagesource.utils.convertToBitmap
import com.example.imagesource.utils.getScaledDownBitmap

private const val MAX_DIM_BITMAP = 900

class SourceViewModel(
    applicationContext: Application,
) : AndroidViewModel(applicationContext) {
    private var sourceUri: Uri? = null

    val originalSource: Bitmap? by lazy {
        sourceUri?.let { uri ->
            convertToBitmap(applicationContext, uri)
        }
    }

    var minimizedSource: Bitmap? = null
        private set

    var currentSource by mutableStateOf<Bitmap?>(null)

    fun setupBitmap(uri: Uri) {
        sourceUri = uri
        minimizedSource = originalSource?.let { src ->
            getScaledDownBitmap(src, MAX_DIM_BITMAP, true)
        }
        currentSource = minimizedSource
    }
}