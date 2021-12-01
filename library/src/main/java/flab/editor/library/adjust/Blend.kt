package flab.editor.library.adjust

import android.graphics.Bitmap
import android.util.Log
import flab.editor.library.ImageProcessing
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.Mat

class Blend(
    bitmap: Bitmap,
) : ImageProcessing(bitmap) {

    init {
        createResultBitmap()
    }

    fun blend(bitmap: Bitmap, bitmap2: Bitmap): Bitmap {
        val src1 = Mat()
        Utils.bitmapToMat(bitmap, src1)
        val src2 = Mat()
        Utils.bitmapToMat(bitmap2, src2)

        val dst = Mat()
        Core.addWeighted(src1, 0.5, src2, 0.5, 0.0, dst)
        result = Bitmap.createBitmap(dst.cols(), dst.rows(), Bitmap.Config.ARGB_8888)
        saveResult(dst)
        return result
    }
}