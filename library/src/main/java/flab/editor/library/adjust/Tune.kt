package flab.editor.library.adjust

import android.graphics.Bitmap
import org.opencv.android.Utils
import org.opencv.core.Mat

class Tune(bitmap: Bitmap) {
    private val src = Mat()
    private val result: Bitmap

    init {
        Utils.bitmapToMat(bitmap, src)
        result = Bitmap.createBitmap(src.cols(), src.rows(), Bitmap.Config.ARGB_8888)
    }

    /**
     * @param value is in range of [1.0 - 3.0]
     */
    fun setContrast(value: Double) : Bitmap =
        setBasicLinearTransforms(alpha = value)


    /**
     * @param value is in range of [0 - 100]
     */
    fun setBrightness(value: Double) : Bitmap =
        setBasicLinearTransforms(beta = value)


    private fun setBasicLinearTransforms(
        alpha: Double = 1.0, beta: Double = 0.0
    ) : Bitmap {
        src.convertTo(src, -1, alpha, beta)

        Utils.matToBitmap(src, result)
        return result
    }
}