package flab.editor.library.adjust

import android.graphics.Bitmap
import flab.editor.library.ImageProcessing
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

class Sharpness(
    bitmap: Bitmap
) : ImageProcessing(bitmap) {
    private val dst: Mat = Mat(src.rows(), src.cols(), src.type())

    init {
        createResultBitmap()
    }

    /**
     * @param kernelSize is Gaussian kernel size. Must be positive and odd
     */
    fun blur(kernelSize: Double) : Bitmap {
        gaussianBlur(kernelSize)
        saveResult(dst)
        return result
    }

    fun sharpen(kernelSize: Double) : Bitmap {
        gaussianBlur(kernelSize)
        Core.addWeighted(src, 1.5, dst, -0.5,0.0, dst)
        saveResult(dst)
        return result
    }

    private fun gaussianBlur(kernelSize: Double) =
        Imgproc.GaussianBlur(src, dst, Size(kernelSize, kernelSize), 0.0, 0.0)
}