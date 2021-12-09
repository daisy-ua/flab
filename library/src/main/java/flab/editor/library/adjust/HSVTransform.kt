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

//Hue range is [0,179], Saturation range is [0,255] and Value range is [0,255]

    init {
        parseBGRAChannels()
        parseHSVChannels()
        createResultBitmap()
    }

    fun setHSVTransform(hue: Double, saturation: Double, value: Double): Bitmap {
        val newH = setHue(hue)
        val newS = setSaturation(saturation)
        val newV = setBrightness(value)
        return saveRGB(newH, newS, newV)
    }

    private fun setHue(value: Double): Mat {
        val newH = Mat()
        Core.add(h, Scalar(value), newH)
        return newH
    }

    private fun setSaturation(value: Double): Mat {
        val newS = Mat()
        Core.add(s, Scalar(value), newS)
        Core.normalize(newS, newS, 0.0, 255.0, Core.NORM_MINMAX)
        return newS
    }

    private fun setBrightness(value: Double): Mat {
        val newV = Mat()
        Core.add(v, Scalar(value), newV)
        Core.normalize(newV, newV, 0.0, 255.0, Core.NORM_MINMAX)
        return newV
    }

    private fun saveRGB(newH: Mat = h, newS: Mat = s, newV: Mat = v): Bitmap {
        val bgrNew = convertHVS2GBR(newH, newS, newV)
        val dst = convertBGR2RGB(bgrNew)
        saveResult(dst)
        return result
    }

    private fun convertHVS2GBR(newH: Mat, newS: Mat, newV: Mat): Mat {
        val hsvNew = Mat()
        Core.merge(listOf(
            newH, newS, newV
        ), hsvNew)

        val brgNew = Mat()
        Imgproc.cvtColor(hsvNew, brgNew, Imgproc.COLOR_HSV2BGR)
        return brgNew
    }

    private fun convertBGR2RGB(bgrNew: Mat): Mat {
        Core.split(bgrNew, bgraChannels)
        val argb = Mat()
        Core.merge(listOf(
            bgraChannels[2],
            bgraChannels[1],
            bgraChannels[0],
            a
        ), argb)

        val dst = Mat()
        Imgproc.cvtColor(argb, dst, Imgproc.COLOR_BGR2BGRA)
        return dst
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