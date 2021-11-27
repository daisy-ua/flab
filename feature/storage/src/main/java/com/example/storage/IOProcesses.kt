package com.example.storage

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

object IOProcesses {
    suspend fun saveMediaToStorage(context: Context, bitmap: Bitmap): Uri? {
        return withContext(Dispatchers.IO) {
            val savedImageURL = MediaStore.Images.Media.insertImage(
                context.contentResolver,
                bitmap,
                createFileName(context),
                ""
            )

            Uri.parse(savedImageURL)
        }
    }


    suspend fun saveEmptyMediaToStorage(context: Context): Uri {
        return withContext(Dispatchers.IO) {
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

            return@withContext imageUri!!
        }

    }
}
