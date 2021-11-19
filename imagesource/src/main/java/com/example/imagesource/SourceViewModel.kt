package com.example.imagesource

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel

class SourceViewModel(
    application: Application
) : AndroidViewModel(application) {
    var sourceString: String? = null
    var sourceUri: Uri? = null

    val originalSource: Bitmap? by lazy {
        sourceUri?.let { uri ->
            convertToBitmap(application, uri)
        }
    }

    var currentSource by mutableStateOf<Bitmap?>(null)
}

fun convertToBitmap(context: Context, imageUri: Uri): Bitmap =
    if (Build.VERSION.SDK_INT < 28) {
        MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
    } else {
        val source = ImageDecoder.createSource(context.contentResolver, imageUri)
        ImageDecoder.decodeBitmap(source) { decoder, _, _ ->
            decoder.isMutableRequired = true
        }
    }