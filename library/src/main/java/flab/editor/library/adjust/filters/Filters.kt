package flab.editor.library.adjust.filters

import android.graphics.Bitmap
import flab.editor.library.ImageProcessing
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

class Filters(
    bitmap: Bitmap,
) : ImageProcessing(bitmap) {
    private val bgraChannels: List<Mat> = mutableListOf()

    init {
        createResultBitmap()
    }

    fun applyGrayScale(): Bitmap {
        val dst = Mat()
        Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGR2GRAY)
        result = Bitmap.createBitmap(src.cols(), src.rows(), Bitmap.Config.ARGB_8888)
        saveResult(dst)
        return result
    }

    fun applyBinary(): Bitmap {
        val dst = Mat()
        Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGR2GRAY)
        Imgproc.threshold(dst, dst, 127.0, 255.0, Imgproc.THRESH_BINARY)
        result = Bitmap.createBitmap(src.cols(), src.rows(), Bitmap.Config.ARGB_8888)

        saveResult(dst)
        return result
    }

    fun applyOtsu(): Bitmap {
        val dst = Mat()
        Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGR2GRAY)
        Imgproc.threshold(dst, dst, 127.0, 255.0, Imgproc.THRESH_OTSU)
        result = Bitmap.createBitmap(src.cols(), src.rows(), Bitmap.Config.ARGB_8888)

        saveResult(dst)
        return result
    }

    fun applySepia() = applyKernelTransform(KernelMats.SEPIA_MAT)

    fun applyColored() = applyKernelTransform(KernelMats.COLORED_MAT)

    fun applyWinter() = applyKernelTransform(KernelMats.COLORED_MAT, false)

    private fun applyKernelTransform(kernel: Mat, normalize: Boolean = true): Bitmap {
        val dst = Mat()
        Core.transform(src, dst, kernel)
        result = Bitmap.createBitmap(src.cols(), src.rows(), Bitmap.Config.ARGB_8888)
        return saveNormalizedBGR(dst, normalize)
    }

    private fun saveNormalizedBGR(source: Mat, normalize: Boolean = true): Bitmap {
        normalizeBGR(source)
        val dst = mergeBGRChannels()
        saveResult(dst)
        return result
    }

    private fun normalizeBGR(source: Mat) {
        Core.split(source, bgraChannels)
        Core.normalize(bgraChannels[0], bgraChannels[0], 0.0, 255.0, Core.NORM_MINMAX)
        Core.normalize(bgraChannels[1], bgraChannels[1], 0.0, 255.0, Core.NORM_MINMAX)
        Core.normalize(bgraChannels[2], bgraChannels[2], 0.0, 255.0, Core.NORM_MINMAX)
    }

    private fun mergeBGRChannels(): Mat {
        val updatedSrc = Mat()
        Core.merge(listOf(
            bgraChannels[0], bgraChannels[1], bgraChannels[2]
        ), updatedSrc)

        return updatedSrc
    }
}