package flab.editor.library.adjust

import android.graphics.Bitmap
import flab.editor.library.ImageProcessing
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc

class HSVTransform(
    bitmap: Bitmap,
) : ImageProcessing(bitmap) {
    private val hsvImage = Mat()
    private val bgraChannels: List<Mat> = mutableListOf()
    private lateinit var a: Mat

    private val hsvChannels: List<Mat> = mutableListOf()
    private lateinit var h: Mat
    private lateinit var s: Mat
    private lateinit var v: Mat

//Some colour in HSV, [Hue (0-360), Saturation (0-1), Value (0-1)]
//Some colour in HSV, [Hue (0-360), Saturation (0-1), Value (0-1)]

    init {
        parseBGRAChannels()
        parseHSVChannels()
        createResultBitmap()
    }

    fun setSaturation(): Bitmap {
        Core.merge(listOf(
            h, s, v
        ), hsvImage)

        val brgNew = Mat()
        Imgproc.cvtColor(hsvImage, brgNew, Imgproc.COLOR_HSV2BGR)

        Core.split(brgNew, bgraChannels)
        val argb = Mat()
        Core.merge(listOf(
            bgraChannels[2],
            bgraChannels[1],
            bgraChannels[0],
            a
        ), argb)

        Imgproc.cvtColor(argb, src, Imgproc.COLOR_BGR2BGRA)

        saveResult()
        return result
    }

    private fun parseBGRAChannels() {
        Core.split(src, bgraChannels)
        a = bgraChannels[3]
    }

    private fun parseHSVChannels() {
        Imgproc.cvtColor(src, hsvImage, Imgproc.COLOR_RGB2HSV)
        Core.split(hsvImage, hsvChannels)
        h = hsvChannels[0]
        s = hsvChannels[1]
        v = hsvChannels[2]
    }
}