package com.example.main.components

import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.runtime.Composable
import com.example.main.utils.saveToGallery

@Composable
fun SaveToGallery(context: Context, source: Bitmap) {
    saveToGallery(
        context,
        source
    )

    Toast.makeText(
        context,
        "Image was saved to Gallery",
        Toast.LENGTH_SHORT
    ).show()
}