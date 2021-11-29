package com.example.imagesource.utils

import android.graphics.Bitmap
import android.graphics.Matrix

/**
 * @param bitmap the Bitmap to be scaled
 * @param threshold the maximum dimension (either width or height) of the scaled bitmap
 * @param isNecessaryToKeepOrig is it necessary to keep the original bitmap?
 * If not recycle the original bitmap to prevent memory leak.
 */
fun getScaledDownBitmap(bitmap: Bitmap, threshold: Int, isNecessaryToKeepOrig: Boolean): Bitmap {
    val width: Int = bitmap.width
    val height: Int = bitmap.height
    var newWidth = width
    var newHeight = height

    if (width > height && width > threshold) {
        newWidth = threshold
        newHeight = (height * newWidth.toFloat() / width).toInt()
    }

    if (width in (height + 1)..threshold) {
        return bitmap
    }

    if (width < height && height > threshold) {
        newHeight = threshold
        newWidth = (width * newHeight.toFloat() / height).toInt()
    }

    if (height in (width + 1)..threshold) {
        return bitmap
    }

    if (width == height && width > threshold) {
        newWidth = threshold
        newHeight = newWidth
    }

    return if (width == height && width <= threshold) {
        bitmap
    } else getResizedBitmap(bitmap, newWidth, newHeight, isNecessaryToKeepOrig)
}

private fun getResizedBitmap(
    bm: Bitmap,
    newWidth: Int,
    newHeight: Int,
    isNecessaryToKeepOrig: Boolean,
): Bitmap {
    val width: Int = bm.width
    val height: Int = bm.height
    val scaleWidth = newWidth.toFloat() / width
    val scaleHeight = newHeight.toFloat() / height

    val matrix = Matrix()

    matrix.postScale(scaleWidth, scaleHeight)

    val resizedBitmap: Bitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false)
    if (!isNecessaryToKeepOrig) {
        bm.recycle()
    }

    return resizedBitmap
}