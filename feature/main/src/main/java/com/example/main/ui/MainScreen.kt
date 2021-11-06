package com.example.main.ui

import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext

@Composable
fun MainScreen(
    imageUri: Uri
) {
    val bitmap = if (Build.VERSION.SDK_INT < 28) {
        MediaStore.Images.Media.getBitmap(LocalContext.current.contentResolver, imageUri)
    }
    else {
        val source = ImageDecoder.createSource(LocalContext.current.contentResolver, imageUri)
        ImageDecoder.decodeBitmap(source)
    }

    Image(bitmap = bitmap!!.asImageBitmap(), contentDescription = "")
}