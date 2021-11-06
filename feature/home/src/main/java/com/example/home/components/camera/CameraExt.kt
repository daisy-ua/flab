package com.example.home.components.camera

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.example.components.R
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


fun saveMediaToStorage(context: Context) : Uri {
    val filename = createFileName(context)

    var imageUri: Uri? = Uri.EMPTY

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        context.contentResolver?.also { resolver ->

            val contentValues = ContentValues().apply {

                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }

            imageUri =
                resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        }
    } else {
        val imagesDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val image = File(imagesDir, filename)
        imageUri = Uri.fromFile(image)
    }

    return imageUri!!
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