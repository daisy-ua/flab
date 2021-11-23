package flab.editor.library.adjust

import android.graphics.Bitmap
import flab.editor.library.ImageProcessing
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import kotlin.math.pow
import kotlin.math.roundToInt


class Tune(
    bitmap: Bitmap,
) : ImageProcessing(bitmap) {

    init {
        createResultBitmap()
    }

    /**
     * @param value should be larger than 0
     * 0 < value < 1 - decrease contrast
     * value > 1 - increase contrast
     * value == 1 - no changes applied
     */
    fun setContrast(value: Double): Bitmap =
        setBasicLinearTransforms(alpha = value)

    /**
     * @param value is recommended in range of [-100 -- 100]
     */
    fun setBrightness(value: Double): Bitmap =
        setBasicLinearTransforms(beta = value)

    /**
     * @param alpha is used for contrast modification
     * should be larger than 0
     * 0 < alpha < 1 - decrease contrast
     * alpha > 1 - increase contrast
     * alpha == 1 - no changes applied
     *
     * @param beta is used for brightness
     * recommended in range of [-100 -- 100]
     */
    fun setBrightnessContrast(alpha: Double, beta: Double) =
        setBasicLinearTransforms(alpha, beta)

    // FIXME: optimize vague layer
    private fun setBasicLinearTransforms(
        alpha: Double = 1.0, beta: Double = 0.0,
    ): Bitmap {
        var dst = Mat()
        src.convertTo(dst, -1, alpha, beta)
        dst = performGammaCorrection(dst)
        saveResult(dst)
        return result
    }

    private fun saturate(value: Double): Byte {
        var iVal = value.roundToInt()
        iVal = if (iVal > 255) 255 else if (iVal < 0) 0 else iVal
        return iVal.toByte()
    }

    private fun performGammaCorrection(dst: Mat, gammaValue: Double = 0.6): Mat {
        val lookUpTable = Mat(1, 256, CvType.CV_8U)
        val lookUpTableData = ByteArray((lookUpTable.total() * lookUpTable.channels()).toInt())
        for (i in 0 until lookUpTable.cols()) {
            lookUpTableData[i] = saturate((i / 255.0).pow(gammaValue) * 255.0)
        }
        lookUpTable.put(0, 0, lookUpTableData)
        val img = Mat()
        Core.LUT(dst, lookUpTable, img)
        return img
    }
}