package flab.editor.library.adjust

import android.graphics.Bitmap
import flab.editor.library.ImageProcessing
import org.opencv.core.Mat

class Tune(
    bitmap: Bitmap
) : ImageProcessing(bitmap) {

    init {
        createResultBitmap()
    }

    /**
     * @param value should be larger than 0
     */
    fun setContrast(value: Double) : Bitmap =
        setBasicLinearTransforms(alpha = value)
    /**
     * @param value is in range of [0 - 100]
     */
    fun setBrightness(value: Double) : Bitmap =
        setBasicLinearTransforms(beta = value)

    fun setBrightnessContrast(alpha: Double, beta: Double) =
        setBasicLinearTransforms(alpha, beta)


    private fun setBasicLinearTransforms(
        alpha: Double = 1.0, beta: Double = 0.0
    ) : Bitmap {
        val dst = Mat()
        src.convertTo(dst, -1, alpha, beta)
        saveResult(dst)
        return result
    }
}