package flab.editor.library

import android.graphics.Bitmap
import org.opencv.android.Utils
import org.opencv.core.Mat

open class ImageProcessing(
    private val bitmap: Bitmap,
) {
    protected var src: Mat = Mat()
    protected lateinit var result: Bitmap

    init {
        Utils.bitmapToMat(bitmap, src)
    }

    fun createResultBitmap() {
        result = Bitmap.createBitmap(src.cols(), src.rows(), Bitmap.Config.ARGB_8888)
    }

    fun saveResult(source: Mat = src) = Utils.matToBitmap(source, result)

    fun updateSource(bitmap: Bitmap) {
        Utils.bitmapToMat(bitmap, src)
        createResultBitmap()
    }

    fun getSource() = bitmap
}
