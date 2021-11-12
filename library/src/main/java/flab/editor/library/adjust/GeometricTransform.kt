package flab.editor.library.adjust

import android.graphics.Bitmap
import flab.editor.library.ImageProcessing
import org.opencv.core.Mat
import org.opencv.core.MatOfPoint2f
import org.opencv.core.Point
import org.opencv.imgproc.Imgproc

class GeometricTransform(
    bitmap: Bitmap
) : ImageProcessing(bitmap) {
    private val srcTriangle = arrayOf(
        Point(0.0, 0.0),
        Point(src.cols() - 1.0, 0.0),
        Point(0.0, src.rows() - 1.0)
    )

    fun setAffineTransform(
        x1: Double = 0.0, y1: Double,
        x2: Double, y2: Double,
        x3: Double, y3: Double,
    ) : Bitmap {
        val dstTriangle = arrayOf(
            Point(x1, src.rows() * y1),
            Point(src.cols() * x2, src.cols() * y2),
            Point(src.cols() * x3, src.rows() * y3)
        )

        val warpMat = Imgproc.getAffineTransform(
            MatOfPoint2f(*srcTriangle),
            MatOfPoint2f(*dstTriangle)
        )

        val dst = Mat.zeros(src.rows(), src.cols(), src.type())

        Imgproc.warpAffine(src, dst, warpMat, dst.size())

        src = dst
        createResultBitmap()
        saveResult()
        return result
    }
}