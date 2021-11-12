package flab.editor.library.adjust

import android.graphics.Bitmap
import flab.editor.library.ImageProcessing

class Tune(
    bitmap: Bitmap
) : ImageProcessing(bitmap) {

    init {
        createResultBitmap()
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
        saveResult()
        return result
    }
}