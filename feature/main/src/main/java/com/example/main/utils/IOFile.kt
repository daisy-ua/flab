package com.example.main.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.example.components.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


// TODO: move to separate file
// TODO: connect with cameraExt
// TODO: make async
fun saveToGallery(context: Context, bitmap: Bitmap): Uri? {
    val savedImageURL = MediaStore.Images.Media.insertImage(
        context.contentResolver,
        bitmap,
        createFileName(context),
        ""
    )

    return Uri.parse(savedImageURL)
}


private fun createFileName(context: Context) : String {
    val formatPattern = context.resources.getString(R.string.datetime_format_pattern)
    val timeStamp: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val formatter = DateTimeFormatter.ofPattern(formatPattern)
        val current = LocalDateTime.now()
        current.format(formatter)
    } else {
        val current = SimpleDateFormat(formatPattern, Locale("US"))
        current.format(Date())
    }

    return "${context.resources.getString(R.string.project_name)}-$timeStamp.jpg"
}