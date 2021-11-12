package flab.editor.library.adjust

import android.graphics.Bitmap
import flab.editor.library.ImageProcessing
import org.opencv.core.Mat
import org.opencv.core.Rect

class Crop(
    bitmap: Bitmap
) : ImageProcessing(bitmap) {

    fun crop(x: Int, y: Int, width: Int, height: Int) : Bitmap {
        val frame = Rect(x, y, width, height)
        val newSrc = Mat(src, frame)
        src = newSrc
        createResultBitmap()
        saveResult()
        return result
    }

}